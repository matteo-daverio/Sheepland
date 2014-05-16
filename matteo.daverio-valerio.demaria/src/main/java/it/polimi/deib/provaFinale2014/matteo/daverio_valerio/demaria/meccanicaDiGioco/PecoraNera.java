package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;

public class PecoraNera extends Pecora {
	private static PecoraNera instance=null;

	// costruttore privato
	private PecoraNera() 
	{
		super(Costanti.POSIZIONE_SHEEPBURG, Costanti.TIPO_PECORA_PECORANERA);
	}

	public static PecoraNera instance() //istanzia la pecora nera solo se non già esistente 
	{
		
		if (instance == null)
			instance = new PecoraNera();
		return instance;
	}
	
	
	

}
