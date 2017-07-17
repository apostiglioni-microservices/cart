package posti.examples.retail.cart.ports.rest.resources;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class CartResource {
    private final UUID id;
    private final Long version;
    private final List<ItemResource> items;
}
