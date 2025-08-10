package org.markdowner.api.domain.validation.profile;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@NotBlank
@Size(max = Name.MAX_SIZE, message = "o comprimento deve ser menor ou igual a " + Name.MAX_SIZE)
@Pattern(regexp = "^[a-zA-Zà-ÿÀ-ß]+([ ''-][a-zA-Zà-ÿÀ-ß]+)*$", message = "deve conter apenas letras, espaços, apóstrofos e hífens.")
@Constraint(validatedBy = {})
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {

    static final int MAX_SIZE = 30;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Builder(access = AccessLevel.PRIVATE)
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    final class Validation {
        private final @Name String name;

        public static String validate(final String target) {
            try (final var factory = buildDefaultValidatorFactory()) {
                final var validations = factory.getValidator().validate(builder().name(target).build());
                if (!validations.isEmpty()) {
                    throw new ConstraintViolationException(validations);
                }
            }
            return target;
        }

    }

}