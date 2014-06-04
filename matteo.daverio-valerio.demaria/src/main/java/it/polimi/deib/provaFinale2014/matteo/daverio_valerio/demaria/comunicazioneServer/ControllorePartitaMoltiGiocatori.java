package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.util.ArrayList;

public class ControllorePartitaMoltiGiocatori extends ControllorePartita {

	public ControllorePartitaMoltiGiocatori(ArrayList<Gestione> connessioni,
			Partita partita) {
		super(connessioni, partita);
	}

}
