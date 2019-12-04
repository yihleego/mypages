package io.leego.mypages.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Is used to specify a mapped column for counting property or field. Defaults to "*".
 * @author Yihleego
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface CountColumn {

    /** Uses <code>defaultValue</code> if current field is absent */
    String defaultValue() default "";

}
