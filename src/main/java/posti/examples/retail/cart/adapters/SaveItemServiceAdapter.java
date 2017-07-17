package posti.examples.retail.cart.adapters;

import java.util.UUID;
import javax.validation.Validator;

import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.api.SaveItemService;
import posti.examples.retail.cart.application.api.ValidatingSupplier;
import posti.examples.retail.cart.application.domain.Cart;

@RequiredArgsConstructor
public class SaveItemServiceAdapter {
    private final Validator validator;
    private final SaveItemService saveItemService;

    public CartBuilderSpec execute(RequestBuilderSpec visitor) {
        ValidatingSupplier<SaveItemService.Request> request = accept(visitor);
        Cart cart = saveItemService.execute(request);
        return new CartAdapter(cart);
    }

    private ValidatingSupplier<SaveItemService.Request> accept(RequestBuilderSpec visitor) {
        SaveItemServiceRequestBuilder builder = new SaveItemServiceRequestBuilder();
        visitor.accept(builder);
        return builder.buildValidatingSupplier(validator);
    }

    @FunctionalInterface
    public interface RequestBuilderSpec {
        void accept(RequestBuilder builder);
    }

    public interface RequestBuilder {
        RequestBuilder withCartId(UUID cartId);
        RequestBuilder withQuantity(int quantity);
        RequestBuilder withSku(String sku);
    }

    private static class SaveItemServiceRequestBuilder implements RequestBuilder {
        private UUID cartId;
        private String sku;
        private int quantity;

        @Override
        public RequestBuilder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        @Override
        public RequestBuilder withSku(String sku) {
            this.sku = sku;
            return this;
        }

        @Override
        public RequestBuilder withCartId(UUID cartId) {
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

        private ValidatingSupplier<SaveItemService.Request> buildValidatingSupplier(Validator validator) {
            SaveItemService.Request request = build();
            return new ValidatingSupplier<>(validator, request);
        }
    }
}
