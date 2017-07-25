package posti.examples.retail.cart.adapters.business;

import java.util.UUID;

public interface GetCartUseCaseExecutor extends UseCaseExecutor<GetCartUseCaseExecutor.Scenario, CartState> {
    interface Scenario {
        Scenario withCartId(UUID cartId);
    }
}
