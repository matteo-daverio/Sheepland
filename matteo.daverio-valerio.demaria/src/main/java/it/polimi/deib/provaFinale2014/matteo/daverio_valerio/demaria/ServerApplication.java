package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.Gestione;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.GestioneRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.GestioneSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.GestorePartite;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaGestioneRMI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * faccio partire il thread di gestione delle partite, pubblico
 * l'implementazione di InterfacciaGestioneRMI sul registry, attendo connesioni
 * socket
 * 
 * @author Valerio De Maria
 * 
 */
public class ServerApplication {

	public final static int SERVER_PORT_RMI = Costanti.PORTA_RMI;// porta su cui
																	// andrò a
																	// creare il
																	// registry
	public final static int SERVER_PORT_SOCKET = Costanti.PORTA_SOCKET;

	public static void main(String[] args) {

		// CREO IL THREAD GESTORE PARTITE
		Thread t1;
		GestorePartite gestoreThread;
		gestoreThread = new GestorePartite();
		t1 = new Thread(gestoreThread);
		t1.start();
		System.out.println("gestore di partite creato");

		// GESTIONE RMI
		try {

			// creo l'oggetto remoto da caricare sul server
			InterfacciaGestioneRMI server = new GestioneRMI();
			// esporto l'oggetto remoto
			InterfacciaGestioneRMI stub = (InterfacciaGestioneRMI) UnicastRemoteObject
					.exportObject(server, 0);
			// creo un registry
			Registry registry = LocateRegistry.createRegistry(SERVER_PORT_RMI);
			// associo all'oggetto remoto esportato un nome
			registry.rebind("serverInAttesa", stub);

			System.out.println("Il server RMI è pronto a ricevere connessioni");

		} catch (RemoteException e) {
			System.err.println("ComputeEngine exception:");
			e.printStackTrace();
		}

		// PARTE IL CICLO DI ATTESA DI CONNESIONI SOCKET

		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(SERVER_PORT_SOCKET);
		} catch (IOException e) {
			System.err.println(e.getMessage()); // porta non disponibile
			return;
		}

		System.out
				.println("Il server socket si mette in attesa di connessioni");
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				Gestione connessioneSocket = new GestioneSocket(socket);
				connessioneSocket.autenticazione(GestorePartite.addConnessione(connessioneSocket));
			} catch (IOException e) {
				break; // entrerei qui se serverSocket venisse chiuso"
			}
		}

		try {
			serverSocket.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}
	
	

}
