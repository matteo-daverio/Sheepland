package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneToClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;

import java.util.List;

/**
 * interfaccia delle classi di mossa
 * 
 * @author Valerio De Maria
 * 
 * 
 */
public interface Mossa {

	/**
	 * esegue su partita la mossa
	 * 
	 * @param partita
	 * @param giocatore
	 * @param pastore
	 * @throws GameException
	 * @author Valerio De Maria
	 */
	public void eseguiMossa(Partita partita, String giocatore, int pastore)
			throws GameException;

	/**
	 * comunica ai client lo svolgimento della mossa
	 * 
	 * @param giocatori
	 * @param turno
	 * @author Valerio De Maria
	 */
	public void aggiornaClients(
			List<InterfacciaComunicazioneToClient> giocatori, int turno,List<Boolean> giocatoriConnessi);

	/**
	 * aggiorna l'arrayList di mosse fatte, il quale server per calcolare le
	 * mosse disponibili
	 * 
	 * @param mosseFatte
	 * @return
	 * @author Valerio De Maria
	 */
	public List<MosseEnum> aggiornaMosseFatte(List<MosseEnum> mosseFatte);

}
