package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Mosse;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;

import java.util.ArrayList;

/**
 * passo la regione dove si vuole abbattere una pecora
 * 
 * @author DeMaria
 * 
 */
public class Abbatti implements Mossa {

	private int regione;

	// costruttore
	public Abbatti(int regione) {

		this.regione = regione;

	}

	public void eseguiMossa(Partita partita) throws NoSheepInShireException,
			NoMoneyException, IllegalShireException {

		partita.abbatti(regione);

	}

	public void aggiornaClients(
			ArrayList<InterfacciaComunicazioneClient> giocatori) {
		for (InterfacciaComunicazioneClient x : giocatori) {

			x.comunicaAbbattimento(regione);
		}

	}

	public void aggiornaMosseFatte(ArrayList<Mosse> mosseFatte) {
		mosseFatte.add(Mosse.ABBATTI);

	}

}
