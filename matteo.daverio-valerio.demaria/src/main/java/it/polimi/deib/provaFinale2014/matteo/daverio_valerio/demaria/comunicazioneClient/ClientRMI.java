package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Mosse;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ServerApplication;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaGestioneRMI;
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
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.MuoviPastore;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClientRMI implements InterfacciaClientRMI {

	// TODO da chiedere all'utente;
	static String nome = "Valerio";
	static String password = "Ramponio";

	private Partita partita;
	private InterfacciaGestioneRMI server;

	/**
	 * il client riceve l'aggiornamento su un cambio di turno
	 */
	public void cambioTurno(int turno) {
		partita.setTurno(turno);
	}

	/**
	 * il client riceve che il giocatore del turno attuale ha mosso
	 * 
	 * @author Valerio De Maria
	 * @throws InvalidMovementException
	 * @throws NoMoneyException
	 * @throws NoMovementException
	 */
	public void movimentoPastore(int posizione) throws NoMovementException,
			NoMoneyException, InvalidMovementException {
		partita.muoviPastore(posizione);
	}

	/**
	 * ricevo l'istanza della partita creata dal server e l'interfaccia su cui
	 * chiamare i metodi
	 * 
	 * @author Valerio De Maria
	 */
	public void riceviPartita(Partita partita) {

		this.partita = partita;
		gioca();

	}

	/**
	 * ricevo un movimento della pecora nera
	 * 
	 * @author Valerio De Maria
	 */
	public void movimentoPecoraNera(int posizione) {

		partita.getPecoraNera().setPosizione(posizione);
	}

	/**
	 * il server richiede al client di inviargli una mossa
	 * 
	 * @author Valerio De Maria
	 */
	public Mossa inviaMossa(ArrayList<Mosse> mosseDisponibili) {

		// TODO il client deve decidere la mossa da fare
		return (new MuoviPastore(3));
	}

	private void gioca() {

		// TODO

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

			//creo l'interfaccia da passare al server 
			InterfacciaClientRMI client = new ClientRMI();
			
			// esporto l'interfaccia client
			InterfacciaClientRMI stub = (InterfacciaClientRMI) UnicastRemoteObject.exportObject(client, 0);
			
			// eseguo il metodo registrazione dell'oggetto remoto del server
			boolean result = server.registrazione(nome, password,client);

		} catch (RemoteException e) {
			System.err.println("Remote exception:");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("Name " + "istanza" + " not bound.");
		}

	}

	public void start() {

		ricercaConnessione();

		// ora il client rimane in attesa di iniziare tramite "riceviPartita"

	}

	public void acquistoTessera(TipoTerreno terreno) throws RemoteException,
			NoMoreCardsException, NoMoneyException, IllegalShireTypeException {
		partita.compraTessera(terreno);

	}

	public void movimentoPecora(int pecora, Strada strada)
			throws IllegalStreetException, IllegalShireException {
		partita.muoviPecora(pecora, strada);
	}

	public void abbattimento(int regione,int pecora) throws NoSheepInShireException,
			NoMoneyException, IllegalShireException {
		partita.abbatti(regione,pecora);

	}

	public void accoppiamento(int regione) throws IllegalShireException,
			CannotProcreateException {
		partita.accoppia(regione);

	}

	public boolean pong() {
		return true;
	}

}
