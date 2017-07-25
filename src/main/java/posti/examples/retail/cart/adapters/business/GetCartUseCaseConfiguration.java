package posti.examples.retail.cart.adapters.business;

import java.util.UUID;
import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import posti.examples.retail.cart.application.business.GetCartService;

@Configuration
public class GetCartUseCaseConfiguration {
    @Bean
    public GetCartUseCaseExecutor getCartByIdServiceAdapter(Validator validator, GetCartService service) {
        return UseCaseExecutorFactory
                .create(GetCartUseCaseExecutor.class)
                .as(builder -> builder
                        .applyScenarioState(GetCartRequestBuilder::buildFromState)
                        .validateWith(validator)
                        .executeUseCase(service::accept)
                        .captureOutputState(CartStateTransfer::captureState)
                );
    }

    private static class GetCartRequestBuilder implements GetCartUseCaseExecutor.Scenario {
        private UUID cartId;

        @Override
        public GetCartUseCaseExecutor.Scenario withCartId(UUID cartId) {
            this.cartId = cartId;
            return this;
        }

        private GetCartService.Request build() {
            return () -> cartId;
        }

        public static GetCartService.Request buildFromState(
                StateTransfer<GetCartUseCaseExecutor.Scenario> scenarioStateTransfer) {

            GetCartRequestBuilder builder = new GetCartRequestBuilder();
            scenarioStateTransfer.transferTo(builder);

            return builder.build();
        }
    }
}
