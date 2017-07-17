package posti.examples.retail.cart.adapters;

import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.domain.Item;

@RequiredArgsConstructor
class ItemAdapter implements ItemBuilderSpec {
    private final Item item;

    @Override
    public void accept(ItemBuilder itemBuilder) {
        itemBuilder
            .withSku(item.getSku())
            .withQuantity(item.getQuantity());
    }
}
