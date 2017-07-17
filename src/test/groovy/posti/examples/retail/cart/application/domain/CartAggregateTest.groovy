package posti.examples.retail.cart.application.domain

import posti.examples.retail.cart.application.domain.Cart.CartBuilder
import spock.lang.Specification

import static java.util.UUID.randomUUID
import static posti.examples.retail.cart.application.domain.Event.EventType.CLEARED
import static posti.examples.retail.cart.application.domain.Event.EventType.QUANTITY_CHANGED

class CartAggregateTest extends Specification {
    CartEventsAggregate cartAggregate = new CartEventsAggregate()

    def "should change quantity"() {
        setup:
            def cartId = randomUUID()
            def initialState = Cart.empty(cartId)
            def version = initialState.version + 1
            def event = new Event(QUANTITY_CHANGED, cartId, version, new QuantityChange("sku-1", 4))

        expect:
            cartAggregate.aggregate(initialState, event)  == CartBuilder.buildWith({ it
                                                                .id(event.cartId)
                                                                .version(event.cartVersion)
                                                                .item(new Item("sku-1", 4))
                                                             })
    }

    def "should reject out of order events"() {
        setup:
            def cartId = randomUUID()
            def version = 1
            def initialState = CartBuilder.buildWith { it.id(cartId).version(2l)}
            def event = new Event(QUANTITY_CHANGED, cartId, version, new QuantityChange("sku-1", 2))
        when:
            cartAggregate.aggregate(initialState, event)
        then:
            thrown OutOfOrderException
    }

    def "should be empty when removing value is 0"() {
        setup:
            def cartId = randomUUID()
            def initialState = Cart.empty(cartId)
            def event = new Event(QUANTITY_CHANGED, cartId, 1, new QuantityChange("sku-1", 0))

        expect:
            cartAggregate.aggregate(initialState, event) == CartBuilder.buildWith { it
                                                                .id(event.cartId)
                                                                .version(event.cartVersion)
                                                                .items([])
                                                            }
    }

    def "should clear cart"() {
        setup:
            def cartId = randomUUID()
            def initialState = CartBuilder.buildWith { it.id(cartId).version(1L).item(new Item("sku-1", 3)) }
            def event = Event.builder().cartId(cartId).cartVersion(initialState.version + 1).type(CLEARED).build()

        expect:
            cartAggregate.aggregate(initialState, event) == CartBuilder.buildWith { it
                                                                .id(event.cartId)
                                                                .version(event.cartVersion)
                                                                .items([])
                                                            }
    }
}
