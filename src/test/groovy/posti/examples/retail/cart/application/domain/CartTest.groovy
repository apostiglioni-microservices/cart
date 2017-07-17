package posti.examples.retail.cart.application.domain

import posti.examples.retail.cart.application.domain.Cart.CartBuilder
import spock.lang.Specification

import static java.util.UUID.fromString
import static java.util.UUID.randomUUID

class CartTest extends Specification {
    def "empty cart should be version 0 and without items"() {
        expect:
            cart.version == 0
            cart.items.isEmpty()
        where:
            cart = Cart.empty(randomUUID())
    }

    def "should build immutable"(cart) {
        setup:
            def id = randomUUID()
        when:
            cart.items.add(new Item("sku", 1))

        then:
            thrown(UnsupportedOperationException)

        where:
            cart << [
               Cart.empty(fromString('524e0937-24f5-845d-ae6d-fe08d7ef90a8'))
            ,  CartBuilder.buildWith({ it
                    .id(fromString('524e0937-24f5-845d-ae6d-fe08d7ef90a8'))
                    .version(0)
                    .items(new HashSet())
               })
            ]
    }
}
