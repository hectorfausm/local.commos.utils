package commons.utils.mapeo.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Anotaci�n que permite deterinar que una clase tiene la definici�n del mapeo
 * */
@Retention(RetentionPolicy.RUNTIME)
public @interface Maping {
	
	/** Determina la clase a la que ser� mapeado el objeto */
	public abstract Class<?> destinyClass();
	
	/** Determina la clase de la cual hereda la clase anotada, por defecto es {@link Object} */
	public abstract Class<?> inheritanceClass() default Object.class;
}
