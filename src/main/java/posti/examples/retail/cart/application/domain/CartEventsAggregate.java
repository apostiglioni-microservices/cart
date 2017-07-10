package posti.examples.retail.cart.application.domain;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import io.vavr.collection.Stream;
import posti.examples.retail.cart.application.domain.Cart.CartBuilder;

import static java.lang.String.format;

public class CartEventsAggregate {
    @SuppressWarnings("unchecked")
    public Cart aggregate(Cart initialState, List<Event> events) {
        BiFunction<Cart, Event, Cart> handleEvents = (snapshot, event) -> {
            if (event.getId() != snapshot.getVersion() + 1) {
                throw new OutOfOrderException(format("Can't apply event %s to cart %s", event, snapshot));
            }

            switch (event.getType()) {
                case ADD_ITEM   :
                case REMOVE_ITEM: return handleStockChange(snapshot, event);
                default         : throw new RuntimeException(format("Unknown event %s", event));
            }
        };

        return Stream.ofAll(events).foldLeft(initialState, handleEvents);
    }

    private Cart handleStockChange(Cart cart, Event<StockChange> event) {
        StockChange data = event.getData();
        String sku = data.getSku();
        int delta = data.getQuantity();

        if (delta == 0) { return cart; }                                         // Nothing to do, no changes

        Stream<Item> cartItems = Stream.ofAll(cart.getItems());

        Item newItem = cartItems
                        .filter(item -> sku.equals(item.getSku()))               // Find the item with same sku
                        .map(item -> new Item(sku, item.getQuantity() + delta))  // Update the quantity
                        .getOrElse(new Item(sku, delta));                        // Or create new with desired quantity

        Set<Item> newItems = cartItems
                              .filter(item -> !sku.equals(item.getSku()))        // Filter the rest of the items
                              .append(newItem)                                   // Add the newly created item
                              .filter(item -> item.getQuantity() > 0)            // Clean up removed items, if any
                              .toJavaSet();

        return CartBuilder.buildWith(builder -> builder
                 .version(event.getId())
                 .items(newItems)
               );

    }
}
