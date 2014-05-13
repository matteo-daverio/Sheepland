package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import java.net.Socket;

public class Server implements Runnable {

	Socket socket;
	Partita partita;

	public Server(Partita partita, Socket socket) {
		this.partita = partita;
		this.socket = socket;
	}

	public void run() {
		
	}

}
