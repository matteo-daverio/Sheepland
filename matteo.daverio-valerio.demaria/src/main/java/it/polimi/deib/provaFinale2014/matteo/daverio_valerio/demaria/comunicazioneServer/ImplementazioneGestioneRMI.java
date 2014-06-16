package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;

import java.rmi.RemoteException;

/**
 * implementazione gestione RMI
 * @author Valerio De Maria
 *
 */
public class ImplementazioneGestioneRMI implements InterfacciaGestioneRMI {

	/**
	 * metodo che effettua il logi-in
	 * @author Valerio De Maria
	 */
	public boolean registrazione(String nomeClient, String password,
			InterfacciaClientRMI client) throws RemoteException {
		
		return GestorePartite.addConnessione(new GestioneRMI(nomeClient,password,"RMI",client,null));
	}
	
	

}
