package posti.examples.retail.cart.ports.rest.resources;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import posti.examples.retail.cart.adapters.business.CartState;
import posti.examples.retail.cart.adapters.business.ItemState;
import posti.examples.retail.cart.adapters.business.StateTransfer;

public class CartResourceResponseBuilder implements CartState {
    private Long version;
    private UUID id;
    private List<ItemResource> items = new LinkedList<>();

    public ResponseEntity<CartResource> build() {
        CartResource resource = new CartResource(id, version, items);
        return ResponseEntity.ok(resource);
    }

    public CartResourceResponseBuilder status(int status) {
        return this;
    }

    @Override
    public CartResourceResponseBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public CartResourceResponseBuilder withVersion(Long version) {
        this.version = version;
        return this;
    }

    @Override
    public CartResourceResponseBuilder withItem(StateTransfer<ItemState> spec) {
        ItemResourceBuilder builder = new ItemResourceBuilder();
        spec.transferTo(builder);
        items.add(builder.build());
        return this;
    }

    private class ItemResourceBuilder implements ItemState {
        private String sku;
        private int quantity;

        @Override
        public ItemResourceBuilder withSku(String sku) {
            this.sku = sku;
            return this;
        }

        @Override
        public ItemResourceBuilder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public ItemResource build() {
            return new ItemResource(sku, quantity);
        }
    }

    public static ResponseEntity<CartResource> asOkResponse(StateTransfer<CartState> stateTransfer) {
        CartResourceResponseBuilder builder = new CartResourceResponseBuilder();
        builder.status(200);
        stateTransfer.transferTo(builder);
        return builder.build();
    }
}
