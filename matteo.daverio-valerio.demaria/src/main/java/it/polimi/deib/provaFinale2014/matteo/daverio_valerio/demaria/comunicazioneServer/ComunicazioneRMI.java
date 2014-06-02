package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ServerApplication;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

/**
 * classe che comunica con client RMI
 * 
 * @author Valerio De Maria
 * 
 */
public class ComunicazioneRMI implements InterfacciaComunicazioneClient,
		InterfacciaComunicazioneRMI {

	private String nome, password;
	private InterfacciaClientRMI client;

	// costruttore
	public ComunicazioneRMI(String nome, String password) {

		this.nome = nome;
		this.password = password;

		// IL SERVER CERCA L'INTERFACCIA CLIENT
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

		// IL SERVER CARICA SUL REGISTRY LA NUOVA INTERFACCIA
		try {

			// creo l'oggetto remoto da caricare sul server
			InterfacciaComunicazioneRMI server = new ComunicazioneRMI(nome,
					password);
			// esporto l'oggetto remoto
			InterfacciaComunicazioneRMI stub = (InterfacciaComunicazioneRMI) UnicastRemoteObject
					.exportObject(server, 0);
			// creo un registry
			Registry registry = LocateRegistry
					.createRegistry(Costanti.PORTA_RMI);
			// associo all'oggetto remoto esportato un nome
			registry.rebind("server" + nome + password, stub);

		} catch (RemoteException e) {
			System.err.println("ComputeEngine exception:");
			e.printStackTrace();
		}

	}

	public Mossa riceviMossa() {
		try {
			return client.inviaMossa();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void inviaPartita(Partita partita) {

		try {
			client.riceviPartita(partita);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void chiudiConnessione() {
		// TODO
	}

	public void comunicaMovimentoPecoraNera(int nuovaPosizione) {

		try {
			client.movimentoPecoraNera(nuovaPosizione);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void comunicaMovimentoPastore(int posizione) {
		try {
			client.movimentoPastore(posizione);
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (NoMovementException e) {
			
			e.printStackTrace();
		} catch (NoMoneyException e) {
			
			e.printStackTrace();
		} catch (InvalidMovementException e) {
			
			e.printStackTrace();
		}
		
	}

	public void comunicaAcquistaTessera(TipoTerreno terreno) {
		try {
			client.acquistoTessera(terreno);
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (NoMoreCardsException e) {
			
			e.printStackTrace();
		} catch (NoMoneyException e) {
			
			e.printStackTrace();
		} catch (IllegalShireTypeException e) {
			
			e.printStackTrace();
		}
		
	}

	public void comunicaAbbattimento(int regione) {
		try {
			client.abbattimento(regione);
		} catch (NoSheepInShireException e) {

			e.printStackTrace();
		} catch (NoMoneyException e) {

			e.printStackTrace();
		} catch (IllegalShireException e) {
		
			e.printStackTrace();
		}
		
	}

	public void comunicaAccoppiamento(int regione) {
		try {
			client.accoppiamento(regione);
		} catch (IllegalShireException e) {
			e.printStackTrace();
		} catch (CannotProcreateException e) {
			e.printStackTrace();
		}
		
	}

	public void comunicaMovimentoPecora(int pecora, Strada strada) {
	try {
		client.movimentoPecora(pecora, strada);
	} catch (IllegalStreetException e) {
		e.printStackTrace();
	} catch (IllegalShireException e) {
		e.printStackTrace();
	}
		
	}
}
