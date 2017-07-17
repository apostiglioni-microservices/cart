package posti.examples.retail.cart.application.api;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidatingSupplier<T> {
    private final Validator validator;
    private final T value;

    public T getValidOrFail() {
        Set<ConstraintViolation<T>> violations = validator.validate(value);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        } else {
            return value;
        }
    }
}
