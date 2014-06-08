package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * implementazione dell'interfaccia di RMI
 * 
 * @author Valerio De Maria
 * 
 */
public class GestioneRMI implements Gestione {

	private String nomeClient, password, tipo;
	private InterfacciaClientRMI client;
	private Partita partita;
	//public int ID=0;

	public GestioneRMI(){
		
	}
	public GestioneRMI(String nome, String password, String tipo,InterfacciaClientRMI client,Partita partita){
		this.nomeClient=nome;
		this.password=password;
		this.tipo=tipo;
		this.client=client;
		this.partita=partita;
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
/**
 * utile solo per socket
 * @author Valerio De Maria
 */
	public void chiediDati() {
		
	}

}
