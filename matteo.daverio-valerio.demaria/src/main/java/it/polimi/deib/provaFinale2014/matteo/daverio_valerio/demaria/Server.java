package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import java.net.Socket;

public class Server implements Runnable {

	Socket socket;
	Partita partita;
	int turnoGiocatore;

	public Server(Partita partita, Socket socket, int turnoGiocatore) {
		this.partita = partita;
		this.socket = socket;
		this.turnoGiocatore=turnoGiocatore;
	}

	public void run() {
		
	}

}
