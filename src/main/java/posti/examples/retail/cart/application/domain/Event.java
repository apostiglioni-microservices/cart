package posti.examples.retail.cart.application.domain;

import java.util.UUID;

import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
public class Event<D> {
    enum EventType {
        ADD_ITEM,
        REMOVE_ITEM,
        CLEAR
    }

    @Id @NonNull
    Long id;

    @NonNull
    EventType type;

    @NonNull
    UUID userId;

    D data;
}
