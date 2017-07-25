package posti.examples.retail.cart.adapters.business;

import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.domain.Item;

@RequiredArgsConstructor
class ItemStateTransfer implements StateTransfer<ItemState> {
    private final Item item;

    @Override
    public void transferTo(ItemState itemState) {
        itemState
            .withSku(item.getSku())
            .withQuantity(item.getQuantity());
    }
}
