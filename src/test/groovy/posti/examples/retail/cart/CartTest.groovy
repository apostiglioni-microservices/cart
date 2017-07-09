package posti.examples.retail.cart

import spock.lang.Specification
import posti.examples.retail.cart.Cart.CartBuilder


class CartTest extends Specification {
    def "empty cart should be version 0 and without items"() {
        expect:
            cart.version == 0
            cart.items.isEmpty()
        where:
            cart = Cart.empty()
    }

    def "should build immutable"(cart) {
        when:
            cart.items.add(new Item("sku", 1))

        then:
            thrown(UnsupportedOperationException)

        where:
            cart << [
               Cart.empty()
            ,  CartBuilder.buildWith({ it.version(0).items(new HashSet()) })
            ]
    }
}
