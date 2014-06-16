package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaGestioneRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaServerRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * classe che gestisce la comunicazione RMI lato client
 * 
 * @author Valerio De Maria
 * 
 */
public class ClientRMI implements InterfacciaClientRMI,
		InterfacciaComunicazioneToServer {

	Scanner in = new Scanner(System.in);

	private String ip_server;
	private int porta;
	private InterfacciaGestioneRMI server;
	private ControllorePartitaClient controllore;
	private InterfacciaServerRMI serverRMI;
	private static final Logger LOG=Logger.getLogger(ClientRMI.class.getName());

	/**
	 * COSTRUTTORE
	 * 
	 * @param IP
	 * @author Valerio De Maria
	 */
	public ClientRMI(String IP, int porta, ControllorePartitaClient controllore) {
		ip_server = IP;
		this.porta = porta;
		this.controllore = controllore;

	}

	/**
	 * metodo di log-in
	 * 
	 * @author Valerio De Maria
	 */
	public boolean effettuaLogin(String nome, String password)
			throws IOException {

		try {

			// cerco il registry del server
			Registry registry = LocateRegistry.getRegistry(ip_server, porta);

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
			LOG.log(Level.SEVERE,"Errore di connessione", e);
		} catch (NotBoundException e) {
			System.err.println("Name " + "istanza" + " not bound.");
		}
		return false;
	}

	/**
	 * ricevo che il giocatore del turno attuale ha mosso la pecora nera
	 * 
	 * @author Valerio De Maria
	 */
	public void spostamentoPecoraNera(int strada, String giocatore, int pastore)
			throws RemoteException {
		controllore.spostamentoPecoraNera(strada, giocatore, pastore);

	}

	/**
	 * ricevo che il giocatore del turno attuale ha mosso il pastore
	 * 
	 * @throws GameException
	 * @author Valerio De Maria
	 * 
	 */
	public void movimentoPastore(int posizione, String giocatore, int pastore)
			throws GameException {
		controllore.movimentoPastore(posizione, giocatore, pastore);
	}

	/**
	 * ricevo che il giocatore del turno attuale ha acquistato una tessera
	 * 
	 * @author Valerio De Maria
	 */
	public void acquistoTessera(TipoTerreno terreno, String giocatore,
			int pastore) throws RemoteException, GameException {
		controllore.acquistoTessera(terreno, giocatore, pastore);

	}

	/**
	 * ricevo che il giocatore del turno attuale ha mosso una pecora
	 * 
	 * @author Valerio De Maria
	 */
	public void movimentoPecora(int pecora, int strada, String giocatore,
			int pastore) throws GameException {
		controllore.movimentoPecora(pecora, strada, giocatore, pastore);
	}

	/**
	 * ricevo che il giocatore del turno attuale ha fatto un abbattimento
	 * 
	 * @author Valerio De Maria
	 */
	public void abbattimento(int regione, int pecora, String giocatore,
			int pastore) throws GameException {
		controllore.abbattimento(regione, pecora, giocatore, pastore);

	}

	/**
	 * ricevo che il giocatore del turno attuale ha fatto un accoppiamento
	 * 
	 * @author Valerio De Maria
	 */
	public void accoppiamento(int regione, String giocatore, int pastore)
			throws GameException {
		controllore.accoppiamento(regione, giocatore, pastore);

	}

	/**
	 * ricevo un movimento della pecora nera e lo passo al controllore
	 * 
	 * @author Valerio De Maria
	 */
	public void movimentoPecoraNera(int posizione) {

		controllore.movimentoPecoraNera(posizione);
	}

	/**
	 * ricevo un movimento del lupo e lo passo al controllore
	 * 
	 * @author Valerio De Maria
	 */
	public void movimentoLupo(int posizione) throws RemoteException {
		controllore.movimentoLupo(posizione);

	}

	/**
	 * comunico all'utente che è iniziata la fase finale
	 * 
	 * @author Valerio De Maria
	 */
	public void faseFinale() {
		controllore.faseFinale();
	}

	/**
	 * dico all'utente che è suo turno di gioco e passo le pecore attuali e il
	 * numero di turno
	 * 
	 * @author Valerio De Maria
	 */
	public void inizioTurno(List<Pecora> pecore, int turno) {
		controllore.iniziaTurno(pecore, turno);
	}

	/**
	 * comunico un cambio di turno, con aggiornamento delle pecore attuali
	 * 
	 * @author Valerio De Maria
	 */
	public void cambioTurno(String giocatore, List<Pecora> pecore)
			throws RemoteException {
		controllore.cambioTurno(giocatore, pecore);

	}

	/**
	 * comunico i dati iniziali dei giocatori
	 * 
	 * @author Valerio De Maria
	 */
	public void riceviDatiGiocatori(List<String> nomi, List<Integer> soldi)
			throws RemoteException {
		controllore.riceviNomiGiocatori(nomi);
		controllore.riceviSoldiPastori(soldi);
	}

	/**
	 * invia la lista delle pecore iniziali
	 * 
	 * @author Valerio De Maria
	 */
	public void riceviPecore(List<Pecora> pecore) throws RemoteException {
		controllore.settaPecore(pecore);

	}

	/**
	 * invia la lista delle strade
	 * 
	 * @author Valerio De Maria
	 */
	public void riceviStrade(List<Strada> strade) throws RemoteException {
		controllore.riceviStrade(strade);

	}

	/**
	 * comunica la tessera terreno iniziale
	 * 
	 * @author Valerio De Maria
	 */
	public void tesseraIniziale(Tessera tesseraIniziale) throws RemoteException {
		controllore.riceviTesseraIniziale(tesseraIniziale);

	}

	/**
	 * comunica il posizionamento di un pastore avversario
	 * 
	 * @author Valerio De Maria
	 */
	public void riceviAggiornamentoPosizionamentoPastore(int turno,
			int pastore, int posizione) throws RemoteException {

		controllore.aggiornamentoPosizionePastore(turno, pastore, posizione);
	}

	/**
	 * comunica che la mossa fatta è sbagliata
	 * 
	 * @author Valerio De Maria
	 */
	public void mossaSbagliata() throws RemoteException {
		controllore.mossaSbagliata();

	}

	/**
	 * comunica che la mossa fatta è corretta
	 * 
	 * @author Valerio De Maria
	 */
	public void mossaCorretta() throws RemoteException {
		controllore.mossaCorretta();

	}

	/**
	 * comunico i punteggi finali
	 * 
	 * @author Valerio De Maria
	 */
	public void punteggiFinali(List<Integer> punteggiFinali, List<String> nomi)
			throws RemoteException {
		controllore.punteggiFinali(punteggiFinali, nomi);

	}

	/**
	 * aggiorno l'utente riguardo al denaro dei vari pastori
	 * 
	 * @author Valerio De Maria
	 */
	public void comunicaDenaro(List<Integer> denaroPastori) {
		controllore.comunicaDenaro(denaroPastori);

	}

	/**
	 * aggiorno l'utente riguardo al numero di recinti usati fino ad ora
	 * 
	 * @author Valerio De Maria
	 */
	public void comunicaNumeroRecinti(int recinti) {
		controllore.comunicaNumeroRecinti(recinti);

	}

	/**
	 * metodo che aggiorna il client dopo una sua disconnessione
	 * 
	 * @author Valerio De Maria
	 */
	public void aggiornamento(List<Pecora> pecore, int posPecoraNera,
			int PosLupo, List<Pastore> pastori) throws RemoteException {
		controllore.aggiornamentoPostDisconnessione(pecore, posPecoraNera,
				PosLupo, pastori);

	}

	/**
	 * ricevo l'oggetto del server su cui posso chiamare dei metodi per inviare
	 * dati
	 * 
	 * @author Valerio De Maria
	 */
	public void riceviStubServer(InterfacciaServerRMI serverRMI)
			throws RemoteException {
		this.serverRMI = serverRMI;

	}

	/**
	 * il server chiede all'utente di inviargli una mossa
	 * 
	 * @author Valerio De Maria
	 */
	public void richiestaMossa(List<MosseEnum> mosseDisponibili)
			throws RemoteException {
		controllore.richiestaMossa(mosseDisponibili);

	}

	/**
	 * il server chiede all'utente di posizionare il pastore
	 * 
	 * @author Valerio De Maria
	 */
	public void richiestaPosizionamento(List<Integer> stradeDisponibili)
			throws RemoteException {
		controllore.richiestaPosizionamentoPastore(stradeDisponibili);

	}

	// usato solo per socket
	public void riceviAggiornamenti() throws IOException, GameException {

	}

	public boolean pong() {
		return true;
	}

	// // FROM CLIENT TO SERVER

	/**
	 * invio la posizione inziale del mio pastore
	 * 
	 * @author Valerio De Maria
	 */
	public void inviaPosizionePastore(int posizione) {
		try {
			this.serverRMI.riceviPosizionePastore(posizione);
		} catch (RemoteException e) {

			controllore.segnalaDisconnessione();
			LOG.log(Level.SEVERE,"Errore in connessione", e);
		}

	}

	/**
	 * invio la mossa che voglio eseguire
	 * 
	 * @author Valerio De Maria
	 */
	public void inviaMossa(Mossa mossa, int pastoreTurno) {
		try {
			this.serverRMI.riceviMossa(mossa, pastoreTurno);
		} catch (RemoteException e) {
			controllore.segnalaDisconnessione();
			LOG.log(Level.SEVERE,"Errore in connessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void disconnessione(String nome) throws RemoteException {
		controllore.disconnessione(nome);
		
	}

	/**
	 * @author Valerio De Maria
	 */
	public void risconnessione(String nome) throws RemoteException {
		controllore.riconnessione(nome);
		
	}

	/**
	 * @author Valerio De Maria
	 */
	public void esclusione(String nome) throws RemoteException {
		controllore.esclusione(nome);
		
	}

}
