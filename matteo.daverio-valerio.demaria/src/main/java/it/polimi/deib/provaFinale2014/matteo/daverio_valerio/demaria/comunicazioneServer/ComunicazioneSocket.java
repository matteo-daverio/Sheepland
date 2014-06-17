package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * classe che invia messaggi ai client socket
 * 
 * @author Valerio De Maria
 * 
 */
public class ComunicazioneSocket implements InterfacciaComunicazioneToClient {

	private Socket socket;
	private String nome;
	private ObjectOutputStream out;
	private ControllorePartita gameManager;
	private int turno;
	private static final Logger LOG = Logger.getLogger(ClientRMI.class
			.getName());

	/**
	 * COSTRUTTORE
	 * 
	 * @param socket
	 * @param in
	 * @param out
	 * @param nome
	 * @author Valerio De Maria
	 */
	public ComunicazioneSocket(Socket socket, ObjectOutputStream out,
			String nome, ControllorePartita gamManager, int turno) {

		this.socket = socket;
		this.out = out;
		this.nome = nome;
		this.gameManager = gameManager;
	}

	/**
	 * ritorna il nome
	 * 
	 * @author Valerio De Maria
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * ritorna il tipo di connessione
	 * 
	 * @author Valerio De Maria
	 */
	public String getTipoConnessione() {
		return "socket";
	}

	/**
	 * setta la socket e riaggiorna il buffer di uscita
	 * 
	 * @author Valerio De Maria
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in settaggio socket", e);
		}

	}

	/**
	 * chiudo la connessione
	 * 
	 * @author Valerio De Maria
	 */
	public void chiudiConnessione() {

		try {
			out.close();
			socket.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "errore in chiusura socket", e);
		}
	}

	/**
	 * comunica il movimento della pecora nera
	 * 
	 * @author Valerio De Maria
	 */
	public void comunicaMovimentoPecoraNera(int nuovaPosizione) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.MOVIMENTO_PECORA_NERA);
			out.flush();
			out.reset();
			out.writeInt(nuovaPosizione);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in invio mossa pecora", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void inviaRichiestaMossa(List<MosseEnum> mosseDisponibili) {
		try {
			// richiedo una mossa
			out.reset();
			out.writeObject(ComandiSocket.RICHIESTA_DI_MOSSA);
			out.flush();
			// invio la lista di mosse disponibili
			out.reset();
			out.writeObject(mosseDisponibili);
			out.flush();

		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione richiesta mossa", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaFaseFinale() {
		try {
			out.reset();
			out.writeObject(ComandiSocket.FASE_FINALE);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione faseFinale", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaTurno(List<Pecora> pecore, int turno) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.INIZIO_TURNO);
			out.flush();

			out.reset();
			out.writeObject(pecore);
			out.flush();

			out.reset();
			out.writeInt(turno);
			out.flush();

		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione turno", e);
		}
	}

	/**
	 * @author Valerio De Maria
	 */
	public void inviaRichiestaPosizionamento(List<Integer> stradeDisponibili) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.RICHIESTA_POSIZIONE_PASTORE);
			out.flush();
			out.reset();
			out.writeObject(stradeDisponibili);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in invio richiesta posizionamento", e);
		}
	}

	/**
	 * @author Valerio De Maria
	 */
	public void inviaDatiGiocatori(List<String> nomi, List<Integer> soldi) {
		try {

			out.reset();
			out.writeObject(ComandiSocket.DATI_GIOCATORI);
			out.flush();

			out.reset();
			out.writeObject(nomi);
			out.flush();

			out.reset();
			out.writeObject(soldi);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in invio dati giocatore", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaPecore(List<Pecora> pecore) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.INVIO_PECORE);
			out.flush();
			out.reset();
			out.writeObject(pecore);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione pecore", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaMovimentoPastore(int posizione, String giocatore,
			int pastore) {

		try {

			out.reset();
			out.writeObject(ComandiSocket.MOVIMENTO_PASTORE);
			out.flush();

			out.reset();
			out.writeInt(posizione);
			out.flush();

			out.reset();
			out.writeObject(giocatore);
			out.flush();

			out.reset();
			out.writeInt(pastore);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione movimento pastore",
					e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaAcquistaTessera(TipoTerreno terreno, String giocatore,
			int pastore) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.ACQUISTO_TESSERA);
			out.flush();

			out.reset();
			out.writeObject(terreno);
			out.flush();

			out.reset();
			out.writeObject(giocatore);
			out.flush();

			out.reset();
			out.writeInt(pastore);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione acquisto tessera", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaAbbattimento(int regione, int pecora, String giocatore,
			int pastore) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.ABBATTIMENTO);
			out.flush();

			out.reset();
			out.writeInt(regione);
			out.flush();

			out.reset();
			out.writeInt(pecora);
			out.flush();

			out.reset();
			out.writeObject(giocatore);
			out.flush();

			out.reset();
			out.writeInt(pastore);
			out.reset();

		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione abbattimento", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaAccoppiamento(int regione, String giocatore, int pastore) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.ACCOPPIAMENTO);
			out.flush();

			out.reset();
			out.writeInt(regione);
			out.flush();

			out.reset();
			out.writeObject(giocatore);
			out.flush();

			out.reset();
			out.writeInt(pastore);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione accoppiamento", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaMovimentoPecora(int pecora, int strada,
			String giocatore, int pastore) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.MOVIMENTO_PECORA);
			out.flush();

			out.reset();
			out.writeInt(pecora);
			out.flush();

			out.reset();
			out.writeInt(strada);
			out.flush();

			out.reset();
			out.writeObject(giocatore);
			out.flush();

			out.reset();
			out.writeInt(pastore);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione movimento pecora", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaPosizionamentoPastore(int turno, int pastore,
			int posizione) {
		try {

			out.reset();
			out.writeObject(ComandiSocket.POSIZIONAMENTO_PASTORE);
			out.flush();

			out.reset();
			out.writeInt(turno);
			out.flush();

			out.reset();
			out.writeInt(pastore);
			out.flush();

			out.reset();
			out.writeInt(posizione);
			out.flush();

		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE,
					"errore in comunicazione posizionamento pastore", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void aggiornaTurno(String giocatore, List<Pecora> pecore) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.CAMBIO_TURNO);
			out.flush();

			out.reset();
			out.writeObject(giocatore);
			out.flush();

			out.reset();
			out.writeObject(pecore);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in aggiornamento turno", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaMossaSbagliata() {
		try {
			out.reset();
			out.writeObject(ComandiSocket.MOSSA_SBAGLIATA);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione mossa sbagliata", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void mossaCorretta() {
		try {
			out.reset();
			out.writeObject(ComandiSocket.MOSSA_CORRETTA);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione mossa corretta", e);
		}
	}

	/**
	 * @author Valerio De Maria
	 */
	public void inviaTesseraIniziale(Tessera tesseraIniziale) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.TESSERA_INIZIALE);
			out.flush();

			out.reset();
			out.writeObject(tesseraIniziale);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in invio tessera iniziale", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void inviaPunteggi(List<Integer> punteggiFinali, List<String> nomi) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.PUNTEGGI);
			out.flush();

			out.reset();
			out.writeObject(punteggiFinali);
			out.flush();

			out.reset();
			out.writeObject(nomi);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in invio punteggi", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaNumeroRecinti(int recinti) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.NUMERO_RECINTI);
			out.flush();

			out.reset();
			out.writeInt(recinti);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione numero recinti", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaDenaro(List<Integer> denaroPastori) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.AGGIORNAMENTO_DENARO);
			out.flush();

			out.reset();
			out.writeObject(denaroPastori);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione denaro", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaMovimentoLupo(int nuovaPosizione) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.MOVIMENTO_LUPO);
			out.flush();

			out.reset();
			out.writeInt(nuovaPosizione);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione movimento lupo", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void aggiornamento(List<Pecora> pecore, int posPecoraNera,
			int posLupo, List<Pastore> pastori) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.AGGIORNAMENTO);
			out.flush();

			out.reset();
			out.writeObject(pecore);
			out.flush();

			out.reset();
			out.writeInt(posPecoraNera);
			out.flush();

			out.reset();
			out.writeInt(posLupo);
			out.flush();

			out.reset();
			out.writeObject(pastori);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione aggiornamento", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaStrade(List<Strada> strade) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.INVIO_STRADE);
			out.flush();

			out.reset();
			out.writeObject(strade);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE, "errore in comunicazione strade", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaSpostamentoPecoraNera(int strada, String giocatore,
			int pastore) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.SPOSTAMENTO_PECORA_NERA);
			out.flush();

			out.reset();
			out.writeInt(strada);
			out.flush();

			out.reset();
			out.writeObject(giocatore);
			out.flush();

			out.reset();
			out.writeInt(pastore);
			out.flush();
		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE,
					"errore in comunicazione spostamento pecora nera", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaDisconnessione(String nome) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.DISCONNESSIONE);
			out.flush();

			out.reset();
			out.writeObject(nome);
			out.flush();

		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE,
					"errore in comunicazione spostamento pecora nera", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaRiconnessione(String nome) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.RICONNESSIONE);
			out.flush();

			out.reset();
			out.writeObject(nome);
			out.flush();

		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE,
					"errore in comunicazione spostamento pecora nera", e);
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaEsclusione(String nome) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.ESCLUSIONE);
			out.flush();

			out.reset();
			out.writeObject(nome);
			out.flush();

		} catch (IOException e) {
			gameManager.avvioTimerDisconnessione(turno);
			LOG.log(Level.SEVERE,
					"errore in comunicazione spostamento pecora nera", e);
		}

	}

	/**
	 * usato in RMI per verificare la connessione del client
	 * @author Valerio De Maria
	 */
	public void ping() {
		
		
	}

}
