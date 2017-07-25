package posti.examples.retail.cart.application.business;

import java.util.UUID;
import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.domain.Cart;
import posti.examples.retail.cart.application.domain.CartRepository;
import posti.examples.retail.cart.application.domain.Event;
import posti.examples.retail.cart.application.domain.EventFactory;
import posti.examples.retail.cart.application.domain.EventRepository;

@RequiredArgsConstructor
public class ClearCartService {
    private final CartRepository cartRepository;
    private final EventRepository eventRepository;
    private final EventFactory eventFactory;

    public Cart accept(ValidatingSupplier<Request> supplier) {
        Request request = supplier.getValidOrFail();

        Event<Void> event = eventFactory.clear(request.getCartId());
        eventRepository.save(event);

        return cartRepository.getById(request.getCartId());
    }

    public interface Request {
        @NotNull UUID getCartId();
    }
}
