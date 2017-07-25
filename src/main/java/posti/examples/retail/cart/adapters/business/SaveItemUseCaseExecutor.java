package posti.examples.retail.cart.adapters.business;

import java.util.UUID;

public interface SaveItemUseCaseExecutor extends UseCaseExecutor<SaveItemUseCaseExecutor.ScenarioState, CartState> {
    interface ScenarioState {
        ScenarioState withCartId(UUID cartId);
        ScenarioState withQuantity(int quantity);
        ScenarioState withSku(String sku);
    }
}
