package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

public class Tessera {
	
	private final String tipo;
	private final int costo;
	
	public Tessera(String tipo, int costo)
	{
		this.tipo=tipo;
		this.costo=costo;
	}
	
	public String getTipo()
	{
		return tipo;
	}
	
	public int getCosto()
	{
		return costo;
	}
}
