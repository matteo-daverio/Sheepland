package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.CreaConnessioneSocket;


public class App {

	
	
	public static void main(String[] args) {

		Thread threadSocket;
		CreaConnessioneSocket connessioneSocket=new CreaConnessioneSocket(Costanti.PORTA);
		threadSocket=new Thread(connessioneSocket);
		threadSocket.start();
	}
	
}


