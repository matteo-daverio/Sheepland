package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.Server;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Timer;
import java.util.TimerTask;

public class App {

	private int port;
	private int contatore = 0;
	private boolean erroreCreazioneSocket = false;
	private static boolean timeout = false;
	private Partita partita;

	// costruttore

	public App(int port) {
		this.port = port;
	}

	public static void main(String[] args) {

		App server = new App(Costanti.PORTA);
		server.startServer();

	}

	// set del timeout

	public static void setTimeout() {
		timeout = true;
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

		Timer timer = new Timer(); // creazione timer

		// attesa client

		while (true) {

			// generazione partita
			partita = new Partita();
			TaskTimer taskTimer = new TaskTimer();
			timer.schedule(taskTimer, 0, Costanti.PERIODO_TICK_TIMER);
			while (contatore < 6 && (!timeout || contatore < 2)) { // attesa
																	// client
																	// della
																	// partita
				try {
					Socket socket = serverSocket.accept();
					contatore++;
					executor.submit(new Server(partita, socket, contatore));
				} catch (IOException e) {
					erroreCreazioneSocket = true;
					break;
				}
			}

			// controllo che le connessioni ai client siano andate a buon fine
			if (!erroreCreazioneSocket) {
				partita.setNumeroGiocatori(contatore);
				
				contatore = 0;
				timeout = false;
			} else
				break;
		}

		executor.shutdown();
		
		try {
			serverSocket.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}

}

class TaskTimer extends TimerTask {

	private int time = 0;

	@Override
	public void run() {

		time++;

		if (time > 60) {
			// fermo il timer

			App.setTimeout();
			this.cancel();

		}

	}

}
