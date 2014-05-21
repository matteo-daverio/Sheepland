package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.CreaConnessioneRmi;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.CreaConnessioneSocket;


public class App {

	
	
	public static void main(String[] args) {

		Thread threadSocket, threadRmi;
		CreaConnessioneSocket connessioneSocket=new CreaConnessioneSocket(Costanti.PORTA);
		CreaConnessioneRmi connessioneRmi=new CreaConnessioneRmi();
		threadSocket=new Thread(connessioneSocket);
		threadRmi=new Thread(connessioneRmi);
		threadSocket.start();
		threadRmi.start();
	}
	
}


