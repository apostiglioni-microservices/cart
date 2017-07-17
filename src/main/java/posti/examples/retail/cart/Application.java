package posti.examples.retail.cart;

import javax.validation.Validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import posti.examples.retail.cart.adapters.ClearCartSerivceAdapter;
import posti.examples.retail.cart.adapters.SaveItemServiceAdapter;
import posti.examples.retail.cart.adapters.GetCartServiceAdapter;
import posti.examples.retail.cart.adapters.RemoveItemServiceAdapter;
import posti.examples.retail.cart.application.api.ClearCartService;
import posti.examples.retail.cart.application.api.SaveItemService;
import posti.examples.retail.cart.application.api.GetCartService;
import posti.examples.retail.cart.application.api.RemoveItemService;
import posti.examples.retail.cart.application.domain.CartCache;
import posti.examples.retail.cart.application.domain.CartEventsAggregate;
import posti.examples.retail.cart.application.domain.CartRepository;
import posti.examples.retail.cart.application.domain.EventFactory;
import posti.examples.retail.cart.application.domain.EventRepository;
import posti.examples.retail.store.sequence.SequenceProvider;

@SpringBootApplication
public class Application {
    @Configuration
    public static class ApplicationConfiguration {
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
        public SaveItemServiceAdapter saveItemServiceAddapter(
                Validator validator, SaveItemService service) {
            return new SaveItemServiceAdapter(validator, service);
        }

        @Bean
        public ClearCartSerivceAdapter clearCartServiceAddapter(
                Validator validator, ClearCartService service) {
            return new ClearCartSerivceAdapter(validator, service);
        }

        @Bean
        public RemoveItemServiceAdapter removeItemServiceAddapter(
                Validator validator, RemoveItemService service) {
            return new RemoveItemServiceAdapter(validator, service);
        }

        @Bean
        public GetCartServiceAdapter getCartByIdServiceAdapter(
                Validator validator, GetCartService service) {
            return new GetCartServiceAdapter(validator, service);
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

        @Bean SequenceProvider sequenceProvider(MongoOperations mongoOperations) {
            return new SequenceProvider(mongoOperations);
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

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
