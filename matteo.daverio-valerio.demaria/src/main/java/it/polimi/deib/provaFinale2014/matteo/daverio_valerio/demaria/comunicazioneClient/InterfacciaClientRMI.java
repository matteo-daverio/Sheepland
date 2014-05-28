package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * interfaccia del client dove ho i metodi che il server può eseguire
 *  
 * @author Valerio De Maria
 *
 */
public interface InterfacciaClientRMI extends Remote {
	
	public String comunicaClient(String messaggio) throws RemoteException;
	
	//public String iniziaPartita()throws RemoteException;
	
	//public String aggiorna()throws RemoteException;
	
	//public String fine()throws RemoteException;
	

}
