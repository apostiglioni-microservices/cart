package posti.examples.retail.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import posti.examples.retail.cart.application.domain.SequenceProvider;
import posti.examples.retail.cart.application.business.ClearCartService;
import posti.examples.retail.cart.application.business.GetCartService;
import posti.examples.retail.cart.application.business.RemoveItemService;
import posti.examples.retail.cart.application.business.SaveItemService;
import posti.examples.retail.cart.application.domain.CartCache;
import posti.examples.retail.cart.application.domain.CartEventsAggregate;
import posti.examples.retail.cart.application.domain.CartRepository;
import posti.examples.retail.cart.application.domain.EventFactory;
import posti.examples.retail.cart.application.domain.EventRepository;
import posti.examples.retail.cart.adapters.mongo.MongoSequenceProvider;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
