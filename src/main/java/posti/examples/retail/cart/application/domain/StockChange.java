package posti.examples.retail.cart.application.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class StockChange {
    @NonNull String sku;
    int quantity;
}
