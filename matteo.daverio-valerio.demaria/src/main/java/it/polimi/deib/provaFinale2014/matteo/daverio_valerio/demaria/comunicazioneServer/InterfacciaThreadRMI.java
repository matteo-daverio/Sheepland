package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfacciaThreadRMI extends Remote{
	
	public String registrazione(String nomeClient, String password) throws RemoteException;

}
