package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.GestorePartite.CreatorePartite;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * gestisce l'evolversi della partita
 * 
 * @author Valerio De Maria
 * 
 */
public class ControllorePartita implements Runnable {

	private List<Gestione> connessioni = new ArrayList<Gestione>();
	private Partita partita;
	private boolean faseFinale;
	private List<InterfacciaComunicazioneToClient> giocatori = new ArrayList<InterfacciaComunicazioneToClient>();
	private List<MosseEnum> mosseDisponibili = new ArrayList<MosseEnum>();
	private List<MosseEnum> mosseFatte = new ArrayList<MosseEnum>();
	private List<Integer> punteggiFinali = new ArrayList<Integer>();
	private List<String> nomi = new ArrayList<String>();
	List<Integer> stradeDisponibili = new ArrayList<Integer>();
	private int numeroMosse;
	private InterfacciaServerRMI stubServerRMI;
	private boolean primoPastore = true, pastoriPosizionati = false;
	private List<Integer> denaroPastori = new ArrayList<Integer>();
	// pool di thread
	ExecutorService ricezioniSocket = Executors.newCachedThreadPool();

	private List<ThreadRicezioneSocket> ascoltatoriSocket = new ArrayList<ThreadRicezioneSocket>();

	private List<Boolean> giocatoriConnessi = new ArrayList<Boolean>();
	private List<Boolean> giocatoriEsclusi = new ArrayList<Boolean>();
	private static final Logger LOG = Logger.getLogger(ClientRMI.class
			.getName());

	private GestorePartite gestore;

	/**
	 * COSTRUTTORE
	 * 
	 * @param connessioni
	 * @param partita
	 * @author Valerio De Maria
	 */
	public ControllorePartita(List<Gestione> connessioni, Partita partita,
			GestorePartite gestore) {

		// a quanto pare se io faccio this.connessioni=connessioni l'array di
		// ControllorePartita diventa lo stesso oggetto dell'array di
		// GestorePartite perchè prende il suo riferimento come oggetto
		// quindi sono costretto a copiarmi nell'array della mia classe ogni
		// singolo elemento che mi viene passato
		for (Gestione x : connessioni) {
			this.connessioni.add(x);
		}

		this.partita = partita;

		this.gestore = gestore;

	}

	// /// METODI CHIAMATI DAL GESTORE PARTITE /////////////////////////

	/**
	 * ritorna true se il nome passato come parametro è il nome di uno dei
	 * giocatori della partita
	 * 
	 * @param nome
	 * @return
	 * @author Valerio De Maria
	 */
	public boolean contieneClient(String nome) {

		for (InterfacciaComunicazioneToClient x : giocatori) {
			if (x.getNome().equals(nome)) {
				System.out
						.println("Io controllore partita contengo quel client");
				return true;
			}
		}
		return false;

	}

	public void comunicaEsclusioneDallaPartita() {

	}

	/**
	 * aggiorno i parametri di comunicazione client e reinvio la partita in
	 * corso al client disconnesso
	 * 
	 * @param nome
	 * @param socket
	 * @author Valerio De Maria
	 */
	public void aggiornaComunicazione(String nome, Socket socket) {
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if (giocatori.get(i).getNome().equals(nome)) {

				if (giocatoriEsclusi.get(i).booleanValue() == false) {
					System.out.println("ora reintegro il client");
					// se il client che si è disconesso è Socket aggiorno la
					// socket
					if (giocatori.get(i).getTipoConnessione().equals("socket")) {
						giocatori.get(i).setSocket(socket);
						ascoltatoriSocket.get(i).aggiornaSocket(socket);
						giocatoriConnessi.set(i, true);
					}
					aggiornaGiocatoreDisconnesso(i);
				} else {
					System.out
							.println("Non reintegro il client perchè si è disconnesso per trippo tempo");
					comunicaEsclusioneDallaPartita();
				}

			}
		}
	}

	/**
	 * invia i dati di aggiornamento al client che si era disconnesso
	 * 
	 * @param i
	 * @author Valerio De Maria
	 */
	public void aggiornaGiocatoreDisconnesso(int i) {

		System.out.println("Invio i dati di aggiornamento a: "
				+ giocatori.get(i).getNome());
		giocatori.get(i).aggiornamento(partita.getPecore(),
				partita.getPecoraNera().getPosizione(),
				partita.getLupo().getPosizione(), partita.getPastori());
	}

	// /////////////////////////////////////METODI
	// LOCALI////////////////////////////////////////////

	/**
	 * comunico a tutti i giocatori il movimento della pecora nera
	 * 
	 * @param nuovaPosizione
	 * @author ValerioDe Maria
	 */
	private void comunicaMovimentoPecoraNera(int nuovaPosizione) {

		for (int i = 0; i <= giocatori.size() - 1; i++) {

			if (giocatoriConnessi.get(i).booleanValue() == true) {
				giocatori.get(i).comunicaMovimentoPecoraNera(nuovaPosizione);

			}
		}
	}

	/**
	 * comunico a tutti i giocatori il movimento del lupo
	 * 
	 * @author Valerio De Maria
	 */
	private void comunicaMovimentoLupo() {

		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if (giocatoriConnessi.get(i).booleanValue() == true) {
				giocatori.get(i).comunicaMovimentoLupo(
						partita.getLupo().getPosizione());
			}
		}
	}

	/**
	 * creo la lista di mosse disponibili da inviare al client
	 * 
	 * @param mosseFatte
	 * @return mosseDisponibili
	 * @author Valerio De Maria
	 */

	private List<MosseEnum> calcolaMosseDisponibili(List<MosseEnum> mosseFatte) {

		List<MosseEnum> mosseDisponibili = new ArrayList<MosseEnum>();

		// se è l'ultima mossa del turno e non ho ancora mosso il pastore
		if ((mosseFatte.size() == Costanti.NUMERO_MOSSE_GIOCATORE - 1)
				&& (mosseFatte.get(0) != MosseEnum.MUOVI_PASTORE)
				&& (mosseFatte.get(1) != MosseEnum.MUOVI_PASTORE)) {
			mosseDisponibili.add(MosseEnum.MUOVI_PASTORE);

			return mosseDisponibili;
		} else {
			// se ho fatto almeno una mossa
			if (mosseFatte.size() > 0) {

				// in base all'ultima mossa fatta
				switch (mosseFatte.get(mosseFatte.size() - 1)) {

				case ABBATTI:

					mosseDisponibili.add(MosseEnum.ACCOPPIA);
					mosseDisponibili.add(MosseEnum.COMPRA_TESSERA);
					mosseDisponibili.add(MosseEnum.MUOVI_PASTORE);
					mosseDisponibili.add(MosseEnum.MUOVI_PECORA);
					mosseDisponibili.add(MosseEnum.MUOVI_PECORA_NERA);
					break;
				case ACCOPPIA:

					mosseDisponibili.add(MosseEnum.ABBATTI);
					mosseDisponibili.add(MosseEnum.COMPRA_TESSERA);
					mosseDisponibili.add(MosseEnum.MUOVI_PASTORE);
					mosseDisponibili.add(MosseEnum.MUOVI_PECORA);
					mosseDisponibili.add(MosseEnum.MUOVI_PECORA_NERA);
					break;
				case COMPRA_TESSERA:

					mosseDisponibili.add(MosseEnum.ACCOPPIA);
					mosseDisponibili.add(MosseEnum.ABBATTI);
					mosseDisponibili.add(MosseEnum.MUOVI_PASTORE);
					mosseDisponibili.add(MosseEnum.MUOVI_PECORA);
					mosseDisponibili.add(MosseEnum.MUOVI_PECORA_NERA);
					break;
				case MUOVI_PASTORE:

					mosseDisponibili.add(MosseEnum.ACCOPPIA);
					mosseDisponibili.add(MosseEnum.ABBATTI);
					mosseDisponibili.add(MosseEnum.MUOVI_PECORA);
					mosseDisponibili.add(MosseEnum.COMPRA_TESSERA);
					mosseDisponibili.add(MosseEnum.MUOVI_PECORA_NERA);
					break;
				case MUOVI_PECORA:

					mosseDisponibili.add(MosseEnum.ACCOPPIA);
					mosseDisponibili.add(MosseEnum.ABBATTI);
					mosseDisponibili.add(MosseEnum.COMPRA_TESSERA);
					mosseDisponibili.add(MosseEnum.MUOVI_PASTORE);
					break;
				case MUOVI_PECORA_NERA:

					mosseDisponibili.add(MosseEnum.ACCOPPIA);
					mosseDisponibili.add(MosseEnum.ABBATTI);
					mosseDisponibili.add(MosseEnum.COMPRA_TESSERA);
					mosseDisponibili.add(MosseEnum.MUOVI_PASTORE);
					break;
				default:
					break;
				}// fine switch

			}// fine if numero mosse fatte maggiore di zero
			else {
				// la mia prima mossa può essere una qualsiasi
				mosseDisponibili.add(MosseEnum.ACCOPPIA);
				mosseDisponibili.add(MosseEnum.ABBATTI);
				mosseDisponibili.add(MosseEnum.COMPRA_TESSERA);
				mosseDisponibili.add(MosseEnum.MUOVI_PASTORE);
				mosseDisponibili.add(MosseEnum.MUOVI_PECORA);
				mosseDisponibili.add(MosseEnum.MUOVI_PECORA_NERA);

			}
			return mosseDisponibili;
		}

	}

	/**
	 * al client di turno dico che è il suo turno agli altro comunico di chi è
	 * il turno
	 * 
	 * @author Valerio De Maria
	 */
	private void inviaTurno() {

		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if (giocatoriConnessi.get(i).booleanValue() == true) {
				if (i == (partita.getTurno() - 1)) {
					giocatori.get(i).comunicaTurno(partita.getPecore(),
							partita.getTurno());
				} else {
					giocatori.get(i).aggiornaTurno(
							giocatori.get(partita.getTurno() - 1).getNome(),
							partita.getPecore());
				}
			}
		}
	}

	/**
	 * invio la lista del denaro di ogni pastore
	 * 
	 * @author Valerio De Maria
	 */
	private void comunicaDenaro() {

		// creo la lista aggiornata del denaro dei pastori
		denaroPastori.clear();
		for (int i = 0; i <= partita.getPastori().size() - 1; i++) {

			denaroPastori.add(partita.getPastori().get(i).getDenaro());

		}

		// invio a tutti i giocatori la lista del denaro dei pastori
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if (giocatoriConnessi.get(i).booleanValue() == true) {
				giocatori.get(i).comunicaDenaro(denaroPastori);
			}
		}
	}

	/**
	 * comunico ai client il numero di recinti usati nella partita
	 * 
	 * @author Valerio De Maria
	 */
	private void comunicaNumRecinti() {
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if (giocatoriConnessi.get(i).booleanValue() == true) {
				giocatori.get(i).comunicaNumeroRecinti(
						partita.getContatoreRecinti());
			}
		}
	}

	/**
	 * conteggio dei punti finali
	 * 
	 * @author Valerio De Maria
	 */
	private void conteggioPunti() {

		int punteggioTotale;

		// nessuna pecora rimasta
		if (partita.getPecore().size() == 0 && partita.getPecoraNera() == null) {
			for (InterfacciaComunicazioneToClient x : giocatori) {
				// tutti i giocatori hanno zero punti
				punteggiFinali.add(0);
			}
			// almeno una pecora
		} else {
			if (giocatori.size() > 2) {
				for (int i = 0; i <= giocatori.size() - 1; i++) {
					punteggioTotale = 0;
					for (Tessera x : partita.getPastori().get(i).getTessere()) {
						for (Pecora y : partita.getPecore()) {
							if (partita.getMappaRegioni().get(y.getPosizione()) == x
									.getTipo()) {
								punteggioTotale = punteggioTotale + 1;
							}
							if (partita.getPecoraNera() != null
									&& partita.getMappaRegioni().get(
											partita.getPecoraNera()
													.getPosizione()) == x
											.getTipo()) {
								punteggioTotale = punteggioTotale + 2;
							}
						}

					}
					punteggioTotale = punteggioTotale
							+ partita.getPastori().get(i).getDenaro();

					punteggiFinali.add(punteggioTotale);
				}
			} else {
				// TODO caso di partita con due giocatori
			}
		}

	}

	/**
	 * invio ai client i punteggi finali
	 * 
	 * @param punteggiFinali
	 * @author Valerio De Maria
	 */
	private void comunicaPunteggiFinali(List<Integer> punteggiFinali) {
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if (giocatoriConnessi.get(i).booleanValue() == true) {
				giocatori.get(i).inviaPunteggi(punteggiFinali, nomi);
			}

		}

	}

	/**
	 * dall'array di connessioni mi creo l'array di giocatori che contiene le
	 * classi di comunicazione RMI o socket specifiche per ogni giocatore
	 * 
	 * creo i thread socket che rimangon in ascolto di messaggi da client, la
	 * lista dei giocatori connessi e quella di quelli esclusi dalla partita
	 * 
	 * @author Valerio De Maria
	 */
	private void trasferisciGestioneComunicazione() {

		for (int i = 0; i <= connessioni.size() - 1; i++) {
			if (connessioni.get(i).getTipoConnessione().equals("socket")) {
				giocatori.add(new ComunicazioneSocket(connessioni.get(i)
						.getSocket(), connessioni.get(i).getBufferOut(),
						connessioni.get(i).getNome(), this, i + 1));
				ThreadRicezioneSocket t = new ThreadRicezioneSocket(connessioni
						.get(i).getBufferIn(), this, i + 1);
				ascoltatoriSocket.add(t);
				ricezioniSocket.submit(t);
			} else {

				giocatori.add(new ComunicazioneRMI(
						connessioni.get(i).getNome(), connessioni.get(i)
								.getInterfacciaClient(), this, i + 1));

			}

		}
		// setto tutti i giocatori come connessi
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			giocatoriConnessi.add(true);
		}
		// setto tutti i giocatori come inclusi
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			giocatoriEsclusi.add(false);
		}
	}

	/**
	 * invio inizialmente i nomi i soldi e le tessere iniziali
	 * 
	 * @author Valerio De Maria
	 */
	private void inviaDatiGiocatori() {

		List<Integer> soldi = new ArrayList<Integer>();
		List<Tessera> tessereIniziali = new ArrayList<Tessera>();

		// creo la lista di nomi
		for (InterfacciaComunicazioneToClient x : giocatori) {
			nomi.add(x.getNome());
		}

		// creo la lista dei soldi
		for (Pastore x : partita.getPastori()) {
			soldi.add(x.getDenaro());
		}

		// creo la lista delle tessereIniziali
		for (Pastore x : partita.getPastori()) {
			if (x.getTessere().size() > 0) {
				tessereIniziali.add(x.getTessere().get(0));
			}
		}

		// invio nomi e soldi a tutti i giocatori e ad ognuno la propria tessera
		// iniziale
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if (giocatoriConnessi.get(i).booleanValue() == true) {
				giocatori.get(i).inviaDatiGiocatori(nomi, soldi);
				giocatori.get(i).inviaTesseraIniziale(tessereIniziali.get(i));
			}
		}

	}

	/**
	 * imposto il denaro iniziale di ogni pastore
	 * 
	 * @author Valerio De Maria
	 */
	private void impostaDenaro() {
		if (giocatori.size() > 2) {
			for (Pastore x : partita.getPastori()) {
				x.setDenaro(20);
			}
		} else {
			partita.getPastori().get(0).setDenaro(30);
			partita.getPastori().get(1).setDenaro(0);
			partita.getPastori().get(2).setDenaro(30);
			partita.getPastori().get(3).setDenaro(0);
		}
	}

	/**
	 * imposto la tessera iniziale di ogni giocatore
	 * 
	 * @author Valerio De Maria
	 */
	private void impostaTesseraIniziale() {

		List<Tessera> tessereIniziali = new ArrayList<Tessera>();

		int scelta;
		// creo la lista di tessere iniziali
		tessereIniziali.add(new Tessera(TipoTerreno.ACQUA, 0));
		tessereIniziali.add(new Tessera(TipoTerreno.FORESTA, 0));
		tessereIniziali.add(new Tessera(TipoTerreno.GRANO, 0));
		tessereIniziali.add(new Tessera(TipoTerreno.PRATERIA, 0));
		tessereIniziali.add(new Tessera(TipoTerreno.ROCCIA, 0));
		tessereIniziali.add(new Tessera(TipoTerreno.SABBIA, 0));

		int top, step;
		if (giocatori.size() > 2) {
			top = partita.getPastori().size() - 1;
			step = 0;
		} else {
			top = 1;
			step = 1;
		}
		System.out.println(top + " e " + step);
		for (int i = 0; i <= top; i++) {

			// scelgo un numero casuale tra 0 ed il numero di tessere iniziali
			// rimasto -1
			do {
				scelta = (int) (Math.random() * Costanti.NUMERO_TIPI_TERRENO);
			} while (scelta >= tessereIniziali.size());
			System.out.println("scelta vale: " + scelta);
			// aggiungo la tessera iniziale scelta alle tessere del pastore
			partita.getPastori().get(i + step * i)
					.aggiungiTessera(tessereIniziali.get(scelta));
			// tolgo la tessera asseganta dalle tessereIniziali disponibili
			tessereIniziali.remove(scelta);
		}

	}

	/**
	 * invio inizialmente l'array di pecore
	 * 
	 * @author Valerio De Maria
	 */
	private void inviaPosizioneInizialePecore() {
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if (giocatoriConnessi.get(i).booleanValue() == true) {
				giocatori.get(i).comunicaPecore(partita.getPecore());
			}
		}
	}

	/**
	 * invio l'array di strade
	 * 
	 * @author Valerio De Maria
	 */
	private void inviaStrade() {
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if (giocatoriConnessi.get(i).booleanValue() == true) {
				giocatori.get(i).comunicaStrade(partita.getStrade());
			}

		}
	}

	/**
	 * aggiungo i pastori all'array di pastori di partita in base ai giocatori
	 * che ho
	 * 
	 * @author Valerio De Maria
	 */
	private void aggiungiPastori() {

		if (giocatori.size() > 2) {
			for (int i = 0; i <= giocatori.size() - 1; i++) {
				partita.getPastori().add(
						new Pastore(giocatori.get(i).getNome(), i + 1));
			}
		} else {
			partita.getPastori()
					.add(new Pastore(giocatori.get(0).getNome(), 1));
			partita.getPastori()
					.add(new Pastore(giocatori.get(0).getNome(), 1));
			partita.getPastori()
					.add(new Pastore(giocatori.get(1).getNome(), 2));
			partita.getPastori()
					.add(new Pastore(giocatori.get(1).getNome(), 2));
		}
	}

	/**
	 * metodo che fa le prime inizializzazioni preliminari
	 * 
	 * @author Valerio De Maria
	 */
	private void inizializza() {

		// creo le classi di comunicazione che si occuperrano di comunicare con
		// i client in maniera trasparente rispetto alla modalità di connessione
		trasferisciGestioneComunicazione();

		partita.start();

		partita.setNumeroGiocatori(giocatori.size());

		inviaPosizioneInizialePecore();

		inviaStrade();

		aggiungiPastori();

		impostaDenaro();

		impostaTesseraIniziale();

		inviaDatiGiocatori();

	}

	/**
	 * dico ai client non di turno che il client di turno ha posizionato il suo
	 * pastore
	 * 
	 * @param turno
	 * @param pastore
	 * @param posizione
	 * @author Valerio De Maria
	 */
	private void aggiornaPosizionamentoPastori(int turno, int pastore,
			int posizione) {
		for (int i = 0; i <= giocatori.size() - 1; i++) {

			// non invio l'aggiornamento al giocatore che ha effettuato il
			// posizionamento e a quelli disconnessi
			if ((i != (turno - 1))
					&& (giocatoriConnessi.get(i).booleanValue() == true)) {
				giocatori.get(i).comunicaPosizionamentoPastore(turno, pastore,
						posizione);
			}

		}
	}

	/**
	 * avviso i client che sono entrato nella fase finale
	 * 
	 * @author Valerio De Maria
	 */
	private void comunicaFaseFinale() {
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if (giocatoriConnessi.get(i).booleanValue() == true) {
				giocatori.get(i).comunicaFaseFinale();
			}
		}

	}

	/**
	 * creo un array di strade disponibili, inizialmente uguale a strade, che
	 * verrà poi modificato in seguito ai posizionamenti dei pastori
	 * 
	 * @author Valerio De Maria
	 */
	private void creaStradeDisponibili() {

		for (Strada x : partita.getStrade()) {
			stradeDisponibili.add(x.getPosizione());
		}
	}

	/**
	 * se il giocatore di turno è connesso gli invio una richiesta di
	 * posizionamento pastore
	 * 
	 * @author Valerio De Maria
	 */
	private void inviaRichiestaPosizionamentoPastore() {
		if (giocatoriConnessi.get(partita.getTurno() - 1).booleanValue() == true) {
			giocatori.get(partita.getTurno() - 1).inviaRichiestaPosizionamento(
					stradeDisponibili);
		}
		else{
		do {
			partita.incrementaTurno();
		} while (giocatoriConnessi.get(partita.getTurno() - 1).booleanValue() == false);
		}

	}

	/**
	 * dico al giocatore di turno che deve fare una mossa se gli mancano delle
	 * mosse da fare
	 * 
	 * @author Valerio De Maria
	 */
	private void chiediMosse() {
		if (numeroMosse == 3) {
			// cambio il turno
			do {
				partita.incrementaTurno();
			} while (giocatoriConnessi.get(partita.getTurno() - 1)
					.booleanValue() == false);

			// il lupo muove a fine turno
			partita.muoviLupo();

			// comunico ai client il movimento del lupo
			comunicaMovimentoLupo();

			// faccio giocare il turno al giocatore successivo
			giocaTurno();

		} else {

			mosseDisponibili.clear();

			mosseDisponibili = calcolaMosseDisponibili(mosseFatte);

			// dico al client che deve inviarmi una mossa
			if (giocatoriConnessi.get(partita.getTurno() - 1).booleanValue() == true) {
				giocatori.get(partita.getTurno() - 1).inviaRichiestaMossa(
						mosseDisponibili);
			} else {

				// cambio il turno
				do {
					partita.incrementaTurno();
				} while (giocatoriConnessi.get(partita.getTurno() - 1)
						.booleanValue() == false);

				// il lupo muove a fine turno
				partita.muoviLupo();

				// comunico ai client il movimento del lupo
				comunicaMovimentoLupo();

				// faccio giocare il turno al giocatore successivo
				giocaTurno();
			}

		}

	}

	/**
	 * controllo se ci sono i parametri necessari per far finire la partita
	 * 
	 * @author Valerio De Maria
	 */
	private void controlloFinePartita() {

		// condizioni per entrare in fase finale
		if (((partita.getContatoreRecinti() == Costanti.NUMERO_RECINTI_NORMALI) && !faseFinale)) {
			comunicaFaseFinale();
			faseFinale = true;
		}
		// condizioni per finire la partita
		if (faseFinale && partita.getTurno() == connessioni.size()) {
			finePartita();
		}

		// controllo che ci sia almeno un giocatore incluso nella partita
		boolean finePartita = true;
		for (int i = 0; i <= giocatoriEsclusi.size() - 1; i++) {
			if (giocatoriEsclusi.get(i).booleanValue() == false) {
				finePartita = false;
			}
		}
		if (finePartita) {
			finePartita();
		}

	}

	/**
	 * inizializzo il turno e faccio partire la sequenza di richiesta mosse
	 * 
	 * @author Valerio De Maria
	 */
	private void giocaTurno() {

		controlloFinePartita();

		// aggiorno i giocatori sul turno attuale
		inviaTurno();

		// la pecora nera muove all'inizio di ogni nuovo turno
		partita.muoviPecoraNera();

		// dico ai client che la pecora nera si è mossa
		comunicaMovimentoPecoraNera(partita.getPecoraNera().getPosizione());

		numeroMosse = 0;
		mosseFatte.clear();

		chiediMosse();

	}

	/**
	 * chiudo le porte socket e connessioni RMI
	 * 
	 * @author Valerio De Maria
	 */
	private void chiudiConnessioni() {

		for (InterfacciaComunicazioneToClient x : giocatori) {
			x.chiudiConnessione();
		}

		for (ThreadRicezioneSocket y : ascoltatoriSocket) {
			y.chiudiConnesione();
		}

	}

	/**
	 * esegue le operazioni da fare una volta finita la partita
	 * 
	 * @author Valerio De Maria
	 */
	private void finePartita() {

		System.out.println("la partita è finita, conto i punti");
		conteggioPunti();

		comunicaPunteggiFinali(punteggiFinali);

		chiudiConnessioni();

		gestore.rimuoviPartitaInCorso(this);
	}

	// ////////METODO CHE VENGONO CHIAMATI DALLE CLASSI CHE RICEVONO INFO DAL
	// CLIENT/////////

	/**
	 * chiamato per inviare una mossa al server, quest'ultimo prova ad eseguirla
	 * e comunica i risultati
	 * 
	 * @param mossa
	 * @param pastoreTurno
	 * @author Valerio De Maria
	 */
	public void riceviMossa(Mossa mossa, int pastoreTurno) {

		try {

			// eseguo la mossa

			mossa.eseguiMossa(partita, giocatori.get(partita.getTurno() - 1)
					.getNome(), pastoreTurno);

			// dico a tutti i client la mossa fatta

			mossa.aggiornaClients(giocatori, partita.getTurno(),
					giocatoriConnessi);

			// aggiorno l'array di mosse fatte

			mosseFatte = mossa.aggiornaMosseFatte(mosseFatte);

			// al giocatore che ha giocato comunico il denaro del pastore che ha
			// eseguito la mossa
			comunicaDenaro();

			// invio a tutti il numero attuale dei recinti
			comunicaNumRecinti();

			numeroMosse++;

		} catch (Exception e) {
			LOG.log(Level.SEVERE, "ECCEZZIONE", e);

			// dico al client che ha eseguito la mossa che essa non era valida e
			// di conseguenza non aggiorno il contatore delle mosse
			giocatori.get(partita.getTurno() - 1).comunicaMossaSbagliata();
		}

		chiediMosse();

	}

	/**
	 * metodo che riceve il posizionamento di un pastore, la validita di questo
	 * spostamento viene fatta su client
	 * 
	 * @param posizioneScelta
	 * @author Valerio De Maria
	 */
	public void riceviPosizionamentoPastore(int posizioneScelta) {

		System.out.println("turno attuale: "+partita.getTurno());
		// // setto posizione pastore
		if (giocatori.size() > 2) {

			partita.getPastori().get(partita.getTurno() - 1)
					.setPosizione(posizioneScelta);

		} else {
			if (partita.getTurno() == 1) {
				if (primoPastore) {
					partita.getPastori().get(0).setPosizione(posizioneScelta);
				} else {
					partita.getPastori().get(1).setPosizione(posizioneScelta);
				}
			} else {
				if (primoPastore) {
					partita.getPastori().get(2).setPosizione(posizioneScelta);
				} else {
					partita.getPastori().get(3).setPosizione(posizioneScelta);
				}
			}

		}

		// aggiorno le strade disponibili per il posizionamento dei pastori
		stradeDisponibili.remove(stradeDisponibili.indexOf(posizioneScelta));

		// invio ai client l'aggiornamento sul posizionamento pastore
		if (giocatori.size() > 2) {
			aggiornaPosizionamentoPastori(partita.getTurno(),
					partita.getTurno() - 1, posizioneScelta);

		} else {
			if (partita.getTurno() == 1) {
				if (primoPastore) {
					aggiornaPosizionamentoPastori(partita.getTurno(), 0,
							posizioneScelta);
				} else {
					aggiornaPosizionamentoPastori(partita.getTurno(), 1,
							posizioneScelta);
				}
			} else {
				if (primoPastore) {
					aggiornaPosizionamentoPastori(partita.getTurno(), 2,
							posizioneScelta);
				} else {
					aggiornaPosizionamentoPastori(partita.getTurno(), 3,
							posizioneScelta);
				}
			}
		}

		if (partita.getNumeroGiocatori() > 2) {
			// se tutti hanno posizionato i propri pastori
			if (partita.getTurno() == giocatori.size()) {

				do {
					partita.incrementaTurno();
				} while (giocatoriConnessi.get(partita.getTurno() - 1)
						.booleanValue() == false);

				pastoriPosizionati = true;
				giocaTurno();
			}
			// manca qualche posizionamento
			else {
				do {
					partita.incrementaTurno();
				} while (giocatoriConnessi.get(partita.getTurno() - 1)
						.booleanValue() == false);
				// chiedo al client del turno di posizionare il pastore
				inviaRichiestaPosizionamentoPastore();
			}
		} else {
			// se tutti hanno posizionato i propri pastori
			if (partita.getTurno() == giocatori.size() && !primoPastore) {
				do {
					partita.incrementaTurno();
				} while (giocatoriConnessi.get(partita.getTurno() - 1) == false);
				pastoriPosizionati = true;
				giocaTurno();
			}
			// manca qualche posizionamento
			else {
				// incremento turno
				if (!primoPastore) {
					do {
						partita.incrementaTurno();
					} while (giocatoriConnessi.get(partita.getTurno() - 1) == false);
					primoPastore = true;
				} else {
					primoPastore = false;
				}
				// chiedo al client del turno di posizionare il pastore
				inviaRichiestaPosizionamentoPastore();

			}
		}

	}

	/**
	 * dico ai miei giocatori che il giocatore di un cert turno è stato escluso
	 * dalla partita
	 * 
	 * @param turno
	 * @author Valerio De Maria
	 */
	public void comunicaEsclusione(int turno) {

		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if (giocatoriConnessi.get(i).booleanValue() == true) {
				giocatori.get(i).comunicaEsclusione(
						giocatori.get(turno - 1).getNome());
			}
		}

	}

	/**
	 * dico ai miei giocatori che il giocatore di un certo turno si è
	 * disconnesso
	 * 
	 * @param turno
	 * @author Valerio De Maria
	 */
	public void comunicaDisconnessione(int turno) {
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if (giocatoriConnessi.get(i).booleanValue() == true) {
				giocatori.get(i).comunicaDisconnessione(
						giocatori.get(turno - 1).getNome());
			}
		}
	}

	/**
	 * dico ai miei giocatori che il giocatore di un certo turno si è riconnesso
	 * 
	 * @param turno
	 * @author Valerio De Maria
	 */
	public void comunicaRiconnessione(int turno) {
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if ((giocatoriConnessi.get(i).booleanValue() == true)
					&& (i != (turno - 1))) {
				giocatori.get(i).comunicaRiconnessione(
						giocatori.get(turno - 1).getNome());
			}
		}
	}

	/**
	 * inner class che controlla se è il caso di escludere un giocatore in
	 * seguito ad una lunga disconnessione
	 * 
	 * @author Valerio De Maria
	 * 
	 */
	class SegnalaDisconnessione extends TimerTask {

		private ControllorePartita p;
		private int turno;

		/**
		 * COSTRUTTORE
		 * 
		 * @param p
		 * @param turno
		 * @author Valerio De Maria
		 */
		public SegnalaDisconnessione(ControllorePartita p, int turno) {
			this.p = p;
			this.turno = turno;
		}

		/**
		 * run
		 * 
		 * @author Valerio De Maria
		 */
		@Override
		public void run() {
			if (p.giocatoriConnessi.get(turno - 1).booleanValue() == false) {
				System.out
						.println("Escludo il client di turno dalla partita perchè è disconnesso da troppo tempo");
				p.giocatoriEsclusi.set(turno - 1, true);
				p.comunicaEsclusione(turno);
			} else {
				System.out
						.println("Il giocatore si è riconnesso nel frattempo");
			}
		}

	}

	/**
	 * metodo chiamato dalle classi di comunicazione quando si verifica
	 * un'eccezione nel collegamento con il client
	 * 
	 * @param turno
	 * @author Valerio De Maria
	 */
	public void avvioTimerDisconnessione(int turno) {

		// segno il giocatore del turno relativo alla classe di comunicazione
		// che ha chiamato questo metodo come disconnesso
		giocatoriConnessi.set(turno - 1, false);

		// avviso gli altri giocatori che quel giocatore si è disconnesso
		comunicaDisconnessione(turno);

		// se chi si è disconnesso era di turno devo far procedere il gioco
		if (turno == partita.getTurno()) {
			do {
				partita.incrementaTurno();

			} while (giocatoriConnessi.get(partita.getTurno() - 1)
					.booleanValue() == false);
			if (pastoriPosizionati) {
				giocaTurno();
			} else {
				inviaRichiestaPosizionamentoPastore();
			}
		}

		// avvio del timer
		Timer timer = new Timer();
		SegnalaDisconnessione task = new SegnalaDisconnessione(this, turno);
		System.out.println("Parte il timer di segnalazione di disconnessione");
		timer.schedule(task, Costanti.TEMPO_RICONNESSIONE);

	}

	private void avvioControlloreDisconnessioniRMI() {

		class ControlloConnessioneRMI extends TimerTask {

			private ControllorePartita p;

			public ControlloConnessioneRMI(ControllorePartita p) {
				this.p = p;
			}

			@Override
			public void run() {
				for (int i = 0; i <= p.giocatori.size() - 1; i++) {
					if (p.giocatori.get(i).getTipoConnessione().equals("rmi")
							&& (p.giocatoriConnessi.get(i).booleanValue() == true)) {

						p.giocatori.get(i).ping();
					}
				}

			}

		}

		// inizializzo il timer
		Timer timer3 = new Timer();
		ControlloConnessioneRMI task = new ControlloConnessioneRMI(this);
		timer3.schedule(task, 0, Costanti.PERIODO_CONTROLLO_RMI);
	}

	/**
	 * ciclo principale della partita
	 * 
	 * @author Valerio De Maria
	 */
	public void run() {

		inizializza();

		avvioControlloreDisconnessioniRMI();

		// inizializzo l'array delle strade disponibili che serve per
		// posizionare inizialmente i pastori
		creaStradeDisponibili();

		// metodo iniziale della "macchina a stati"
		inviaRichiestaPosizionamentoPastore();
		

	}

}