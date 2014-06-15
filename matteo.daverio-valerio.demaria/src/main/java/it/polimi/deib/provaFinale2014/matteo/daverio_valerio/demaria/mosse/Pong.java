package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneToClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;

import java.io.Serializable;
import java.util.List;

/**
 * mossa che serve per capire al server che il client è ancora connesso
 * 
 * @author Valerio De Maria
 * 
 */
public class Pong implements Mossa, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7670762943182933867L;

	/**
	 * 
	 */
	public void eseguiMossa(Partita partita, String giocatore, int pastore)
			throws GameException {

	}

	/**
     * 
     */
	public void aggiornaClients(
			List<InterfacciaComunicazioneToClient> giocatori, int turno) {

	}

	/**
     * 
     */
	public List<MosseEnum> aggiornaMosseFatte(List<MosseEnum> mosseFatte) {
		return null;

	}

}
