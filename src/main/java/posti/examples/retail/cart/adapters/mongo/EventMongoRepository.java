package posti.examples.retail.cart.adapters.mongo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.data.repository.RepositoryDefinition;
import posti.examples.retail.cart.application.domain.Event;
import posti.examples.retail.cart.application.domain.EventRepository;

@RepositoryDefinition(domainClass = Event.class, idClass = UUID.class)
interface EventMongoRepository extends EventRepository {
    List<Event> findByCartIdAndCartVersionGreaterThanOrderByCartVersionAsc(UUID cartId, long cartVersion);

    @Override
    default List<Event> findUpdates(UUID cartId, long cartVersion) {
        return this.findByCartIdAndCartVersionGreaterThanOrderByCartVersionAsc(cartId, cartVersion);
    }

    @Override
    default Stream<Event> streamUpdates(UUID cartId, long cartVersion) {
        return this.findUpdates(cartId, cartVersion).stream();
    }
}
