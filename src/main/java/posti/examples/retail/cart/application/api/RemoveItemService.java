package posti.examples.retail.cart.application.api;

import java.util.UUID;
import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.domain.Cart;
import posti.examples.retail.cart.application.domain.CartRepository;
import posti.examples.retail.cart.application.domain.Event;
import posti.examples.retail.cart.application.domain.EventFactory;
import posti.examples.retail.cart.application.domain.EventRepository;
import posti.examples.retail.cart.application.domain.QuantityChange;

@RequiredArgsConstructor
public class RemoveItemService {
    private final CartRepository cartRepository;
    private final EventRepository eventRepository;
    private final EventFactory eventFactory;

    public Cart execute(ValidatingSupplier<Request> supplier) {
        Request request = supplier.getValidOrFail();

        Event<QuantityChange> event = eventFactory.changeProductQuantity(request.getSku(), 0, request.getCartId());
        eventRepository.save(event);

        return cartRepository.getById(request.getCartId());
    }

    public interface Request {
        @NotNull UUID getCartId();
        @NotNull String getSku();
    }
}
