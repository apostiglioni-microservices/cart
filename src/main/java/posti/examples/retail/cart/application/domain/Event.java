package posti.examples.retail.cart.application.domain;

import java.util.UUID;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
@Document
public class Event<D> {
    public enum EventType {
        QUANTITY_CHANGED,
        ITEM_REMOVED,
        CART_CLEARED
    }

    @NonNull
    EventType type;

    @NonNull
    UUID cartId;

    @NonNull Long cartVersion;

    D data;
}
