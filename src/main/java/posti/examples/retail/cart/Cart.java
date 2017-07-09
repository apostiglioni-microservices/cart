package posti.examples.retail.cart;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
    private Long version;

    @NonNull @Singular
    private Set<Item> items;

    public static Cart empty() {
        return new Cart(0l, emptySet());
    }

    public static class CartBuilder {
        public static Cart buildWith(Consumer<CartBuilder> consumer) {
            CartBuilder builder = builder();
            consumer.accept(builder);
            return builder.build();
        }
    }
}
