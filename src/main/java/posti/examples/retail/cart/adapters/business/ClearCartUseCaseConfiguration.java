package posti.examples.retail.cart.adapters.business;

import java.util.UUID;
import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import posti.examples.retail.cart.application.business.ClearCartService;

@Configuration
public class ClearCartUseCaseConfiguration {
    @Bean
    public ClearCartUseCaseExecutor clearCartServiceAddapter(Validator validator, ClearCartService service) {
        return UseCaseExecutorFactory
                   .create(ClearCartUseCaseExecutor.class)
                   .as(builder -> builder
                       .applyScenarioState(ClearCartRequestBuilder::buildFromState)
                       .validateWith(validator)
                       .executeUseCase(service::accept)
                       .captureOutputState(CartStateTransfer::captureState)
                   );
    }

    private static class ClearCartRequestBuilder implements ClearCartUseCaseExecutor.ScenarioState {
        private UUID cartId;

        @Override
        public ClearCartUseCaseExecutor.ScenarioState withCartId(UUID cartId) {
            this.cartId = cartId;
            return this;
        }

        private ClearCartService.Request build() {
            return () -> cartId;
        }

        public static ClearCartService.Request buildFromState(
                StateTransfer<ClearCartUseCaseExecutor.ScenarioState> scenarioStateTransfer) {

            ClearCartRequestBuilder builder = new ClearCartRequestBuilder();
            scenarioStateTransfer.transferTo(builder);

            return builder.build();
        }
    }
}
