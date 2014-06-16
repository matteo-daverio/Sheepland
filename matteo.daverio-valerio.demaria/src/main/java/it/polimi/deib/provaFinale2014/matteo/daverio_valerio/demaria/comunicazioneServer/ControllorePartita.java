package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.GestorePartite.CreatorePartite;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.net.Socket;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	private boolean primoPastore = true;
	private List<Integer> denaroPastori = new ArrayList<Integer>();
	// pool di thread
	ExecutorService ricezioniSocket = Executors.newCachedThreadPool();

	private List<ThreadRicezioneSocket> ascoltatoriSocket = new ArrayList<ThreadRicezioneSocket>();

	private List<Boolean> giocatoriConnessi = new ArrayList<Boolean>();

	private GestorePartite gestore;
	
	private int numeroTurni;

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

	// //// METODI DA RIVEDERE ////////////////7//////
	/*
	 * private void controllaConnessioneClients(List<MosseEnum>
	 * mosseDisponibili) { Mossa mossa; for (InterfacciaComunicazioneToClient x
	 * : giocatori) { mossa = x.riceviMossa(mosseDisponibili); } }
	 */

	/*
	 * private int selezionaPastore() {
	 * 
	 * if (giocatori.size() > 2) { return partita.getTurno() - 1; }
	 * 
	 * else { if ((partita.getTurno() - 1) == 0) { return
	 * giocatori.get(0).selezionaPastore(0, 1); } else return
	 * giocatori.get(1).selezionaPastore(2, 3); } }
	 */

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
				return true;
			}
		}
		return false;

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
				// se il client che si è disconesso è Socket aggiorno la socket
				if (giocatori.get(i).getTipoConnessione().equals("socket")) {
					giocatori.get(i).setSocket(socket);
					ascoltatoriSocket.get(i).aggiornaSocket(socket);
					giocatoriConnessi.set(i, true);
					aggiornaGiocatoreDisconnesso(i);
				}

			}
		}
	}

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

		for (InterfacciaComunicazioneToClient x : giocatori) {

			x.comunicaMovimentoPecoraNera(nuovaPosizione);

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

	private void inviaTurno() {

		for (int i = 0; i <= giocatori.size() - 1; i++) {

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

	private void comunicaDenaro() {

		// creo la lista aggiornata del denaro dei pastori
		denaroPastori.clear();
		for (int i = 0; i <= partita.getPastori().size() - 1; i++) {

			denaroPastori.add(partita.getPastori().get(i).getDenaro());

		}

		// invio a tutti i giocatori la lista del denaro dei pastori
		for (InterfacciaComunicazioneToClient x : giocatori) {
			x.comunicaDenaro(denaroPastori);
		}
	}

	private void comunicaNumRecinti() {
		for (InterfacciaComunicazioneToClient x : giocatori) {
			x.comunicaNumeroRecinti(partita.getContatoreRecinti());
		}
	}

	private void conteggioPunti() {

		int punteggioTotale;

		if (partita.getPecore().size() == 0 && partita.getPecoraNera() == null) {
			for (InterfacciaComunicazioneToClient x : giocatori) {
				punteggiFinali.add(0);
			}
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

	private void comunicaPunteggiFinali(List<Integer> punteggiFinali) {
		for (InterfacciaComunicazioneToClient x : giocatori) {
			x.inviaPunteggi(punteggiFinali, nomi);
		}

	}

	private void trasferisciGestioneComunicazione() {

		for (Gestione x : connessioni) {
			if (x.getTipoConnessione().equals("socket")) {
				giocatori.add(new ComunicazioneSocket(x.getSocket(), x
						.getBufferOut(), x.getNome()));
				ThreadRicezioneSocket t = new ThreadRicezioneSocket(
						x.getBufferIn(), this);
				ascoltatoriSocket.add(t);
				ricezioniSocket.submit(t);
			} else {

				giocatori.add(new ComunicazioneRMI(x.getNome(), x
						.getInterfacciaClient(), this));

			}

		}
		// setto tutti i giocatori come connessi
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			giocatoriConnessi.add(true);
		}
	}

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
			System.out.println("Invio a " + giocatori.get(i).getNome()
					+ " le liste di nomi e denaro");
			giocatori.get(i).inviaDatiGiocatori(nomi, soldi);
			System.out.println("Invio a " + giocatori.get(i).getNome()
					+ " la sua tessera iniziale");
			giocatori.get(i).inviaTesseraIniziale(tessereIniziali.get(i));
		}

	}

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

	private void inviaPosizioneInizialePecore() {
		for (InterfacciaComunicazioneToClient x : giocatori) {
			x.comunicaPecore(partita.getPecore());
		}
	}

	private void inviaStrade() {
		for (InterfacciaComunicazioneToClient x : giocatori) {
			x.comunicaStrade(partita.getStrade());
		}
	}

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

	private void aggiornaPosizionamentoPastori(int turno, int pastore,
			int posizione) {
		for (int i = 0; i <= giocatori.size() - 1; i++) {

			// non invio l'aggiornamento al giocatore che ha effettuato il
			// posizionamento
			if (i != turno - 1) {
				giocatori.get(i).comunicaPosizionamentoPastore(turno, pastore,
						posizione);
			}

		}
	}

	private void comunicaFaseFinale() {
		for (InterfacciaComunicazioneToClient x : giocatori) {
			x.comunicaFaseFinale();
		}

	}

	private void creaStradeDisponibili() {

		for (Strada x : partita.getStrade()) {
			stradeDisponibili.add(x.getPosizione());
		}
	}

	private void inviaRichiestaPosizionamentoPastore() {

		giocatori.get(partita.getTurno() - 1).inviaRichiestaPosizionamento(
				stradeDisponibili);

	}

	private void comunicaMovimentoLupo() {

		for (InterfacciaComunicazioneToClient x : giocatori) {
			x.comunicaMovimentoLupo(partita.getLupo().getPosizione());
		}
	}

	private void chiediMosse() {
		if (numeroMosse == 3) {
			// cambio il turno
			partita.incrementaTurno();

			// il lupo muove a fine turno
			partita.muoviLupo();

			// comunico ai client il movimento del lupo
			comunicaMovimentoLupo();

			// faccio giocare il turno al giocatore successivo
			giocaTurno();

		} else {

			mosseDisponibili.clear();

			// TODO gestire la disconnessione
			// controllaConnessioneClients(mosseDisponibili);

			mosseDisponibili = calcolaMosseDisponibili(mosseFatte);

			// dico al client che deve inviarmi una mossa
			giocatori.get(partita.getTurno() - 1).inviaRichiestaMossa(
					mosseDisponibili);

		}

	}

	private void controlloFinePartita() {

		if (((partita.getContatoreRecinti() == Costanti.NUMERO_RECINTI_NORMALI)
				&& !faseFinale)||(numeroTurni==6)) {
			comunicaFaseFinale();
			faseFinale = true;
		}
		if (faseFinale && partita.getTurno() == connessioni.size()) {
			finePartita();
		}

	}

	private void giocaTurno() {

		controlloFinePartita();
		
		numeroTurni++;

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

	private void chiudiConnessioni(){
		
	for(InterfacciaComunicazioneToClient x:giocatori){
		x.chiudiConnessione();
	}
	
	for(ThreadRicezioneSocket y:ascoltatoriSocket){
		y.chiudiConnesione();
	}
		
	}
	
	private void finePartita() {

		System.out.println("la partita è finita, conto i punti");
		conteggioPunti();

		comunicaPunteggiFinali(punteggiFinali);
		
		chiudiConnessioni();

		gestore.rimuoviPartitaInCorso(this);
	}

	// ////////METODO CHE VENGONO CHIAMATI DALLE CLASSI CHE RICEVONO INFO DAL
	// CLIENT/////////
	public void riceviMossa(Mossa mossa, int pastoreTurno) {

		try {

			// eseguo la mossa
			System.out.println("vado ad eseguire la mossa");
			mossa.eseguiMossa(partita, giocatori.get(partita.getTurno() - 1)
					.getNome(), pastoreTurno);

			// dico a tutti i client la mossa fatta
			System.out.println("vado ad aggiornare clients");
			mossa.aggiornaClients(giocatori, partita.getTurno());

			// aggiorno l'array di mosse fatte
			System.out.println("vado ad aggiornare mosse fatte");
			mosseFatte = mossa.aggiornaMosseFatte(mosseFatte);

			// al giocatore che ha giocato comunico il denaro del pastore che ha
			// eseguito la mossa
			comunicaDenaro();

			// invio a tutti il numero attuale dei recinti
			comunicaNumRecinti();

			numeroMosse++;

		} catch (Exception e) {

			// dico al client che ha eseguito la mossa che essa non era valida e
			// di conseguenza non aggiorno il contatore delle mosse
			giocatori.get(partita.getTurno() - 1).comunicaMossaSbagliata();
		}

		chiediMosse();

	}

	public void riceviPosizionamentoPastore(int posizioneScelta) {
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
				} while (!(giocatoriConnessi.get(partita.getTurno() - 1) == true));
				giocaTurno();
			}
			// manca qualche posizionamento
			else {
				partita.incrementaTurno();
				// chiedo al client del turno di posizionare il pastore
				inviaRichiestaPosizionamentoPastore();
			}
		} else {
			// se tutti hanno posizionato i propri pastori
			if (partita.getTurno() == giocatori.size() && !primoPastore) {
				do {
					partita.incrementaTurno();
				} while (!(giocatoriConnessi.get(partita.getTurno() - 1) == true));
				giocaTurno();
			}
			// manca qualche posizionamento
			else {
				// incremento turno
				if (!primoPastore) {
					partita.incrementaTurno();
					primoPastore = true;
				} else {
					primoPastore = false;
				}
				// chiedo al client del turno di posizionare il pastore
				inviaRichiestaPosizionamentoPastore();

			}
		}

	}
	
	class ControlloDisconnessione extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
	private void creazioneTimerDisconnessione(){
		
		Timer timer = new Timer();
		ControlloDisconnessione task = new ControlloDisconnessione();
		timer.schedule(task, 0);
		
	}
	
	/**
	 * ciclo principale della partita
	 * 
	 * @author Valerio De Maria
	 */
	public void run() {
		
 
		creazioneTimerDisconnessione();
		
		inizializza();

		// inizializzo l'array delle strade disponibili che serve per
		// posizionare inizialmente i pastori
		creaStradeDisponibili();

		// metodo iniziale della "macchina a stati"
		inviaRichiestaPosizionamentoPastore();

	}

}