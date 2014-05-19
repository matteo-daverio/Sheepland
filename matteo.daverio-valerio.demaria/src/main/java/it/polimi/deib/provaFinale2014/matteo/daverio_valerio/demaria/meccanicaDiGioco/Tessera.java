package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;

public class Tessera {
	
	private final TipoTerreno tipo;
	private final int costo;
	
	public Tessera(TipoTerreno tipo, int costo)
	{
		this.tipo=tipo;
		this.costo=costo;
	}
	
	public TipoTerreno getTipo()
	{
		return tipo;
	}
	
	public int getCosto()
	{
		return costo;
	}
}
