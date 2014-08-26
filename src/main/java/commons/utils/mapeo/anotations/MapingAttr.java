package commons.utils.mapeo.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotaciones que permiten determinar las características de los atributos del mepo
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.FIELD)
public @interface MapingAttr{
	
	/** Método setter de origen */
	public abstract String setOrigin();
	
	/** Método getter de origen */
	public abstract String getOrigin();
	
	/** Métodos setter de destino */
	public abstract String[] setDestinys() default {};
	
	/** Métodos getter de destino */
	public abstract String[] getDestinys() default {};
	
	/** Clases de destino */
	public abstract Class<?>[] destinyClases() default {};
}
