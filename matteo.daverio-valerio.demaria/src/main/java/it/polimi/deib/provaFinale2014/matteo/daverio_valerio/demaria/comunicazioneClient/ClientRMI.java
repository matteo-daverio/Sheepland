package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ServerApplication;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaGestioneRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaServerRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
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

public class ClientRMI implements InterfacciaClientRMI, InterfacciaComunicazioneToServer {

	Scanner in = new Scanner(System.in);

	private String nome;
	private String password;

	private String ip_server;
	private int porta;

	private Partita partita;
	private InterfacciaGestioneRMI server;
	private ControllorePartitaClient controllore;
	private InterfacciaServerRMI serverRMI;

	/**
	 * costruttore
	 * 
	 * @param IP
	 * @author Valerio De Maria
	 */
	public ClientRMI(String IP,int porta,ControllorePartitaClient controllore) {
		ip_server = IP;
		this.porta=porta;
		this.controllore=controllore;
		
	}

	/**
	 * il client riceve che il giocatore del turno attuale ha mosso
	 * 
	 * @author Valerio De Maria
	 * @throws InvalidMovementException
	 * @throws NoMoneyException
	 * @throws NoMovementException
	 */
	public void movimentoPastore(int posizione,String giocatore, int pastore) throws GameException {
		controllore.movimentoPastore(posizione, giocatore, pastore);
	}

	/**
	 * ricevo un movimento della pecora nera e lo passo al controllore
	 * 
	 * @author Valerio De Maria
	 */
	public void movimentoPecoraNera(int posizione) {

		controllore.movimentoPecoraNera(posizione);
	}
/*
		public Mossa inviaMossa(List<MosseEnum> mosseDisponibili) {
        
		if (mosseDisponibili.size() == 0) {
			return (new Pong());
		}
		return controllore.richiediMossa(mosseDisponibili);
	}
*/
	public void acquistoTessera(TipoTerreno terreno,String giocatore,int pastore) throws RemoteException,
			GameException {
		controllore.acquistoTessera(terreno, giocatore, pastore);

	}

	public void movimentoPecora(int pecora, int strada,String giocatore, int pastore)
			throws GameException {
		controllore.movimentoPecora(pecora, strada, giocatore, pastore);
	}

	public void abbattimento(int regione, int pecora,String giocatore, int pastore)
			throws GameException {
		controllore.abbattimento(regione, pecora, giocatore, pastore);

	}

	public void accoppiamento(int regione,String giocatore, int pastore) throws GameException {
		controllore.accoppiamento(regione, giocatore, pastore);

	}

	public boolean pong() {
		return true;
	}

	public void faseFinale() {
		controllore.faseFinale();
	}

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

            return result;

		} catch (RemoteException e) {
			System.err.println("Remote exception:");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("Name " + "istanza" + " not bound.");
		}
		return false;
	}
	
//usato solo per socket
	public void riceviAggiornamenti() throws IOException, GameException {
		// TODO Auto-generated method stub
		
	}

	public void inizioTurno() {
		controllore.iniziaTurno();
	}
	
	public void cambioTurno(String giocatore) throws RemoteException {
		controllore.cambioTurno(giocatore);
		
	}

	public void riceviDatiGiocatori(List<String> nomi,List<Integer> soldi)
			throws RemoteException {
		controllore.riceviNomiGiocatori(nomi);
		controllore.riceviSoldiPastori(soldi);
	}

	public void riceviPecore(List<Pecora> pecore) throws RemoteException {
		controllore.settaPecore(pecore);
		
	}
/*
	public int posizionaPastore(List<Integer> stradeDisponibili)
			throws RemoteException {
		return controllore.posizionamentoPastore(stradeDisponibili);
	}
*/
	public void riceviAggiornamentoPosizionamentoPastore(int turno,
			int pastore, int posizione) throws RemoteException {
		
		controllore.aggiornamentoPosizionePastore(turno, pastore, posizione);
	}

	public int selezionaPastore(int primo, int secondo) {
		return controllore.selezionaPastore(primo,secondo);
	}

	public void mossaSbagliata() throws RemoteException {
		controllore.mossaSbagliata();
		
	}

	public void tesseraIniziale(Tessera tesseraIniziale) throws RemoteException {
		controllore.riceviTesseraIniziale(tesseraIniziale);
		
	}

	public void mossaCorretta() throws RemoteException {
		controllore.mossaCorretta();
		
	}

	public void punteggiFinali(List<Integer> punteggiFinali,List<String> nomi)
			throws RemoteException {
		controllore.punteggiFinali(punteggiFinali,nomi);
		
	}

	public void comunicaDenaro(int denaro) {
		controllore.comunicaDenaro(denaro);
		
	}

	public void comunicaNumeroRecinti(int recinti) {
	controllore.comunicaNumeroRecinti(recinti);
		
	}
	
	//// NUOVI METODI

	public void riceviStubServer(InterfacciaServerRMI serverRMI)
			throws RemoteException {
		this.serverRMI=serverRMI;
		
	}


	public void richiestaMossa(List<MosseEnum> mosseDisponibili) throws RemoteException {
		controllore.richiestaMossa(mosseDisponibili);
		
	}

	public void richiestaPosizionamento(List<Integer> stradeDisponibili) throws RemoteException {
		controllore.richiestaPosizionamentoPastore(stradeDisponibili);
		
	}

	public void inviaPosizionePastore(int posizione) {
		try {
			this.serverRMI.riceviPosizionePastore(posizione);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void inviaMossa(Mossa mossa) {
		try {
			this.serverRMI.riceviMossa(mossa);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}





}
