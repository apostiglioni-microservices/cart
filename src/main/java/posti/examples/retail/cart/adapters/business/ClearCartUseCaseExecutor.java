package posti.examples.retail.cart.adapters.business;

import java.util.UUID;

public interface ClearCartUseCaseExecutor extends UseCaseExecutor<ClearCartUseCaseExecutor.ScenarioState, CartState> {
    interface ScenarioState {
        ScenarioState withCartId(UUID cartId);
    }
}
