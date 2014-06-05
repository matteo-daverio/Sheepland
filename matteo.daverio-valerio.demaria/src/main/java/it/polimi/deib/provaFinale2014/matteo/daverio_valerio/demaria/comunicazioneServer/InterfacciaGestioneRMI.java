package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * interfaccia che serve per la connessione del client in modalità RMI
 * 
 * @author Valerio De Maria
 * 
 */
public interface InterfacciaGestioneRMI extends Remote {

	/**
	 * metodo con cui il client RMI fa il log-in
	 * 
	 * @param nomeClient
	 * @param password
	 * @param client
	 * @return
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public boolean registrazione(String nomeClient, String password, InterfacciaClientRMI client)
			throws RemoteException;
	
	
}
