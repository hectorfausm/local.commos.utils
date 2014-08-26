package commons.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase con Utilidades para listas
 * */
public abstract class ListUtils {
	
	/**
	 * Método estático que prove un clon de una lista
	 * @param lista Lista a clonar
	 * @return Devulve el clon de esa lista
	 * */
	public static List cloneArrayList(List lista){
		List<Object> res = new ArrayList<Object>();
		for (Object object : lista) {
			res.add(object);
		}
		return res;
	}
}
