package posti.examples.retail.cart.application.domain;

import java.util.UUID;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import posti.examples.retail.store.sequence.SequenceProvider;

import static java.lang.String.format;
import static posti.examples.retail.cart.application.domain.Event.EventType.QUANTITY_CHANGED;
import static posti.examples.retail.cart.application.domain.Event.EventType.CLEARED;

@RequiredArgsConstructor
public class EventFactory {
    @NonNull
    private final SequenceProvider sequenceProvider;

    public Event<QuantityChange> changeProductQuantity(String sku, int quantity, UUID cartId) {
        long cartVersion = nextVersion(cartId);
        return new Event<>(QUANTITY_CHANGED, cartId, cartVersion, new QuantityChange(sku, quantity));
    }

    public Event<Void> clear(UUID cartId) {
        long cartVersion = nextVersion(cartId);
        return new Event<>(CLEARED, cartId, cartVersion, null);
    }

    private long nextVersion(UUID cartId) {
        return sequenceProvider.next(format("%s#%s", Event.class.getName(), cartId));
    }
}
