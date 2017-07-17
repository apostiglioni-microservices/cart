package posti.examples.retail.cart.application.domain;

import java.util.Set;

import io.vavr.collection.Stream;
import posti.examples.retail.cart.application.domain.Cart.CartBuilder;

import static java.lang.String.format;

import static java.util.Collections.emptySet;

public class CartEventsAggregate {
    public Cart aggregate(Cart snapshot, Event event) {
        if (event.getCartVersion() != snapshot.getVersion() + 1) {
            throw new OutOfOrderException(format("Can't apply event %s to cart %s", event, snapshot));
        }

        switch (event.getType()) {
            case CLEARED: return handleClear(event);
            case REMOVE_ITEM     :
            case QUANTITY_CHANGED: return handleQuantityChange(snapshot, event);
            default              : throw new RuntimeException(format("Unknown event %s", event));
        }
    }

    private Cart handleClear(Event event) {
        return CartBuilder.buildWith(builder -> builder
                 .id(event.getCartId())
                 .version(event.getCartVersion())
                 .items(emptySet())
               );
    }

    private Cart handleQuantityChange(Cart cart, Event<QuantityChange> event) {
        QuantityChange data = event.getData();
        String sku = data.getSku();
        int newQuantity = data.getQuantity();

        Set<Item> newItems = Stream.ofAll(cart.getItems())
                              .filter(item -> !sku.equals(item.getSku()))  // Filter the rest of the items
                              .append(new Item(sku, newQuantity))          // Add the newly created item
                              .filter(item -> item.getQuantity() > 0)      // Clean up removed items, if any
                              .toJavaSet();

        return CartBuilder.buildWith(builder -> builder
                 .id(event.getCartId())
                 .version(event.getCartVersion())
                 .items(newItems)
               );

    }
}
