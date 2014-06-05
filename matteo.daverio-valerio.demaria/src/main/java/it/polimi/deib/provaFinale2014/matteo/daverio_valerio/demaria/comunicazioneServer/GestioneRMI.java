package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * implementazione dell'interfaccia di RMI
 * 
 * @author Valerio De Maria
 * 
 */
public class GestioneRMI implements Gestione, InterfacciaGestioneRMI {

	private String nomeClient, password, tipo;
	private InterfacciaClientRMI client;
	private Partita partita;

	/**
	 * metodo che i client chiamano per fare il log-in
	 * 
	 * @author Valerio De Maria
	 */
	public boolean registrazione(String nomeClient, String password,
			InterfacciaClientRMI client) throws RemoteException {

		this.nomeClient = nomeClient;
		this.password = password;
		this.tipo = "RMI";
		// interfaccia del client RMI su cui il server può chiamare i metodi
		this.client = client;
		// al momento del log-in il client non ha una partita in corso
		this.partita = null;
		// torna false se il client ha una partita in corso e la sua password è
		// sbagliata
		return GestorePartite.addConnessione(this);

	}

	/**
	 * ritorna l'interfaccia client RMI
	 * 
	 * @author Valerio De Maria
	 */
	public InterfacciaClientRMI getInterfacciaClient() {
		return client;
	}

	/**
	 * ritorna la partita associata al client
	 * 
	 * @author Valerio De Maria
	 */
	public Partita getPartita() {
		return partita;
	}

	/**
	 * ritorna il nome del client
	 * 
	 * @author Valerio De Maria
	 */
	public String getNome() {
		return nomeClient;
	}

	/**
	 * ritorna il tipo di connessione
	 * 
	 * @author Valerio De Maria
	 */
	public String getTipoConnessione() {
		return tipo;
	}

	/**
	 * ritorna null perchè non è una connessione Socket
	 * 
	 * @author Valerio De Maria
	 */
	public Socket getSocket() {
		return null;
	}

	/**
	 * ritorna la password
	 * 
	 * @author Valerio De Maria
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * ritorna null perchè non è una connessione Socket
	 * 
	 * @author Valerio De Maria
	 */
	public ObjectInputStream getBufferIn() {
		return null;
	}

	/**
	 * ritorna null perchè non è una connessione Socket
	 * 
	 * @author Valerio De Maria
	 */
	public ObjectOutputStream getBufferOut() {
		return null;
	}

	/**
	 * questo metodo serve solo per il client Socket il client RMI sa il
	 * risultato del log-in tramite il parametro di ritorno del metodo
	 * registrazione
	 * 
	 * @author Valerio De Maria
	 */
	public void autenticazione(boolean riuscita) {

	}

}
