package posti.examples.retail.cart;

public class OutOfOrderException extends RuntimeException {
    public OutOfOrderException(String message) {
        super(message);
    }
}
