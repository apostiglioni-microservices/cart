package posti.examples.retail.cart.application.domain;

public class OutOfOrderException extends RuntimeException {
    public OutOfOrderException(String message) {
        super(message);
    }
}
