package fr.jhelp.compose.math.anotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates a time in milliseconds
 */
@Documented
@Retention(CLASS)
@Target({PARAMETER, METHOD, LOCAL_VARIABLE, FIELD})
public @interface Milliseconds {
}
