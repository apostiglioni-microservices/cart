package posti.examples.retail.cart.adapters.business;

import java.util.function.Function;
import javax.validation.Validator;

import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.business.ValidatingSupplier;

import static java.lang.reflect.Proxy.newProxyInstance;

@RequiredArgsConstructor
class UseCaseExecutorFactory<T extends UseCaseExecutor> {
    private final Class<T> type;

    static <E extends UseCaseExecutor> UseCaseExecutorFactory<E> create(Class<E> type) {
        return new UseCaseExecutorFactory<>(type);
    }

    <I, SI, SO, O> T as(Function<TypeHolderState<T>, ProxyCreatorState<T, I, SI, SO, O>> specs) {
        return specs.apply(new TypeHolderState<T>(type)).create();
    }

    @RequiredArgsConstructor
    static class ProxyCreatorState<T extends UseCaseExecutor, I, SI, SO, O> {
        private final Class<T> type;
        private final Function<StateTransfer<I>, SI> applyScenario;
        private final Function<SI, ValidatingSupplier<SI>> validate;
        private final Function<ValidatingSupplier<SI>, SO> executeUseCase;
        private final Function<SO, StateTransfer<O>> captureOutput;

        @SuppressWarnings("unchecked")
        private T create() {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            UseCaseExecutorSupport<I, O, SI, SO> executorSupport =
                    new UseCaseExecutorSupport<>(applyScenario, validate, executeUseCase, captureOutput);

            return (T) newProxyInstance(classLoader, new Class[]{ type },
                    (proxy, method, args) -> method.invoke(executorSupport, args));
        }
    }

    @RequiredArgsConstructor
    static class UseCaseExecutorHolderState<T extends UseCaseExecutor, I, SI, SO> {
        private final Class<T> type;
        private final Function<StateTransfer<I>, SI> applyScenario;
        private final Function<SI, ValidatingSupplier<SI>> validate;
        private final Function<ValidatingSupplier<SI>, SO> executeUseCase;

        <O> ProxyCreatorState<T, I, SI, SO, O> captureOutputState(Function<SO, StateTransfer<O>> captureOutput) {
            return new ProxyCreatorState<>(type, applyScenario, validate, executeUseCase, captureOutput);
        }
    }

    @RequiredArgsConstructor
    static class ValidatorHolderState<T extends UseCaseExecutor, I, SI> {
        private final Class<T> type;
        private final Function<StateTransfer<I>, SI> applyScenario;
        private final Function<SI, ValidatingSupplier<SI>> validate;

        <SO> UseCaseExecutorHolderState<T, I, SI, SO> executeUseCase(Function<ValidatingSupplier<SI>, SO> exec) {
            return new UseCaseExecutorHolderState<>(type, applyScenario, validate, exec);
        }
    }

    @RequiredArgsConstructor
    static class ScenarioApplierHolderState<T extends UseCaseExecutor, I, SI> {
        private final Class<T> type;
        private final Function<StateTransfer<I>, SI> applyScenario;

        ValidatorHolderState<T, I, SI> validateWith(Validator validator) {
            Function<SI, ValidatingSupplier<SI>> validate = (scenario) -> new ValidatingSupplier<>(validator, scenario);
            return new ValidatorHolderState<>(type, applyScenario, validate);
        }
    }

    @RequiredArgsConstructor
    static class TypeHolderState<T extends UseCaseExecutor> {
        private final Class<T> type;

        <I, SI> ScenarioApplierHolderState<T, I, SI> applyScenarioState(Function<StateTransfer<I>, SI> apply) {
            return new ScenarioApplierHolderState<>(type, apply);
        }
    }
}
