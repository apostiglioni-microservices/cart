package posti.examples.retail.cart.adapters.business;

public interface ItemState {
    ItemState withSku(String sku);
    ItemState withQuantity(int quantity);
}
