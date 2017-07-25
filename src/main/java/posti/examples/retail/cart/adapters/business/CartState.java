package posti.examples.retail.cart.adapters.business;

import java.util.UUID;

public interface CartState {
    CartState withId(UUID id);
    CartState withVersion(Long version);
    CartState withItem(StateTransfer<ItemState> spec);
}
