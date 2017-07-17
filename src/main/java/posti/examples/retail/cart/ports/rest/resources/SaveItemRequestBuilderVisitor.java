package posti.examples.retail.cart.ports.rest.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class SaveItemRequestBuilderVisitor {
    private int quantity;

    @JsonCreator
    public SaveItemRequestBuilderVisitor(@JsonProperty("quantity") int quantity) {
        this.quantity = quantity;
    }
}
