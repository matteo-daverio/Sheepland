package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import java.util.ArrayList;

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
/**
 * interfaccia delle classi di mossa
 * 
 * @author Valerio De Maria
 * 
 *
 */
public interface Mossa {
	
	public void eseguiMossa(Partita partita) throws NoMovementException, NoMoneyException, InvalidMovementException, NoMoreCardsException, IllegalShireTypeException, NoSheepInShireException, IllegalShireException, CannotProcreateException, IllegalStreetException;
	
	public void aggiornaClients(ArrayList<InterfacciaComunicazioneClient> giocatori);

}
