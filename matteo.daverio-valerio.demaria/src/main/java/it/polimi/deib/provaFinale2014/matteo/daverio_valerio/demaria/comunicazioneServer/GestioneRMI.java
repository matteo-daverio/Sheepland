package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;

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

	/**
	 * metodo che i client chiamano per registrarsi
	 * 
	 * @author Valerio De Maria
	 */
	public String registrazione(String nomeClient, String password, InterfacciaClientRMI client)
			throws RemoteException {

		this.nomeClient = nomeClient;
		this.password = password;
		this.tipo = "RMI";
		this.client=client;
		GestorePartite.addConnessione(this);
		return null;

	}
	
	public InterfacciaClientRMI getInterfacciaClient(){
		return client;
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

}
