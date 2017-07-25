package posti.examples.retail.cart.adapters.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import posti.examples.retail.cart.application.domain.SequenceProvider;

@Configuration
public class MongoConfiguration {
    @Bean
    public SequenceProvider sequenceProvider(MongoOperations mongoOperations) {
        return new MongoSequenceProvider(mongoOperations);
    }
}
