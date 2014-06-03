package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.util.ArrayList;

/**
 * caso in cui ho solo due giocatori
 * 
 * @author Valerio De Maria
 * 
 */
public class ControllorePartitaDueGiocatori extends ControllorePartitaClassica {

	public ControllorePartitaDueGiocatori(ArrayList<Gestione> connessioni,
			Partita partita) {
		super(connessioni, partita);

	}

}