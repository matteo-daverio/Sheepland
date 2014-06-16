package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * interfaccia che contiene i metodi che il client RMI può chiamare su server
 * 
 * @author Valerio De Maria
 * 
 */
public interface InterfacciaServerRMI extends Remote {

	/**
	 * il client manda la posizione iniziale del pastore
	 * 
	 * @param posizione
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void riceviPosizionePastore(int posizione) throws RemoteException;

	/**
	 * il client manda la mossa che vuole effettuare
	 * 
	 * @param mossa
	 * @param pastoreTurno
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void riceviMossa(Mossa mossa, int pastoreTurno)
			throws RemoteException;

}
