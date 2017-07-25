package posti.examples.retail.cart.ports.rest.resources;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import posti.examples.retail.cart.adapters.business.RemoveItemUseCaseExecutor;
import posti.examples.retail.cart.adapters.business.SaveItemUseCaseExecutor;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
@RequestMapping("/carts/{cartId}/items")
@RequiredArgsConstructor
public class ItemsEndpoints {
    private final SaveItemUseCaseExecutor saveItemUseCase;
    private final RemoveItemUseCaseExecutor removeItemUseCase;

    @RequestMapping(path = "/{sku}", method = PUT)
    public ResponseEntity<CartResource> saveItem(
            @RequestBody SaveItemRequestBody request,
            @PathVariable("cartId") UUID cartId,
            @PathVariable("sku") String sku) {

        // State mutation is constrained into accept and map. All other things are immutable
        // Resembles pretty much as reactive programming
        return saveItemUseCase
                .apply(scenario -> scenario
                     .withCartId(cartId)
                     .withSku(sku)
                     .withQuantity(request.getQuantity()))
                .map(CartResourceResponseBuilder::asOkResponse);
    }

    @RequestMapping(path = "/{sku}", method = DELETE)
    public ResponseEntity<CartResource> removeItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("sku") String sku) {

        return removeItemUseCase
                .apply(scenario -> scenario
                     .withCartId(cartId)
                     .withSku(sku))
                .map(CartResourceResponseBuilder::asOkResponse);
    }
}
