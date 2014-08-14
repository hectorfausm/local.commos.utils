package commons.utils;

import java.math.BigDecimal;

/**
 * Clase que contiene métodos de comparación
 * */
public class Comparators{
	
	/**
	 * Método que identifica si un número se encuentra entre dos límites
	 * @param number Número a comparar
	 * @param min Límite mínimo
	 * @param max Límite máximo
	 * @param equalsMin Determina si el número puede ser mayor o igual al límite o no 
	 * @param equalsMax Determina si el número puede ser menor o igual al límite o no
	 * @return Devulve true en caso de que cumpla los límites y false en caso contrario
	 * */
	public static boolean inLimits(Number number, Number min, Number max, 
			boolean equalsMin, boolean equalsMax){
		boolean minLimit;
		boolean maxLimit;
		if(equalsMin){
			minLimit = new BigDecimal(number.toString()).compareTo(new BigDecimal(min.toString()))>=0;
		}else{
			minLimit = new BigDecimal(number.toString()).compareTo(new BigDecimal(min.toString()))>0;
		}
		if(equalsMax){
			maxLimit = new BigDecimal(number.toString()).compareTo(new BigDecimal(max.toString()))<=0;
		}else{
			maxLimit = new BigDecimal(number.toString()).compareTo(new BigDecimal(max.toString()))<0;
		}
		return minLimit && maxLimit;
	}
	
	/**
	 * Método que identifica si un número se encuentra entre dos límites
	 * @param number Número a comparar
	 * @param min Límite mínimo
	 * @param max Límite máximo
	 * @return Devulve true en caso de que cumpla los límites y false en caso contrario
	 * */
	public static boolean inLimits(Number number, Number min, Number max){
		return inLimits(number, min, max, true, true);
	}
	
	/**
	 * Método que identifica si la longiutud de una cadena se encuentra entre dos límites
	 * @param number Cadena a comparar
	 * @param min Límite mínimo
	 * @param max Límite máximo
	 * @return Devulve true en caso de que cumpla los límites y false en caso contrario
	 * */
	public static boolean inLimits(String cadena, Number min, Number max){
		return inLimits(cadena.length(), min, max, true, true);
	}
	
	/**
	 * Método que identifica si la longitud de una cadena se encuentra entre dos límites
	 * @param number Cadena a comparar
	 * @param min Límite mínimo
	 * @param max Límite máximo
	 * @param equalsMin Determina si la longitud puede ser mayor o igual al límite o no 
	 * @param equalsMax Determina si la longitud puede ser menor o igual al límite o no
	 * @return Devulve true en caso de que cumpla los límites y false en caso contrario
	 * */
	public static boolean inLimits(String cadena, Number min, Number max,
			boolean equalsMax, boolean equalsMin){
		return inLimits(cadena.length(), min, max, equalsMax, equalsMin);
	}
}
