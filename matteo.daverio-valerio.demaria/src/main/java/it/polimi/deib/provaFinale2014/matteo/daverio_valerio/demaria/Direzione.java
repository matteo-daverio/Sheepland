package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

public class Direzione {
	
	private final int posizione;
	private final String verso;
	
	
	// costruttore 
	public Direzione(int posizione, String verso)
	{
		this.posizione=posizione;
		this.verso=verso;
	}

	
	// getter 
	
	public int getPosizione() {
		return posizione;
	}


	public String getVerso() {
		return verso;
	}
	
	
	
}
