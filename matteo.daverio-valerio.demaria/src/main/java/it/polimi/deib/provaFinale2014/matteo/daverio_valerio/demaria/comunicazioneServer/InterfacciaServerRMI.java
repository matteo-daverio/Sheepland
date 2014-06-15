package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfacciaServerRMI extends Remote {

	public void riceviPosizionePastore(int posizione) throws RemoteException;
	
	public void riceviMossa(Mossa mossa,int pastoreTurno) throws RemoteException;
	
}
