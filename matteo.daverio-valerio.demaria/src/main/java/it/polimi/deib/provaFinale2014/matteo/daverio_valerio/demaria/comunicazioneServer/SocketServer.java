package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

public class SocketServer implements Server {
	private int port;
	private int contatore = 0;
	private boolean erroreCreazioneSocket = false;
	private static boolean timeout = false;
	private Partita partita;

	public SocketServer(int port) {
		this.port = port;
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
					executor.submit(new ComunicazioneServer(partita, socket,
							contatore));
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
