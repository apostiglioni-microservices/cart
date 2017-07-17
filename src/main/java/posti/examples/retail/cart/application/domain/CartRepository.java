package posti.examples.retail.cart.application.domain;

import java.util.UUID;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;

import static io.vavr.collection.Stream.ofAll;

@RequiredArgsConstructor
public class CartRepository {
    private final CartCache cache;
    private final EventRepository eventRepository;
    private final CartEventsAggregate aggregate;

    public Cart getById(UUID id) {
        Cart initialState = cache.get(id).orElse(Cart.empty(id));

        try(Stream<Event> events = eventRepository.streamUpdates(id, initialState.getVersion())) {
            Cart newState = ofAll(events).foldLeft(initialState, aggregate::aggregate);
            cache.put(id, newState);
            return newState;
        }
    }
}
