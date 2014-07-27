package commons.utils;

import java.math.BigDecimal;

public class Comparators implements Logs{
	
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
	
	public static boolean inLimits(Number number, Number min, Number max){
		return inLimits(number, min, max, true, true);
	}
	
	public static boolean inLimits(String cadena, Number min, Number max){
		return inLimits(cadena.length(), min, max, true, true);
	}
	
	public static boolean inLimits(String cadena, Number min, Number max,
			boolean equalsMax, boolean equalsMin){
		return inLimits(cadena.length(), min, max, equalsMax, equalsMin);
	}
}
