package posti.examples.retail.cart.adapters.business;

import java.util.UUID;

public interface RemoveItemUseCaseExecutor extends UseCaseExecutor<RemoveItemUseCaseExecutor.Scenario, CartState> {
    interface Scenario {
        Scenario withCartId(UUID cartId);
        Scenario withSku(String sku);
    }
}
