package commons.utils.mapeo.factory;

import java.util.Comparator;

/**
 * Clase que aglomera las ordenaciones de los objetos del Mapeo
 * */
public abstract class Orderer{
	
	/**
	 * Ordenación de los Objetos {@link OriginObject}  por identificador
	 * */
	public static class OrderOriginObjects implements Comparator<OriginObject>{
		public int compare(OriginObject arg0, OriginObject arg1) {
			return arg0.getId().compareTo(arg1.getId());
		}
	}
	
	/**
	 * Ordenación de los Objetos {@link DestinyObject} por identificador
	 * */
	public static class OrderDestinyObjects implements Comparator<DestinyObject>{
		public int compare(DestinyObject arg0, DestinyObject arg1) {
			return arg0.getId().compareTo(arg1.getId());
		}
	}
	
	/**
	 * Ordenación de los objetos {@link OriginDestinyObject} por Identificador de origen
	 * */
	public static class OrderOriginDestinyObjectsByOrigin implements Comparator<OriginDestinyObject>{
		public int compare(OriginDestinyObject o1, OriginDestinyObject o2) {
			return o1.getOrigin().compareTo(o2.getOrigin());
		}
	}
	
	/**
	 * Ordenación de los objetos {@link OriginDestinyObject} por identificador de destino
	 * */
	public static class OrderOriginDestinyObjectsByDestiny implements Comparator<OriginDestinyObject>{
		public int compare(OriginDestinyObject o1, OriginDestinyObject o2) {
			return o1.getDestiny().compareTo(o2.getDestiny());
		}
	}
}
