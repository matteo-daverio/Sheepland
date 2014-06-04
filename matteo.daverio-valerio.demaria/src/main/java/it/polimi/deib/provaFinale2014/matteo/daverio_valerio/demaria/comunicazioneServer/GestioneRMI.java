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
	 * metodo che i client chiamano per registrarsi
	 * 
	 * @author Valerio De Maria
	 */
	public boolean registrazione(String nomeClient, String password, InterfacciaClientRMI client)
			throws RemoteException {

		this.nomeClient = nomeClient;
		this.password = password;
		this.tipo = "RMI";
		this.client=client;
		this.partita=null;
		return GestorePartite.addConnessione(this);

	}
	
	public InterfacciaClientRMI getInterfacciaClient(){
		return client;
	}
	
	public Partita getPartita(){
		return partita;
	}

	public String getNome() {
		return nomeClient;
	}

	public String getTipoConnessione() {
		return tipo;
	}

	public Socket getSocket() {
		return null;
	}

	public String getPassword() {
		return password;
	}

	public ObjectInputStream getBufferIn() {
		return null;
	}

	public ObjectOutputStream getBufferOut() {
		return null;
	}

	public void autenticazione(boolean riuscita) {
		
	}

}
