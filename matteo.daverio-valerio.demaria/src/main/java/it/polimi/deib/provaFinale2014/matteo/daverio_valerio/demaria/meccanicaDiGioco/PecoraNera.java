package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

public class PecoraNera extends Pecora {
	private static PecoraNera instance = null;

	// costruttore privato
	private PecoraNera() {
		super(Costanti.POSIZIONE_SHEEPBURG, Costanti.TIPO_PECORA_PECORANERA);
	}

	public static PecoraNera instance() // istanzia la pecora nera solo se non
										// già esistente
	{

		if (instance == null)
			instance = new PecoraNera();
		return instance;
	}

	// movimento iniziale pecora nera

	public int fugaPecoraNera(int lancioDado) // restituisce la nuova posizione
												// della pecora nera
	{
		Strada strada = esisteStrada(lancioDado);

		if (strada != null && !strada.recintata() && !stradaOccupata(strada)) {
			if(strada.getRegioneDestra()==this.getPosizione())
				this.setPosizione(strada.getRegioneSinistra());
			else
				this.setPosizione(strada.getRegioneDestra());
		}

		return this.getPosizione();
	}

	// restituisce, se esiste, la strada adiacente alla pecora nera con il
	// numero del dado corrispondente al risultato del lancio

	private Strada esisteStrada(int lancioDado) {
		for (Strada strada : Partita.getStrade()) {
			if (stradaAdiacente(strada) && strada.getNumeroDado() == lancioDado)
				return strada;
		}
		return null;
	}

	// controlla se la pecora nera si trova in un terreno adiacente alla strada
	// ricevuta

	private boolean stradaAdiacente(Strada strada) {
		return strada.getRegioneDestra() == this.getPosizione()
				|| strada.getRegioneSinistra() == this.getPosizione();
	}
	
	// controlla se la strada è occupata da un pastore
	
	private boolean stradaOccupata(Strada strada)
	{
		return false;    ///  DA FINIRE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}
	
}
