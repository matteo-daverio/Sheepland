package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

public class Tessera {
	
	private final int tipo;
	private final int costo;
	
	public Tessera(int tipo, int costo)
	{
		this.tipo=tipo;
		this.costo=costo;
	}
	
	public int getTipo()
	{
		return tipo;
	}
	
	public int getCosto()
	{
		return costo;
	}
}
