package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * interfaccia del client RMI dove ho i metodi che il server può eseguire
 *  
 * @author Valerio De Maria
 *
 */
public interface InterfacciaClientRMI extends Remote {

	
	public void riceviPartita(Partita partita) throws RemoteException;
	
	public void movimentoPecoraNera(int posizione) throws RemoteException;
	
	

}
