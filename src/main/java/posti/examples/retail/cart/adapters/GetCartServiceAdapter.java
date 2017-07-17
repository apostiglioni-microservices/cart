package posti.examples.retail.cart.adapters;

import java.util.UUID;
import javax.validation.Validator;

import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.api.GetCartService;
import posti.examples.retail.cart.application.api.ValidatingSupplier;
import posti.examples.retail.cart.application.domain.Cart;

@RequiredArgsConstructor
public class GetCartServiceAdapter {
    private final Validator validator;
    private final GetCartService getCartService;

    public CartBuilderSpec execute(RequestBuilderSpec visitor) {
        ValidatingSupplier<GetCartService.Request> request = accept(visitor);
        Cart cart = getCartService.execute(request);
        return new CartAdapter(cart);
    }

    private ValidatingSupplier<GetCartService.Request> accept(RequestBuilderSpec visitor) {
        GetCartRequestBuilder builder = new GetCartRequestBuilder();
        visitor.accept(builder);
        return builder.buildValidatingSupplier(validator);
    }

    @FunctionalInterface
    public interface RequestBuilderSpec {
        void accept(RequestBuilder builder);
    }

    @FunctionalInterface
    public interface RequestBuilder {
        RequestBuilder withCartId(UUID cartId);
    }

    private static class GetCartRequestBuilder implements RequestBuilder {
        private UUID cartId;

        public RequestBuilder withCartId(UUID cartId) {
            this.cartId = cartId;
            return this;
        }

        private GetCartService.Request build() {
            return () -> cartId;
        }

        private ValidatingSupplier<GetCartService.Request> buildValidatingSupplier(Validator validator) {
            GetCartService.Request request = build();
            return new ValidatingSupplier<>(validator, request);
        }
    }
}
