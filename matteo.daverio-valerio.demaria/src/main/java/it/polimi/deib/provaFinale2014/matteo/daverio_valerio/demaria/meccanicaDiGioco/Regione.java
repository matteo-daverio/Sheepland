package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import java.util.ArrayList;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;

public class Regione {

	// attributi
	private int posizione;
	private TipoTerreno tipo;
	private ArrayList<Pecora> pecore;

	// costruttori

	public Regione(int posizione,TipoTerreno tipo) {
		this.posizione=posizione;
		this.tipo = tipo;
	}

	// getters and setters

	public ArrayList<Pecora> getPecore() {
		return pecore;
	}
}
