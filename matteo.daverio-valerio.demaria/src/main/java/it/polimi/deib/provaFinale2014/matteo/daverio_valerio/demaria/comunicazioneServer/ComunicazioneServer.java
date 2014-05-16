package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ComunicazioneServer implements Runnable {
	Socket socket;
	Partita partita;
	int turnoGiocatore;
	private Scanner in;
	private PrintWriter out;
	private String line;

	// costruttore

	public ComunicazioneServer(Partita partita, Socket socket,
			int turnoGiocatore) {
		this.partita = partita;
		this.socket = socket;
		this.turnoGiocatore = turnoGiocatore;
	}

	public void run() {

		try {
			// creo gli stream di ingresso e uscita
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream());

		} catch (IOException e) {
			System.err.println(e.getMessage());
			// devo comunicare alla classe App che non sono riuscito a creare
			// gli stream
		}

	}
}
