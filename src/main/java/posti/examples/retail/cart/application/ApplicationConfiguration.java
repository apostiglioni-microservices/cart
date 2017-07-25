package posti.examples.retail.cart.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import posti.examples.retail.cart.application.business.ClearCartService;
import posti.examples.retail.cart.application.business.GetCartService;
import posti.examples.retail.cart.application.business.RemoveItemService;
import posti.examples.retail.cart.application.business.SaveItemService;
import posti.examples.retail.cart.application.domain.CartCache;
import posti.examples.retail.cart.application.domain.CartEventsAggregate;
import posti.examples.retail.cart.application.domain.CartRepository;
import posti.examples.retail.cart.application.domain.EventFactory;
import posti.examples.retail.cart.application.domain.EventRepository;
import posti.examples.retail.cart.application.domain.SequenceProvider;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public CartRepository repository(CartCache cache, EventRepository eventRepository, CartEventsAggregate aggregate) {
        return new CartRepository(cache, eventRepository, aggregate);
    }

    @Bean
    public CartEventsAggregate aggregate() {
        return new CartEventsAggregate();
    }

    @Bean
    public CartCache cartCache() {
        return new CartCache();
    }

    @Bean
    public RemoveItemService removeItemService(CartRepository cartRepository, EventRepository eventRepository, EventFactory eventFactory) {
        return new RemoveItemService(cartRepository, eventRepository, eventFactory);
    }

    @Bean
    public SaveItemService saveItemService(CartRepository cartRepository, EventRepository eventRepository, EventFactory eventFactory) {
        return new SaveItemService(cartRepository, eventRepository, eventFactory);
    }

    @Bean
    public EventFactory eventFactory(SequenceProvider sequenceProvider) {
        return new EventFactory(sequenceProvider);
    }


    @Bean
    public ClearCartService clearCartService(CartRepository cartRepository, EventRepository eventRepository, EventFactory eventFactory) {
        return new ClearCartService(cartRepository, eventRepository, eventFactory);
    }

    @Bean
    public GetCartService getCartService(CartRepository repository) {
        return new GetCartService(repository);
    }
}
