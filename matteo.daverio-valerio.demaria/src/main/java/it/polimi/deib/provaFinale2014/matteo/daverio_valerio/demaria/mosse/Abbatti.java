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
 * @author Valeri De Maria
 * 
 */
public class Abbatti implements Mossa {

	private int regione,pecora;

	// costruttore
	public Abbatti(int regione,int pecora) {

		this.regione = regione;
		this.pecora=pecora;

	}

	public void eseguiMossa(Partita partita) throws NoSheepInShireException,
			NoMoneyException, IllegalShireException {

		partita.abbatti(regione,pecora);

	}

	public void aggiornaMosseFatte(ArrayList<Mosse> mosseFatte) {
		mosseFatte.add(Mosse.ABBATTI);

	}

	public void aggiornaClients(
			ArrayList<InterfacciaComunicazioneClient> giocatori, int turno) {
		for (int i=0;i<=giocatori.size();i++) {
	           if(i!=(turno-1)){
				giocatori.get(turno-1).comunicaAbbattimento(regione,pecora);
	           }
			}		
	}

}
