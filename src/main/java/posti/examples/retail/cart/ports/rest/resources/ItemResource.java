package posti.examples.retail.cart.ports.rest.resources;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class ItemResource {
    private String sku;
    private int quantity;
}
