package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

	private int port;
	private int contatore = 0;
	private boolean erroreCreazioneSocket=false;

	public App(int port) {
		this.port = port;
	}

	public static void main(String[] args) {

		App server = new App(Costanti.PORTA);
		server.startServer();
	}

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

		while (true) {
			while (contatore<6) {
				try {
					Socket socket = serverSocket.accept();
					executor.submit(new Server(new Partita(), socket));
					contatore ++;
				} catch (IOException e) {
					erroreCreazioneSocket=true;
					break;
				}
			}
				if(!erroreCreazioneSocket){					
					contatore=0;	
				}
				else
					break;
		}
		
		executor.shutdown();
		
	}

}
