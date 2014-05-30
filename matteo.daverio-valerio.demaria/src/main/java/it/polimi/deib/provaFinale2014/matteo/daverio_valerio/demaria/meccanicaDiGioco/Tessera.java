package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
/**
 * 
 * @author Matteo Daverio
 *
 */
public class Tessera {
	
	private final TipoTerreno tipo;
	private final int costo;
	
	/**
	 * costruttore oggetto tessera
	 * 
	 * @param tipo
	 * @param costo
	 * @author Matteo Daverio
	 */
	public Tessera(TipoTerreno tipo, int costo)
	{
		this.tipo=tipo;
		this.costo=costo;
	}
	
	/**
	 * 
	 * @return tipo di terreno della tessera
	 */
	public TipoTerreno getTipo()
	{
		return tipo;
	}
	
	/**
	 * 
	 * @return costo della tessera
	 */
	public int getCosto()
	{
		return costo;
	}
}
