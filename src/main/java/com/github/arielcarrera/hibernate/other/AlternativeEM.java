package com.github.arielcarrera.hibernate.other;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE })
public @interface AlternativeEM {

    /**
    * Supports inline instantiation of the {@link AlternativeEM} qualifier.
    *
    * @author Martin Kouba
    * @since 2.0
    * @see Instance
    * @see Event
    */
    public static final class Literal extends AnnotationLiteral<AlternativeEM> implements AlternativeEM {

	public static final Literal INSTANCE = new Literal();

	private static final long serialVersionUID = 1L;

    }
}