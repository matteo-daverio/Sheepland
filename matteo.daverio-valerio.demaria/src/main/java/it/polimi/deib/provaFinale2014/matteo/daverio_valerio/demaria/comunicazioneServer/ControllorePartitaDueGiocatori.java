package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;


import java.util.List;

/**
 * caso in cui ho solo due giocatori
 * 
 * @author Valerio De Maria
 * 
 */
public class ControllorePartitaDueGiocatori extends ControllorePartita {

	public ControllorePartitaDueGiocatori(List<Gestione> connessioni,
			Partita partita) {
		super(connessioni, partita);

	}

	@Override
	void aggiungiPastori(List<Gestione> connessioni, Partita partita) {
		// TODO 
		
	}

}