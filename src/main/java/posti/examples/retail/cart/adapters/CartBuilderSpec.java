package posti.examples.retail.cart.adapters;

@FunctionalInterface
public interface CartBuilderSpec {
     <T extends CartBuilder> T accept(T builder);
}
