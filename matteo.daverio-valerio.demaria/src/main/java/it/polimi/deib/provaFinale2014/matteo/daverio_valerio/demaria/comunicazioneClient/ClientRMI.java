package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
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
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Pong;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

public class ClientRMI implements InterfacciaClientRMI, InterfacciaComunicazioneClient {

	Scanner in = new Scanner(System.in);

	private String nome;
	private String password;

	private String ip_server;
	private int porta;

	private Partita partita;
	private InterfacciaGestioneRMI server;

	/**
	 * costruttore
	 * 
	 * @param IP
	 * @author Valerio De Maria
	 */
	public ClientRMI(String IP,int porta) {
		ip_server = IP;
		this.porta=porta;
		
	}

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
		System.out.println("ho ricevuto la partita");
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
	public Mossa inviaMossa(List<MosseEnum> mosseDisponibili) {

		for (MosseEnum x : mosseDisponibili) {
			System.out.println(x);
		}
		if (mosseDisponibili.size() == 0) {
			System.out.println("invio un pong");
			return (new Pong());
		}
		System.out.println("invio una mossa");
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
			Registry registry = LocateRegistry.getRegistry(ip_server,
					porta);

			// scarico l'oggetto remoto del server
			server = (InterfacciaGestioneRMI) registry.lookup("serverInAttesa");

			// int ID=server.ottieniID();

			// TODO codice talebano
			// System.out.println(ID);

			// esporto l'interfaccia client
			InterfacciaClientRMI stub = (InterfacciaClientRMI) UnicastRemoteObject
					.exportObject(this, 0);

			// eseguo il metodo registrazione dell'oggetto remoto del server
			boolean result = server.registrazione(nome, password, stub);

			// TODO codice talebano
			if (result)
				System.out.println("il server mi ha accettato");
			else
				System.out.println("il server mi ha respinto");

		} catch (RemoteException e) {
			System.err.println("Remote exception:");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("Name " + "istanza" + " not bound.");
		}

	}

	public void logIn() {

		System.out.println("Nome?");
		this.nome = in.nextLine();
		System.out.println("Password?");
		this.password = in.nextLine();
	}

	public void start() {

		logIn();
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

	public void abbattimento(int regione, int pecora)
			throws NoSheepInShireException, NoMoneyException,
			IllegalShireException {
		partita.abbatti(regione, pecora);

	}

	public void accoppiamento(int regione) throws IllegalShireException,
			CannotProcreateException {
		partita.accoppia(regione);

	}

	public boolean pong() {
		return true;
	}

	public void cambioTurno() {
		partita.setTurno(partita.getTurno() + 1);

	}

	public void faseFinale() {
		// TODO Auto-generated method stub
	}

	//NEW
	public boolean effettuaLogin(String nome, String password)
			throws IOException {
		

		try {

			// cerco il registry del server
			Registry registry = LocateRegistry.getRegistry(ip_server,
					porta);

			// scarico l'oggetto remoto del server
			server = (InterfacciaGestioneRMI) registry.lookup("serverInAttesa");

			// esporto l'interfaccia client
			InterfacciaClientRMI stub = (InterfacciaClientRMI) UnicastRemoteObject
					.exportObject(this, 0);

			// eseguo il metodo registrazione dell'oggetto remoto del server
			boolean result = server.registrazione(nome, password, stub);

			System.out.println("ho ricevuto da server");
            return result;

		} catch (RemoteException e) {
			System.err.println("Remote exception:");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("Name " + "istanza" + " not bound.");
		}
		return false;
	}

}
