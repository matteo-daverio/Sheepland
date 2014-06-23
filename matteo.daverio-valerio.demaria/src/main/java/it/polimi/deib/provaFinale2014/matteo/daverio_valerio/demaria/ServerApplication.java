package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.Gestione;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.GestioneSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.GestorePartite;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.ImplementazioneGestioneRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaGestioneRMI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * faccio partire il thread di gestione delle partite, pubblico
 * l'implementazione di InterfacciaGestioneRMI sul registry, attendo connesioni
 * socket
 * 
 * @author Valerio De Maria
 * 
 */
public class ServerApplication {

	// porta su cui creo il registry
	public final static int SERVER_PORT_RMI = Costanti.PORTA_RMI;
	// porta su cui il server socket si mette in ascolto
	public final static int SERVER_PORT_SOCKET = Costanti.PORTA_SOCKET;

	private static final Logger LOG = Logger.getLogger(ClientRMI.class
			.getName());

	private static boolean test = false;
	
	public static void setTestTrue(){
		test=true;
	}

	public static void main(String[] args) {

		// CREO IL THREAD GESTORE PARTITE
		Thread t1;
		GestorePartite gestoreThread;
		gestoreThread = new GestorePartite();
		t1 = new Thread(gestoreThread);
		t1.start();

		// GESTIONE RMI
		InterfacciaGestioneRMI server;
		try {

			// creo l'oggetto remoto da caricare sul server
			server = new ImplementazioneGestioneRMI();
			// esporto l'oggetto remoto
			InterfacciaGestioneRMI stub = (InterfacciaGestioneRMI) UnicastRemoteObject
					.exportObject(server, 0);

			// System.setProperty("java.rmi.server.hostname", "131.175.28.158");
			// creo un registry
			Registry registry = LocateRegistry.createRegistry(SERVER_PORT_RMI);
			// associo all'oggetto remoto esportato un nome
			registry.rebind("serverInAttesa", stub);

			//System.out.println("Il server RMI è pronto a ricevere connessioni");

		} catch (RemoteException e) {
			
			LOG.log(Level.SEVERE, "errore esportazione RMI", e);
			return;
		}

		// PARTE IL CICLO DI ATTESA DI CONNESIONI SOCKET

		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(SERVER_PORT_SOCKET);
		} catch (IOException e) {
			
			return;
		}

		//System.out
		//		.println("Il server socket si mette in attesa di connessioni");

		while (true) {
			try {
				// socket che dedico per la comunicazione con il client
				Socket socket = serverSocket.accept();

				// creo un oggetto di gestioneSocket
				Gestione connessioneSocket = new GestioneSocket(socket);


				connessioneSocket.chiediDati();

				// se il client non ha una partita in corso lo metto in attesa
				// di una
				connessioneSocket.autenticazione(GestorePartite
						.addConnessione(connessioneSocket));
			} catch (IOException e) {
				break;
			}
		}

		try {
			serverSocket.close();
		} catch (IOException e) {
		}

		if(test){
		// IMPORTANTE, questa riga serve per usare la variabile server la quale
		// altrimenti verrebbe eliminata dal garbage collector
		System.out.println(server.toString());
		}

	}

}
