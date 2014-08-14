package commons.utils;

import java.math.BigDecimal;

/**
 * Clase que contiene m�todos de comparaci�n
 * */
public class Comparators{
	
	/**
	 * M�todo que identifica si un n�mero se encuentra entre dos l�mites
	 * @param number N�mero a comparar
	 * @param min L�mite m�nimo
	 * @param max L�mite m�ximo
	 * @param equalsMin Determina si el n�mero puede ser mayor o igual al l�mite o no 
	 * @param equalsMax Determina si el n�mero puede ser menor o igual al l�mite o no
	 * @return Devulve true en caso de que cumpla los l�mites y false en caso contrario
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
	 * M�todo que identifica si un n�mero se encuentra entre dos l�mites
	 * @param number N�mero a comparar
	 * @param min L�mite m�nimo
	 * @param max L�mite m�ximo
	 * @return Devulve true en caso de que cumpla los l�mites y false en caso contrario
	 * */
	public static boolean inLimits(Number number, Number min, Number max){
		return inLimits(number, min, max, true, true);
	}
	
	/**
	 * M�todo que identifica si la longiutud de una cadena se encuentra entre dos l�mites
	 * @param number Cadena a comparar
	 * @param min L�mite m�nimo
	 * @param max L�mite m�ximo
	 * @return Devulve true en caso de que cumpla los l�mites y false en caso contrario
	 * */
	public static boolean inLimits(String cadena, Number min, Number max){
		return inLimits(cadena.length(), min, max, true, true);
	}
	
	/**
	 * M�todo que identifica si la longitud de una cadena se encuentra entre dos l�mites
	 * @param number Cadena a comparar
	 * @param min L�mite m�nimo
	 * @param max L�mite m�ximo
	 * @param equalsMin Determina si la longitud puede ser mayor o igual al l�mite o no 
	 * @param equalsMax Determina si la longitud puede ser menor o igual al l�mite o no
	 * @return Devulve true en caso de que cumpla los l�mites y false en caso contrario
	 * */
	public static boolean inLimits(String cadena, Number min, Number max,
			boolean equalsMax, boolean equalsMin){
		return inLimits(cadena.length(), min, max, equalsMax, equalsMin);
	}
}
