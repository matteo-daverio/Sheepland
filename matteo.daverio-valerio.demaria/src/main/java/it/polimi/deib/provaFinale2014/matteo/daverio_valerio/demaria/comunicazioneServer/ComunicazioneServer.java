package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.net.Socket;


public class ComunicazioneServer implements Runnable {
	Socket socket;
	Partita partita;
	int turnoGiocatore;

	// costruttore

	public ComunicazioneServer(Partita partita, Socket socket,
			int turnoGiocatore) {
		this.partita = partita;
		this.socket = socket;
		this.turnoGiocatore = turnoGiocatore;
	}

	public void run() {
		
		
		
	}
}
