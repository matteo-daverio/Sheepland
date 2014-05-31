package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaGestioneRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ServerApplication;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientRMI implements InterfacciaClientRMI {

	// TODO da chiedere all'utente;
	static String nome = "Valerio";
	static String password = "Ramponio";

	private Partita partita;
	private InterfacciaGestioneRMI server;

	/**
	 * ricevo l'istanza della partita creata dal server
	 * 
	 * @author Valerio De Maria
	 */
	public void riceviPartita(Partita partita) {

		this.partita = partita;
		gioca();

	}
	/**
	 * ricevo un movimento della pacora nera
	 * @author Valerio De Maria
	 */
	public void movimentoPecoraNera(int posizione){
		
		partita.getPecoraNera().setPosizione(posizione);
	}
	
	private void gioca(){
		
		//TODO
		
	}

	/**
	 * cerco il registry, scarico l'oggetto remoto del server e mi registro
	 * 
	 * @author Valerio De Maria
	 */
	private void ricercaConnessione() {

		try {

			// cerco il registry del server
			Registry registry = LocateRegistry.getRegistry("localhost",
					ServerApplication.SERVER_PORT_RMI);

			// scarico l'oggetto remoto del server
			server = (InterfacciaGestioneRMI) registry.lookup("serverInAttesa");

			// eseguo il metodo registrazione dell'oggetto remoto del server
			String result = server.registrazione(nome, password);

		} catch (RemoteException e) {
			System.err.println("Remote exception:");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("Name " + "istanza" + " not bound.");
		}

	}

	/**
	 * creo un oggetto remoto sul registry tramite cui il server mi comunica
	 * @author Valerio De Maria
	 */
	private void esportaOggettoRemoto(){
		
		try {

			// creo l'oggetto da caricare sul registry
			InterfacciaClientRMI client = new ClientRMI();

			// esporto l'oggetto sull registry
			InterfacciaClientRMI stub = (InterfacciaClientRMI) UnicastRemoteObject
					.exportObject(client, 0);

			// cerco il registry del server
			Registry registry = LocateRegistry.getRegistry("localhost",
					ServerApplication.SERVER_PORT_RMI);

			// associo all'oggetto esportato il nome formato da
			// nomeGiocatore+password
			registry.rebind(nome + password, stub);

		} catch (RemoteException e) {
			System.err.println("non riesco a caricare l'oggetto client sul registry");
			e.printStackTrace();
		}
		
	}

	public void start() {

		ricercaConnessione();
		
		esportaOggettoRemoto();
		
		//ora il client rimane in attesa di iniziare tramite "riceviPartita"

	}

}
