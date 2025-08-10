package org.markdowner.api.domain.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import org.markdowner.api.domain.validation.Year.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

@Constraint(validatedBy = Validation.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Year {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default Integer.MIN_VALUE;

    int max() default Integer.MAX_VALUE;

    boolean nullable() default true;

    class Validation implements ConstraintValidator<Year, LocalDate> {

        private Year year;

        @Override
        public void initialize(final Year year) {
            this.year = year;
        }

        @Override
        public boolean isValid(final LocalDate targetDate, final ConstraintValidatorContext context) {
            if (targetDate == null) {
                return year.nullable();
            }
            final var currentDate = LocalDate.now(ZoneId.systemDefault());
            final var years = Period.between(targetDate, currentDate).getYears();
            return years >= year.min() && years <= year.max();
        }

    }

}
