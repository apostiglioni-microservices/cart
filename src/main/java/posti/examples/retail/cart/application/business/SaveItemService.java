package posti.examples.retail.cart.application.business;

import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import posti.examples.retail.cart.application.domain.Cart;
import posti.examples.retail.cart.application.domain.CartRepository;
import posti.examples.retail.cart.application.domain.Event;
import posti.examples.retail.cart.application.domain.EventFactory;
import posti.examples.retail.cart.application.domain.EventRepository;
import posti.examples.retail.cart.application.domain.QuantityChange;

@RequiredArgsConstructor
public class SaveItemService {
    private final CartRepository cartRepository;
    private final EventRepository eventRepository;
    private final EventFactory eventFactory;

    public Cart accept(ValidatingSupplier<Request> supplier) {
        Request request = supplier.getValidOrFail();

        Event<QuantityChange> event = eventFactory.changeProductQuantity(request.getSku(), request.getQuantity(), request.getCartId());
        eventRepository.save(event);

        return cartRepository.getById(request.getCartId());
    }

    public interface Request {
        @NotNull UUID getCartId();
        @NotNull String getSku();
        @Min(0)  int getQuantity();
    }
}
