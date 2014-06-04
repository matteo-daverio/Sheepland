package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;

import java.net.Socket;

/**
 * interfaccia delle di connessioni
 * 
 * @author Valerio De Maria
 * 
 */
public interface Gestione {

	public String getNome();

	public String getTipoConnessione();

	public String getPassword();

	public Socket getSocket();
	
	public InterfacciaClientRMI getInterfacciaClient();
}
