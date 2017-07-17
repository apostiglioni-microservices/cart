package posti.examples.retail.cart.adapters;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.domain.Cart;

@RequiredArgsConstructor
class CartAdapter implements CartBuilderSpec {
    @NonNull
    private final Cart cart;

    @Override
    public CartBuilder accept(CartBuilder builder) {
        builder
            .withId(cart.getId())
            .withVersion(cart.getVersion());

        cart.getItems().stream()
            .map(ItemAdapter::new)
            .forEach(itemBuilderSpec -> builder.withItem(itemBuilderSpec::accept));

        return builder;
    }
}
