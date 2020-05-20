package io.leego.mypages.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enables pagination capability.
 * @author Yihleego
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface Pagination {

    /**
     * Default page number (One-based).
     * Replaces <code>page</code> with <code>defaultPage</code>
     * if <code>defaultPage</code> is greater than 0 and <code>page</code> is invalid.
     */
    int defaultPage() default -1;

    /**
     * Default page size.
     * Replaces <code>size</code> with <code>defaultSize</code>
     * if <code>defaultSize</code> is greater than 0 and <code>size</code> is invalid.
     */
    int defaultSize() default -1;

    /**
     * Max page number (One-based).
     * Replaces <code>page</code> with <code>maxPage</code>
     * if <code>maxPage</code> is greater than 0 and <code>page</code> is greater than <code>maxPage</code>
     */
    int maxPage() default -1;

    /**
     * Max page size.
     * Replaces <code>size</code> with <code>maxSize</code>
     * if <code>maxSize</code> is greater than 0 and <code>size</code> is greater than <code>maxSize</code>
     */
    int maxSize() default -1;

    /** The counting of the column. The default value is "*". */
    String countColumn() default "";

}
