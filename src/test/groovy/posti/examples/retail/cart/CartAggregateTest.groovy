package posti.examples.retail.cart

import posti.examples.retail.cart.Cart.CartBuilder
import spock.lang.Specification

import static java.util.UUID.randomUUID
import static posti.examples.retail.cart.Event.EventType.ADD_ITEM
import static posti.examples.retail.cart.Event.EventType.REMOVE_ITEM

class CartAggregateTest extends Specification {
    CartAggregate cartAggregate = new CartAggregate()

    def "should add single item"() {
        setup:
            def userId = randomUUID()
            def initialState = Cart.empty()
            def events = [
               new Event(1l, ADD_ITEM, userId, new StockChange("sku-1", 1))
            ,  new Event(2l, ADD_ITEM, userId, new StockChange("sku-1", 2))
            ,  new Event(3l, ADD_ITEM, userId, new StockChange("sku-1", 3))
            ,  new Event(4l, ADD_ITEM, userId, new StockChange("sku-1", 4))
            ]

        expect:
            cartAggregate.aggregate(initialState, events) == CartBuilder.buildWith({ it
                                                                .version(4l)
                                                                .item(new Item("sku-1", 10))
                                                             })
    }

    def "should reject out of order events"() {
        setup:
            def userId = randomUUID()
            def initialState = CartBuilder.buildWith { it.version(2l)}
            def events = [new Event(4l, ADD_ITEM, userId, new StockChange("sku-1", 2))]
        when:
            cartAggregate.aggregate(initialState, events)
        then:
            thrown OutOfOrderException
    }

    def "should be empty when removing more than added"() {
        setup:
            def userId = randomUUID()
            def initialState = Cart.empty()
            def events = [
              new Event(1l, ADD_ITEM   , userId, new StockChange("sku-1", 1))
            , new Event(2l, REMOVE_ITEM, userId, new StockChange("sku-1", -2))
            ]

        expect:
            cartAggregate.aggregate(initialState, events) == CartBuilder.buildWith { it
                                                                .version(2l)
                                                                .items([])
                                                             }
    }

    def "should add and remove multiple items"() {
        setup:
            def userId = randomUUID()
            def initialState = Cart.empty();
            def events = [
               new Event(1l, ADD_ITEM   , userId, new StockChange("sku-1", 1))
            ,  new Event(2l, ADD_ITEM   , userId, new StockChange("sku-2", 2))
            ,  new Event(3l, ADD_ITEM   , userId, new StockChange("sku-1", 3))
            ,  new Event(4l, ADD_ITEM   , userId, new StockChange("sku-3", 4))
            ,  new Event(5l, REMOVE_ITEM, userId, new StockChange("sku-2", -1))
            ,  new Event(6l, REMOVE_ITEM, userId, new StockChange("sku-1", -1))
            ,  new Event(7l, ADD_ITEM   , userId, new StockChange("sku-2", 4))
            ]

        expect:
            cartAggregate.aggregate(initialState, events) == CartBuilder.buildWith { it
                                                                .version(7l)
                                                                .item(new Item("sku-1", 3))
                                                                .item(new Item("sku-2", 5))
                                                                .item(new Item("sku-3", 4))
                                                             }
    }
}
