package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

	private int port;
	private int contatore = 0;
	private boolean erroreCreazioneSocket = false;
	private boolean timeout = false;
	private Partita partita;

	// costruttore

	public App(int port) {
		this.port = port;
	}

	public static void main(String[] args) {

		App server = new App(Costanti.PORTA);
		server.startServer();

	}

	// avvio del server

	public void startServer() {
		ExecutorService executor = Executors.newCachedThreadPool();
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return;
		}

		System.out.println("server pronto");

		// attesa client

		while (true) {

			// generazione partita
			partita = new Partita();
			while (contatore < 6 || !timeout) { // attesa client della partita
				try {
					Socket socket = serverSocket.accept();
					executor.submit(new Server(partita, socket));
					contatore++;
				} catch (IOException e) {
					erroreCreazioneSocket = true;
					break;
				}
			}
			
			// controllo che le connessioni ai client siano andate a buon fine
			if (!erroreCreazioneSocket) {
				partita.setNumeroGiocatori(contatore);
				contatore = 0;
			} else
				break;
		}

		executor.shutdown();

	}

}
