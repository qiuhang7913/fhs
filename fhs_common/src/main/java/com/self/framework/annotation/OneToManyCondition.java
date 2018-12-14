package com.self.framework.annotation;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface OneToManyCondition {

    String conditionFile() default "";

}
