package posti.examples.retail.cart.adapters.business;

import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.business.ValidatingSupplier;

@RequiredArgsConstructor
class UseCaseExecutorSupport<I, O, SI, SO> implements UseCaseExecutor<I, O> {
    private final Function<StateTransfer<I>, SI> captureScenario;
    private final Function<SI, ValidatingSupplier<SI>> validate;
    private final Function<ValidatingSupplier<SI>, SO> executeUseCase;
    private final Function<SO, StateTransfer<O>> adaptOutput;

    public UseCaseExecutionOutput<O> apply(StateTransfer<I> scenarioState) {
        SO rawOut = captureScenario.andThen(validate).andThen(executeUseCase).apply(scenarioState);

        return new UseCaseExecutionOutput<O>() {
            @Override
            public <R> R map(Function<StateTransfer<O>, R> mapper) {
                return adaptOutput.andThen(mapper::apply).apply(rawOut);
            }
        };
    }
}
