package posti.examples.retail.cart.adapters.business;

import java.util.function.Function;

@FunctionalInterface
public interface UseCaseExecutionOutput<B> {
    <R> R map(Function<StateTransfer<B>, R> mapper);
}
