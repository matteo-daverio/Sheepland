package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;

import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * classe che comunica con client RMI
 * 
 * @author Valerio De Maria
 * 
 */
public class ComunicazioneRMI implements InterfacciaComunicazioneToClient {

	private String nome;
	private InterfacciaClientRMI client;
	private ControllorePartita gameManager;
	private int turno;
	private static final Logger LOG = Logger.getLogger(ClientRMI.class
			.getName());

	/**
	 * COSTRUTTORE
	 * 
	 * @param nome
	 * @param client
	 * @param gameManager
	 * @param turno
	 * @author Valerio De Maria
	 */
	public ComunicazioneRMI(String nome, InterfacciaClientRMI client,
			ControllorePartita gameManager, int turno) {

		this.nome = nome;
		this.client = client;
		this.gameManager = gameManager;
		this.turno = turno;

		try {
			// cerco il registry del server
			Registry registry = LocateRegistry.getRegistry("localhost",
					Costanti.PORTA_RMI);

			// creo la classe su cui il client può chiamare i metodi
			InterfacciaServerRMI s = new RicezioneRMI(gameManager);

			// esporto l'interfaccia server
			InterfacciaServerRMI stubServerRMI = (InterfacciaServerRMI) UnicastRemoteObject
					.exportObject(s, 0);

			client.riceviStubServer(stubServerRMI);

		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @author Valerio De Maria
	 */
	public String getTipoConnessione() {
		return "rmi";
	}

	/**
	 * 
	 * @author Valerio De Maria
	 */
	public void chiudiConnessione() {

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaMovimentoPecoraNera(int nuovaPosizione) {

		try {
			client.movimentoPecoraNera(nuovaPosizione);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaMovimentoPastore(int posizione, String giocatore,
			int pastore) {
		try {
			client.movimentoPastore(posizione, giocatore, pastore);
		} catch (RemoteException e) {

			LOG.log(Level.SEVERE, "erroreConnessione", e);
		} catch (GameException e) {

			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaAcquistaTessera(TipoTerreno terreno, String giocatore,
			int pastore) {
		try {
			client.acquistoTessera(terreno, giocatore, pastore);
		} catch (RemoteException e) {

			LOG.log(Level.SEVERE, "erroreConnessione", e);
		} catch (GameException e) {

			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaAbbattimento(int regione, int pecora, String giocatore,
			int pastore) {
		try {
			client.abbattimento(regione, pecora, giocatore, pastore);
		} catch (GameException e) {

			LOG.log(Level.SEVERE, "erroreConnessione", e);
		} catch (RemoteException e) {

			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaAccoppiamento(int regione, String giocatore, int pastore) {
		try {
			client.accoppiamento(regione, giocatore, pastore);
		} catch (GameException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		} catch (RemoteException e) {

			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaMovimentoPecora(int pecora, int strada,
			String giocatore, int pastore) {
		try {
			client.movimentoPecora(pecora, strada, giocatore, pastore);
		} catch (GameException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * solo per comunicazione socket
	 * 
	 * @author Valerio De Maria
	 */
	public void setSocket(Socket socket) {

	}

	/**
	 * 
	 * @author Valerio De Maria
	 */
	public void comunicaFaseFinale() {
		try {
			client.faseFinale();
		} catch (RemoteException e) {

			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void inviaDatiGiocatori(List<String> nomi, List<Integer> soldi) {
		try {
			client.riceviDatiGiocatori(nomi, soldi);
			;
		} catch (RemoteException e) {

			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaPecore(List<Pecora> pecore) {
		try {
			client.riceviPecore(pecore);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaPosizionamentoPastore(int turno, int pastore,
			int posizione) {
		try {
			client.riceviAggiornamentoPosizionamentoPastore(turno, pastore,
					posizione);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaTurno(List<Pecora> pecore, int turno) {
		try {
			client.inizioTurno(pecore, turno);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void aggiornaTurno(String giocatore, List<Pecora> pecore) {
		try {
			client.cambioTurno(giocatore, pecore);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaMossaSbagliata() {
		try {
			client.mossaSbagliata();
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void inviaTesseraIniziale(Tessera tesseraIniziale) {
		try {
			client.tesseraIniziale(tesseraIniziale);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void mossaCorretta() {
		try {
			client.mossaCorretta();
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void inviaPunteggi(List<Integer> punteggiFinali, List<String> nomi) {
		try {
			client.punteggiFinali(punteggiFinali, nomi);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaDenaro(List<Integer> denaroPastori) {
		try {
			client.comunicaDenaro(denaroPastori);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaNumeroRecinti(int recinti) {
		try {
			client.comunicaNumeroRecinti(recinti);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void inviaRichiestaMossa(List<MosseEnum> mosseDisponibili) {
		try {
			client.richiestaMossa(mosseDisponibili);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void inviaRichiestaPosizionamento(List<Integer> stradeDisponibili) {
		try {
			client.richiestaPosizionamento(stradeDisponibili);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaMovimentoLupo(int nuovaPosizione) {
		try {
			client.movimentoLupo(nuovaPosizione);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * metodo usato per aggiornare un client che si era disconnesso
	 * 
	 * @author Valerio De Maria
	 */
	public void aggiornamento(List<Pecora> pecore, int posPecoraNera,
			int posLupo, List<Pastore> pastori) {
		try {
			client.aggiornamento(pecore, posPecoraNera, posLupo, pastori);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaStrade(List<Strada> strade) {
		try {
			client.riceviStrade(strade);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaSpostamentoPecoraNera(int strada, String giocatore,
			int pastore) {
		try {
			client.spostamentoPecoraNera(strada, giocatore, pastore);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaDisconnessione(String nome) {
		try {
			client.disconnessione(nome);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaRiconnessione(String nome) {
		try {
			client.risconnessione(nome);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaEsclusione(String nome) {
		try {
			client.esclusione(nome);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

	/**
	 * chiama un metodo su client allo solo scopo di verificarne la connessione
	 * 
	 * @author Valerio De Maria
	 */
	public void ping() {
		try {
			client.pong();
		} catch (RemoteException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "erroreConnessione", e);
		}

	}

}
