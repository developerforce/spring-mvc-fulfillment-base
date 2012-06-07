package com.force.example.fulfillment.order.model;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckInvoiceIdValidator.class)
@Documented
public @interface CheckInvoiceId {
    String message() default "{com.force.example.constraints.checkinvoiceid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}