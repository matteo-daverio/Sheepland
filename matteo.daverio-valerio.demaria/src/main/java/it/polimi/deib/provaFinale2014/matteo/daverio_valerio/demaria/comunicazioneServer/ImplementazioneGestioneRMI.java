package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;

import java.rmi.RemoteException;

public class ImplementazioneGestioneRMI implements InterfacciaGestioneRMI {

	public boolean registrazione(String nomeClient, String password,
			InterfacciaClientRMI client) throws RemoteException {
		
		return GestorePartite.addConnessione(new GestioneRMI(nomeClient,password,"RMI",client,null));
	}
	
	

}
