package posti.examples.retail.cart.ports.rest.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class SaveItemRequestBody {
    private int quantity;

    @JsonCreator
    public SaveItemRequestBody(@JsonProperty("quantity") int quantity) {
        this.quantity = quantity;
    }
}
