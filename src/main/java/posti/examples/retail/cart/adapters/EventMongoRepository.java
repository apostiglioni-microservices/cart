package posti.examples.retail.cart.adapters;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.data.repository.RepositoryDefinition;
import posti.examples.retail.cart.application.domain.Event;
import posti.examples.retail.cart.application.domain.EventRepository;

@RepositoryDefinition(domainClass = Event.class, idClass = UUID.class)
public interface EventMongoRepository extends EventRepository {
    List<Event> findByCartIdAndCartVersionGreaterThanOrderByCartVersionAsc(UUID cartId, long cartVersion);

    @Override
    default List<Event> findUpdates(UUID cartId, long cartVersion) {
        return this.findByCartIdAndCartVersionGreaterThanOrderByCartVersionAsc(cartId, cartVersion);
    }

    @Override
    default Stream<Event> streamUpdates(UUID cartId, long cartVersion) {
        return this.findUpdates(cartId, cartVersion).stream();
    }

//    default BiFunction<Cart, BiFunction<? super Cart, ? super Event, ? extends Cart>, Cart> pp(long version, UUID cartId) {
//        return (Cart initialState, BiFunction<? super Cart, ? super Event, ? extends Cart> f) -> {
//            Supplier<Stream<Event>> streamer = () -> this.streamUpdates(cartId, version);
//            try(Stream<Event> events = streamer.get()) {
//                Cart newState = ofAll(events).foldLeft(initialState, f);
//                return newState;
//            }
//        };
//    }
//
//    default <T> BiFunction<T, BiFunction<? super T, ? super Event, ? extends T>, T> foldLeft(UUID cartId, long version) {
//       return foldLeft(() -> this.streamUpdates(cartId, version));
//    }
//
//    default <T> BiFunction<T, BiFunction<? super T, ? super Event, ? extends T>, T> foldLeft(Supplier<Stream<Event>> streamer) {
//        return (initialState, f) -> {
//            try(Stream<Event> events = streamer.get()) {
//                T newState = ofAll(events).foldLeft(initialState, f);
//                return newState;
//            }
//        };
//    }
}
