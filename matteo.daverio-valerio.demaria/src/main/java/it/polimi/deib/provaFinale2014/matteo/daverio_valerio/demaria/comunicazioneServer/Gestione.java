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
	 * 
	 * @return
	 * @author Valerio De Maria
	 */
	public Partita getPartita();

	/**
	 * getter nome
	 * 
	 * @return
	 * @author Valerio De Maria
	 */
	public String getNome();

	/**
	 * getter tipo connessione
	 * 
	 * @return
	 * @author Valerio De Maria
	 */
	public String getTipoConnessione();

	/**
	 * getter password
	 * 
	 * @return
	 * @author Valerio De Maria
	 */
	public String getPassword();

	/**
	 * solo per socket getter Socket
	 * 
	 * @return
	 * @author Valerio De Maria
	 */
	public Socket getSocket();

	/**
	 * solo per socket getter buffer in
	 * 
	 * @return
	 * @author Valerio De Maria
	 */
	public ObjectInputStream getBufferIn();

	/**
	 * solo per socket getter buffer out
	 * 
	 * @return
	 * @author Valerio De Maria
	 */
	public ObjectOutputStream getBufferOut();

	/**
	 * solo RMI getter interfaccia client serve al server per poter poi eseguire
	 * i metodi su quella interfaccia
	 * 
	 * @return
	 * @author Valerio De Maria
	 */
	public InterfacciaClientRMI getInterfacciaClient();

	/**
	 * solo socket restituisce l'esito dell'autenticazione
	 * 
	 * @param riuscita
	 * @author Valerio De Maria
	 */
	public void autenticazione(boolean riuscita);

	/**
	 * solo socket chiede nome e password
	 * 
	 * @author Valerio De Maria
	 */
	public void chiediDati();

}
