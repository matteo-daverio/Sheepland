package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.net.Socket;

public interface Gestione {
	
	public String getNome();
	
	public String getTipoConnessione();
	
	public String getPassword();
	
	public Socket getSocket();
}
