package posti.examples.retail.cart.adapters.business;

@FunctionalInterface
public interface UseCaseExecutor<ScenarioState, OutputState> {
    UseCaseExecutionOutput<OutputState> apply(StateTransfer<ScenarioState> stateTransfer);
}
