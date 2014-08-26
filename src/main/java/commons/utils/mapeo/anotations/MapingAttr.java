package commons.utils.mapeo.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotaciones que permiten determinar las caracter�sticas de los atributos del mepo
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.FIELD)
public @interface MapingAttr{
	
	/** M�todo setter de origen */
	public abstract String setOrigin();
	
	/** M�todo getter de origen */
	public abstract String getOrigin();
	
	/** M�todos setter de destino */
	public abstract String[] setDestinys() default {};
	
	/** M�todos getter de destino */
	public abstract String[] getDestinys() default {};
	
	/** Clases de destino */
	public abstract Class<?>[] destinyClases() default {};
}
