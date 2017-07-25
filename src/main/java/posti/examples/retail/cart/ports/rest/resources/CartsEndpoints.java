package posti.examples.retail.cart.ports.rest.resources;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import posti.examples.retail.cart.adapters.business.ClearCartUseCaseExecutor;
import posti.examples.retail.cart.adapters.business.GetCartUseCaseExecutor;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartsEndpoints {
    private final GetCartUseCaseExecutor getCartUseCase;
    private final ClearCartUseCaseExecutor clearCartUseCase;

    @RequestMapping(path = "/{cartId}", method = GET)
    public ResponseEntity<CartResource> getCart(@PathVariable("cartId") UUID cartId) {
        return getCartUseCase
                .apply(scenario -> scenario.withCartId(cartId))
                .map(CartResourceResponseBuilder::asOkResponse);
    }

    @RequestMapping(path = "/{cartId}", method = DELETE)
    public ResponseEntity<CartResource> clearCart(@PathVariable("cartId") UUID cartId) {
        return clearCartUseCase
                .apply(scenario -> scenario.withCartId(cartId))
                .map(CartResourceResponseBuilder::asOkResponse);
    }
}
