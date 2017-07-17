package posti.examples.retail.cart.application.api;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.domain.Cart;
import posti.examples.retail.cart.application.domain.CartRepository;

@RequiredArgsConstructor
public class GetCartService {
    private final CartRepository repository;

    public Cart execute(ValidatingSupplier<Request> supplier) {
        Request request = supplier.getValidOrFail();

        return repository.getById(request.getCartId());
    }

    public interface Request {
        @NotNull UUID getCartId();
    }
}
