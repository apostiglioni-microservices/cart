package posti.examples.retail.cart.ports.rest.resources;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import posti.examples.retail.cart.adapters.RemoveItemServiceAdapter;
import posti.examples.retail.cart.adapters.SaveItemServiceAdapter;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
@RequestMapping("/carts/{cartId}/items")
@RequiredArgsConstructor
public class ItemsEndpoints {
    private final SaveItemServiceAdapter saveItemServiceAdapter;
    private final RemoveItemServiceAdapter removeItemServiceAdapter;

    @RequestMapping(path = "/{sku}", method = PUT)
    public ResponseEntity<CartResource> saveItem(
            @RequestBody SaveItemRequestBuilderVisitor body,
            @PathVariable("cartId") UUID cartId,
            @PathVariable("sku") String sku) {

        return saveItemServiceAdapter
                .execute( builder -> builder
                     .withCartId(cartId)
                     .withSku(sku)
                     .withQuantity(body.getQuantity()) )
                .accept(new CartResourceResponseBuilder())
                .status(200)
                .build();
    }

    @RequestMapping(path = "/{sku}", method = DELETE)
    public ResponseEntity<CartResource> removeItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("sku") String sku) {

        return removeItemServiceAdapter
                .execute( builder -> builder
                     .withCartId(cartId)
                     .withSku(sku) )
                .accept(new CartResourceResponseBuilder())
                .status(200)
                .build();
    }
}
