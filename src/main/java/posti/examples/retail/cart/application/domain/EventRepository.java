package posti.examples.retail.cart.application.domain;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface EventRepository {
    <T> Event<T> save(Event<T> event);
    List<Event> findUpdates(UUID cartId, long version);
    Stream<Event> streamUpdates(UUID cartId, long version);
}
