package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.Server;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.SocketServer;


public class App {
	
	public static void main(String[] args) {

		Server server = new SocketServer(Costanti.PORTA);
		server.startServer();

	}
	
}


