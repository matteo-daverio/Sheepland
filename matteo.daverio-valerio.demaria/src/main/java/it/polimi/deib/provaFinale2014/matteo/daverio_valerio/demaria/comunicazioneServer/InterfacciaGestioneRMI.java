package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * interfaccia che serve per la connessione del client in modalità RMI
 * 
 * @author Valerio De Maria
 * 
 */
public interface InterfacciaGestioneRMI extends Remote {

	public String registrazione(String nomeClient, String password)
			throws RemoteException;
}
