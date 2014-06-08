package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;

import java.io.Serializable;
import java.util.List;

public class Pong implements Mossa,Serializable{

	public void eseguiMossa(Partita partita) throws NoMovementException,
			NoMoneyException, InvalidMovementException, NoMoreCardsException,
			IllegalShireTypeException, NoSheepInShireException,
			IllegalShireException, CannotProcreateException,
			IllegalStreetException {
		// TODO Auto-generated method stub
		System.out.println("eseguo il pong");

	}

	public void aggiornaClients(List<InterfacciaComunicazioneClient> giocatori,
			int turno) {
		// TODO Auto-generated method stub
		
	}

	public void aggiornaMosseFatte(List<MosseEnum> mosseFatte) {
		// TODO Auto-generated method stub
		
	}

}
