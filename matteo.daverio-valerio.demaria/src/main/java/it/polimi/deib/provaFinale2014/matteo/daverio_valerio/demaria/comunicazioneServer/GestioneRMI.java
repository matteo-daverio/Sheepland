package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.net.Socket;
import java.rmi.RemoteException;
/**
 * implementazione dell'interfaccia di RMI 
 * @author Valerio De Maria
 *
 */
public class GestioneRMI implements Gestione, InterfacciaGestioneRMI {

    private String nomeClient,password,tipo;
    
	
    public String registrazione(String nomeClient, String password)
			throws RemoteException {
    	
		this.nomeClient=nomeClient;
		this.password=password;
		this.tipo="RMI";
		GestorePartite.addConnessione(this);
		return null;
		
	}
    
    public String getNome(){
    	return nomeClient;
    }
    
	public String getTipoConnessione(){
		return tipo;
	}
	
	public Socket getSocket(){
		return null;
	}
	
	public String getPassword(){
		return password;
	}
	
	

}
