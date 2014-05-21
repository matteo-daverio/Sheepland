package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 
 * @author Matteo Daverio
 *
 */
public class CreaConnessioneSocket implements Runnable {

	private int port;
	
	public CreaConnessioneSocket(int port)
	{
		this.port=port;
	}
	
	public void run() {

		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return;
		}
		
		System.out.println("server pronto");
		
		
		
		while (true) {
				try {
					Socket socket = serverSocket.accept();
					// TODO passare socket a una classe che lo gestisce
				} catch (IOException e) {
					System.err.println(e.getMessage());
					break;
				}
		}

		try {
			serverSocket.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		
		
	}

	
	
}
