package posti.examples.retail.cart.adapters;

import java.util.UUID;
import javax.validation.Validator;

import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.api.RemoveItemService;
import posti.examples.retail.cart.application.api.ValidatingSupplier;
import posti.examples.retail.cart.application.domain.Cart;

@RequiredArgsConstructor
public class RemoveItemServiceAdapter {
    private final Validator validator;
    private final RemoveItemService removeItemService;

    public CartBuilderSpec execute(RequestBuilderSpec visitor) {
        ValidatingSupplier<RemoveItemService.Request> request = accept(visitor);
        Cart cart = removeItemService.execute(request);
        return new CartAdapter(cart);
    }

    private ValidatingSupplier<RemoveItemService.Request> accept(RequestBuilderSpec visitor) {
        RemoveItemServiceRequestBuilder builder = new RemoveItemServiceRequestBuilder();
        visitor.accept(builder);
        return builder.buildValidatingSupplier(validator);
    }

    @FunctionalInterface
    public interface RequestBuilderSpec {
        void accept(RequestBuilder builder);
    }

    public interface RequestBuilder {
        RequestBuilder withCartId(UUID cartId);
        RequestBuilder withSku(String sku);
    }

    private static class RemoveItemServiceRequestBuilder implements RequestBuilder {
        private UUID cartId;
        private String sku;

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

        private RemoveItemService.Request build() {
            return new RemoveItemService.Request() {
                @Override public UUID getCartId()  { return cartId; }
                @Override public String getSku()   { return sku; }
            };
        }

        private ValidatingSupplier<RemoveItemService.Request> buildValidatingSupplier(Validator validator) {
            RemoveItemService.Request request = build();
            return new ValidatingSupplier<>(validator, request);
        }
    }
}
