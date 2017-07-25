package posti.examples.retail.cart.adapters.business;

import java.util.UUID;
import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import posti.examples.retail.cart.application.business.SaveItemService;

@Configuration
public class SaveItemUseCaseConfiguration {
    @Bean
    public SaveItemUseCaseExecutor saveItemServiceAddapter(Validator validator, SaveItemService service) {

        return UseCaseExecutorFactory
                .create(SaveItemUseCaseExecutor.class)
                .as(builder -> builder
                    .applyScenarioState(SaveItemRequestBuilder::buildFromState)
                    .validateWith(validator)
                    .executeUseCase(service::accept)
                    .captureOutputState(CartStateTransfer::captureState)
                );
    }

    private static class SaveItemRequestBuilder implements SaveItemUseCaseExecutor.ScenarioState {
        private UUID cartId;
        private String sku;
        private int quantity;

        @Override
        public SaveItemUseCaseExecutor.ScenarioState withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        @Override
        public SaveItemUseCaseExecutor.ScenarioState withSku(String sku) {
            this.sku = sku;
            return this;
        }

        @Override
        public SaveItemUseCaseExecutor.ScenarioState withCartId(UUID cartId) {
            this.cartId = cartId;
            return this;
        }

        private SaveItemService.Request build() {
            return new SaveItemService.Request() {
                @Override public UUID getCartId()  { return cartId; }
                @Override public String getSku()   { return sku; }
                @Override public int getQuantity() { return quantity; }
            };
        }

        public static SaveItemService.Request buildFromState(
                StateTransfer<SaveItemUseCaseExecutor.ScenarioState> scenarioStateTransfer) {

            SaveItemRequestBuilder builder = new SaveItemRequestBuilder();
            scenarioStateTransfer.transferTo(builder);

            return builder.build();
        }
    }
}
