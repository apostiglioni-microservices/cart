package posti.examples.retail.cart.application.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class QuantityChange {
    @NonNull String sku;
    int quantity;
}
