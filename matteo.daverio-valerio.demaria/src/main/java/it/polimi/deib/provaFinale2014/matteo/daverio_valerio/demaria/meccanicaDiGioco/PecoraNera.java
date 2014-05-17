package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import java.util.ArrayList;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;

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

	public int fugaPecoraNera(int lancioDado, ArrayList<Pastore> pastori,
			ArrayList<Strada> strade) // restituisce
	// la
	// nuova
	// posizione
	// della pecora nera
	{
		Strada strada = esisteStrada(lancioDado, strade);

		if (strada != null && !strada.recintata()
				&& !stradaOccupata(strada, pastori)) {
			if (strada.getRegioneDestra() == this.getPosizione())
				this.setPosizione(strada.getRegioneSinistra());
			else
				this.setPosizione(strada.getRegioneDestra());
		}

		return this.getPosizione();
	}

	// restituisce, se esiste, la strada adiacente alla pecora nera con il
	// numero del dado corrispondente al risultato del lancio

	private Strada esisteStrada(int lancioDado, ArrayList<Strada> strade) {
		for (Strada strada : strade) {
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

	private boolean stradaOccupata(Strada strada, ArrayList<Pastore> pastori) {
		for (Pastore pastore : pastori) {
			if (pastore.getPosizione() == strada.getPosizione())
				return true;
		}
		return false;
	}

}
