package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * interfaccia delle connessioni
 * 
 * @author Valerio De Maria
 * 
 */
public interface Gestione {

	/**
	 * getter partita
	 * @return
	 * @author Valerio De Maria
	 */
	public Partita getPartita();
	
	public String getNome();

	public String getTipoConnessione();

	public String getPassword();

	//utile solo per GestioneSocket
	public Socket getSocket();
	
	//utile solo per GestioneSocket
	public ObjectInputStream getBufferIn();
	
	//utile solo per GestioneSocket
	public ObjectOutputStream getBufferOut();
	
	//utile solo per GestioneRMI
	public InterfacciaClientRMI getInterfacciaClient();
	
	//utile solo per GestioneSocket
	public void autenticazione(boolean riuscita);
	
	//utile solo per GestioneSocket
	public void chiediDati();
	
	
}
