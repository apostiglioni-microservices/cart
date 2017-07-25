package posti.examples.retail.cart.adapters.business;

@FunctionalInterface
public interface StateTransfer<T> {
    void transferTo(T target);
}
