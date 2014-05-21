package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.RmiServer;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.SocketServer;


public class App {

	
	
	public static void main(String[] args) {

		Thread threadSocket, threadRmi;
		SocketServer socketServer=new SocketServer(Costanti.PORTA);
		RmiServer rmiServer=new RmiServer();
		threadSocket=new Thread(socketServer);
		threadRmi=new Thread(rmiServer);
		threadSocket.start();
		threadRmi.start();
	}
	
}


