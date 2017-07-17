package posti.examples.retail.cart.adapters;

@FunctionalInterface
public interface ItemBuilderSpec {
    void accept(ItemBuilder itemBuilder);
}
