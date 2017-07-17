package posti.examples.retail.cart.adapters;

import java.util.UUID;

public interface CartBuilder {
    CartBuilder withId(UUID id);
    CartBuilder withVersion(Long version);
    CartBuilder withItem(ItemBuilderSpec spec);   //TODO: Why consumer???
}
