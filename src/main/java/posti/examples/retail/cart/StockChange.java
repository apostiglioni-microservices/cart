package posti.examples.retail.cart;

import lombok.NonNull;
import lombok.Value;

@Value
public class StockChange {
    @NonNull String sku;
    int quantity;
}
