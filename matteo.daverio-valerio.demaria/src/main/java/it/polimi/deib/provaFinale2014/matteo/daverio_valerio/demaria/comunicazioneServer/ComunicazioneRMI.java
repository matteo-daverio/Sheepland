package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ServerApplication;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

/**
 * classe che comunica con client RMI
 * 
 * @author Valerio De Maria
 * 
 */
public class ComunicazioneRMI implements InterfacciaComunicazioneClient {

	private String nome, password;
	private InterfacciaClientRMI client;

	// costruttore
	public ComunicazioneRMI(String nome, String password) {

		this.nome = nome;
		this.password = password;

		try {

			// cerco il registry del client
			Registry registry = LocateRegistry.getRegistry("localhost",
					ServerApplication.SERVER_PORT_RMI);

			// scarico l'oggetto remoto del client
			client = (InterfacciaClientRMI) registry.lookup(nome + password);

		} catch (RemoteException e) {
			System.err.println("Remote exception:");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("Remote instance" + " not bound.");
		}

	}


	public void inviaPartita(Partita partita) {

		try {
			client.riceviPartita(partita);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void chiudiConnessione() {
      //TODO
	}

	public void comunicaMovimentoPecoraNera(int nuovaPosizione) {

		try {
			client.movimentoPecoraNera(nuovaPosizione);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}
