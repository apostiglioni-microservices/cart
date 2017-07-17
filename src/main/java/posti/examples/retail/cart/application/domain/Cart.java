package posti.examples.retail.cart.application.domain;

import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import static java.util.Collections.emptySet;
import static lombok.AccessLevel.PRIVATE;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = PRIVATE)
public class Cart {
    @NonNull

    private UUID id;

    @NonNull
    private Long version;

    @NonNull @Singular
    private Set<Item> items;

    public static Cart empty(UUID id) {
        return new Cart(id, 0L, emptySet());
    }

    public static class CartBuilder {
        public static Cart buildWith(Consumer<CartBuilder> consumer) {
            CartBuilder builder = builder();
            consumer.accept(builder);
            return builder.build();
        }
    }
}
