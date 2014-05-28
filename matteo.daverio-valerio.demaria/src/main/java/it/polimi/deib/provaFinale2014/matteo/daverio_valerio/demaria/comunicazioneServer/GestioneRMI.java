package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.rmi.RemoteException;
/**
 * implementazione dell'interfaccia di RMI 
 * @author Valerio De Maria
 *
 */
public class GestioneRMI implements Gestione, InterfacciaGestioneRMI {

    private String nomeClient,password;	
	public String registrazione(String nomeClient, String password)
			throws RemoteException {
		this.nomeClient=nomeClient;
		this.password=password;
		GestorePartite.addConnessione(this);
		return null;
	}
	
	

}
