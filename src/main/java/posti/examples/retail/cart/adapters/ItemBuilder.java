package posti.examples.retail.cart.adapters;

public interface ItemBuilder {
    ItemBuilder withSku(String sku);
    ItemBuilder withQuantity(int quantity);
}
