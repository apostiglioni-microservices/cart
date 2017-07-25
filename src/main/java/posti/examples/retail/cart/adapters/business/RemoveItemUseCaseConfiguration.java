package posti.examples.retail.cart.adapters.business;

import java.util.UUID;
import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import posti.examples.retail.cart.application.business.RemoveItemService;

@Configuration
public class RemoveItemUseCaseConfiguration {
    @Bean
    public RemoveItemUseCaseExecutor removeItemServiceAddapter(Validator validator, RemoveItemService service) {
        return UseCaseExecutorFactory
                .create(RemoveItemUseCaseExecutor.class)
                .as(builder -> builder
                        .applyScenarioState(RemoveItemRequestBuilder::buildFromState)
                        .validateWith(validator)
                        .executeUseCase(service::accept)
                        .captureOutputState(CartStateTransfer::captureState)
                );
    }

    private static class RemoveItemRequestBuilder implements RemoveItemUseCaseExecutor.Scenario {
        private UUID cartId;
        public String sku;

        @Override
        public RemoveItemRequestBuilder withCartId(UUID cartId) {
            this.cartId = cartId;
            return this;
        }

        @Override
        public RemoveItemRequestBuilder withSku(String sku) {
            this.sku = sku;
            return this;
        }

        private RemoveItemService.Request build() {
            return new RemoveItemService.Request() {
                @Override
                public UUID getCartId() {
                    return cartId;
                }

                @Override
                public String getSku() {
                    return sku;
                }
            };
        }

        public static RemoveItemService.Request buildFromState(
                StateTransfer<RemoveItemUseCaseExecutor.Scenario> scenarioStateTransfer) {

            RemoveItemRequestBuilder builder = new RemoveItemRequestBuilder();
            scenarioStateTransfer.transferTo(builder);

            return builder.build();
        }
    }
}
