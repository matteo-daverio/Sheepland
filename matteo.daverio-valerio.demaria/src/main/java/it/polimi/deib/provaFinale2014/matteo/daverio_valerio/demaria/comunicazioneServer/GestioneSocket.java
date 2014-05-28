package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.net.Socket;
import java.rmi.RemoteException;
/**
 * 
 * 
 * @author Valerio De Maria
 *
 */
public class GestioneSocket implements Gestione {

	Socket socket;
	
	public GestioneSocket(Socket socket){
		this.socket=socket;
	}
	

}
