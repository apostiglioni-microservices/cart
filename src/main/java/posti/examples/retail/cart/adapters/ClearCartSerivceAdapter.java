package posti.examples.retail.cart.adapters;

import java.util.UUID;
import javax.validation.Validator;

import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.api.ClearCartService;
import posti.examples.retail.cart.application.api.ValidatingSupplier;
import posti.examples.retail.cart.application.domain.Cart;

@RequiredArgsConstructor
public class ClearCartSerivceAdapter {
    private final Validator validator;
    private final ClearCartService clearCartService;

    public CartBuilderSpec execute(RequestBuilderSpec visitor) {
        ValidatingSupplier<ClearCartService.Request> request = accept(visitor);
        Cart cart = clearCartService.execute(request);
        return new CartAdapter(cart);
    }

    private ValidatingSupplier<ClearCartService.Request> accept(RequestBuilderSpec visitor) {
        ClearCartServiceRequestBuilder builder = new ClearCartServiceRequestBuilder();
        visitor.accept(builder);
        return builder.buildValidatingSupplier(validator);
    }

    @FunctionalInterface
    public interface RequestBuilderSpec {
        void accept(RequestBuilder builder);
    }

    public interface RequestBuilder {
        RequestBuilder withCartId(UUID cartId);
    }

    private static class ClearCartServiceRequestBuilder implements RequestBuilder {
        private UUID cartId;

        @Override
        public RequestBuilder withCartId(UUID cartId) {
            this.cartId = cartId;
            return this;
        }

        private ClearCartService.Request build() {
            return () -> cartId;
        }

        private ValidatingSupplier<ClearCartService.Request> buildValidatingSupplier(Validator validator) {
            ClearCartService.Request request = build();
            return new ValidatingSupplier<>(validator, request);
        }
    }
}
