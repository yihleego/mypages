package io.leego.mypages.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enables pagination capability
 * @author Yihleego
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface Pagination {

    /** Default page number (One-based) */
    int defaultPage() default -1;

    /** Default page size */
    int defaultSize() default -1;

    /** Max page number (One-based) */
    int maxPage() default -1;

    /** Max page size */
    int maxSize() default -1;

    /**
     * Replaces <code>page</code> with {@link #defaultPage}
     * when following:<pre>
     * 1.reasonable
     * 2.{@link #defaultPage} is greater than 0
     * 3.<code>page</code> is invalid
     * </pre>
     * <p>
     * Replaces <code>size</code> with {@link #defaultSize}
     * when following:<pre>
     * 1.reasonable
     * 2.{@link #defaultSize} is greater than 0
     * 3.<code>size</code> is invalid
     * </pre>
     * <p>
     * Replaces <code>page</code> with {@link #maxPage}
     * when following:<pre>
     * 1.reasonable
     * 2.{@link #maxPage} is greater than 0
     * 3.<code>page</code> is greater than {@link #maxPage}
     * </pre>
     * <p>
     * Replaces <code>size</code> with {@link #maxSize}
     * when following:<pre>
     * 1.reasonable
     * 2.{@link #maxSize} is greater than 0
     * 3.<code>size</code> is greater than {@link #maxSize}
     * </pre>
     */
    boolean reasonable() default false;

    /** The counting of the column. The default value is "*". */
    String countColumn() default "";

}
