package bg.softuni.jsonprocessingexercise.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;

    public ValidationUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {
        return this.getViolations(entity).isEmpty();
    }

    @Override
    public <E> Set<ConstraintViolation<E>> getViolations(E entity) {
        return this.validator.validate(entity);
    }
}
