package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfacciaClientRMI extends Remote {
	
	public String comunicaClient(String messaggio) throws RemoteException;

}
