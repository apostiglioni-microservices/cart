package posti.examples.retail.cart.ports.rest.resources;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.http.ResponseEntity;
import posti.examples.retail.cart.adapters.CartBuilder;
import posti.examples.retail.cart.adapters.ItemBuilder;
import posti.examples.retail.cart.adapters.ItemBuilderSpec;

public class CartResourceResponseBuilder implements CartBuilder {
    private Long version;
    private UUID id;
    private List<ItemResource> items = new LinkedList<>();

    @Override
    public CartBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public CartBuilder withVersion(Long version) {
        this.version = version;
        return this;
    }

    @Override
    public CartBuilder withItem(ItemBuilderSpec spec) {
        ItemResourceBuilder builder = new ItemResourceBuilder();
        spec.accept(builder);
        items.add(builder.build());
        return this;
    }

    public ResponseEntity<CartResource> build() {
        CartResource resource = new CartResource(id, version, items);
        return ResponseEntity.ok(resource);
    }

    public CartResourceResponseBuilder status(int status) {
        return this;
    }

    private class ItemResourceBuilder implements ItemBuilder {
        private String sku;
        private int quantity;

        @Override
        public ItemBuilder withSku(String sku) {
            this.sku = sku;
            return this;
        }

        @Override
        public ItemBuilder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public ItemResource build() {
            return new ItemResource(sku, quantity);
        }
    }
}
