package posti.examples.retail.cart.adapters.business;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.domain.Cart;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
class CartStateTransfer implements StateTransfer<CartState> {
    @NonNull
    private final Cart cart;

    @Override
    public void transferTo(CartState builder) {
        builder
            .withId(cart.getId())
            .withVersion(cart.getVersion());

        cart.getItems().stream()
            .map(ItemStateTransfer::new)    // new ItemAdapter(item)
            .forEach(builder::withItem);    // builder.withItem(itemAdapter)
    }

    public static StateTransfer<CartState> captureState(Cart cart) {
        return new CartStateTransfer(cart);
    }
}
