package posti.examples.retail.cart;

import java.util.function.Consumer;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Item {
    String sku; // TODO change to product
    int quantity;

    public static Item build(Consumer<ItemBuilder> consumer) {
        ItemBuilder builder = builder();
        consumer.accept(builder);
        return builder.build();
    }
}
