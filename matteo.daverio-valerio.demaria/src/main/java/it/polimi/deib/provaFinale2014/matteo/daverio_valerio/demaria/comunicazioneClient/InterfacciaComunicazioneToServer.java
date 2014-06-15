package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.io.IOException;

/**
 * interfaccia con i metodi che permetto di parlare al server
 * 
 * @author Valerio De Maria
 * 
 */
public interface InterfacciaComunicazioneToServer {

	/**
	 * per fare il login
	 * 
	 * @param nome
	 * @param password
	 * @return
	 * @throws IOException
	 * @author Valerio De Maria
	 */
	public boolean effettuaLogin(String nome, String password)
			throws IOException;

	/**
	 * usato nella comunicazione socket per mettersi in attesa di comandi da
	 * server
	 * 
	 * @throws IOException
	 * @throws GameException
	 * @author Valerio De Maria
	 */
	public void riceviAggiornamenti() throws IOException, GameException;

	/**
	 * invia la posizione del pastore nel posizionamento iniziale
	 * 
	 * @param posizione
	 * @author Valerio De Maria
	 */
	public void inviaPosizionePastore(int posizione);

	/**
	 * invia la mossa che l'utente vuole eseguire
	 * 
	 * @param mossa
	 * @param pastoreTurno
	 * @author Valerio De Maria
	 */
	public void inviaMossa(Mossa mossa, int pastoreTurno);

}
