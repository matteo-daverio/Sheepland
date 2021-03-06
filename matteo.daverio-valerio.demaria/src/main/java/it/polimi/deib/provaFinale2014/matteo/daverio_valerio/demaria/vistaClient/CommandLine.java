package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * grafica con linea di comando
 * 
 * @author Valerio De Maria
 * 
 */
public class CommandLine implements InterfacciaGrafica {

	private Scanner in = new Scanner(System.in);
	private String nome, password;
	private boolean autenticato = false;
	private int numGiocatori, p1, p2, pastoreTurno;
	ControllorePartitaClient controllore;

	private static final Logger LOG=Logger.getLogger(CommandLine.class.getName());

	/**
	 * COSTRUTTORE
	 * 
	 * @param controllore
	 * @author Valerio De Maria
	 */
	public CommandLine(ControllorePartitaClient controllore) {

		this.controllore = controllore;
	}

	/**
	 * metodo di inizio che chiede nome e cognome
	 * 
	 * @author Valerio De Maria
	 */
	public void start() {
		System.out.println("Benvenuto in Sheepland!");

		while (!autenticato) {
			System.out.println("Inserire nome:");
			do {
				nome = in.nextLine();
			} while (nome.equals(""));
			System.out.println("Inserire password:");
			do {
				password = in.nextLine();
			} while (password.equals(""));

			try {
				autenticato = controllore.logIn(nome, password);
			} catch (IOException e) {
				LOG.log(Level.SEVERE,"errore di connessione", e);
			}
		}

		System.out.println("Autenticazione riuscita, attendere inizio partita");

		controllore.riceviAggiornamenti();

	}

	/**
	 * @author Valerio De Maria
	 * @return
	 */
	public int posizionaPastore() {
		System.out
				.println("Dove vuoi posizionare inizialmente il tuo pastore?");
		int posPastore;
		do {
			posPastore = in.nextInt();
		} while (posPastore < 0 && posPastore > 41);

		return posPastore;

	}

	/**
	 * @author Valerio De Maria
	 */
	public void iniziaTurno(List<Pecora> pecore, int turno) {
		System.out.println("Ora è il tuo turno!");

		// nel caso di due giocatori l'utente sceglie il pastore da usare ad
		// ogni turno
		if (numGiocatori == 2) {
			if (turno == 1) {
				p1 = 0;
				p2 = 1;
			} else {
				p1 = 2;
				p2 = 3;
			}
			System.out
					.println("Scegli il pastore che vuoi usare per giocare il turno: "
							+ p1 + " o " + p2);
			do {
				pastoreTurno = in.nextInt();
			} while (pastoreTurno != p1 && pastoreTurno != p2);
		} else {
			pastoreTurno = turno - 1;
		}

		for (Pecora x : pecore) {
			if (x.getTipoPecora() == (Costanti.TIPO_PECORA_PECORA)) {
				System.out.println("C'è una pecora nella regione: "
						+ x.getPosizione());
			} else if (x.getTipoPecora() == (Costanti.TIPO_PECORA_MONTONE)) {
				System.out.println("C'è un montone nella regione: "
						+ x.getPosizione());
			} else
				System.out.println("C'è un agnello nella regione: "
						+ x.getPosizione());
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void cambioTurno(String giocatore, List<Pecora> pecore) {
		System.out.println("Ora è il turno di " + giocatore);

		for (Pecora x : pecore) {
			if (x.getTipoPecora() == (Costanti.TIPO_PECORA_PECORA)) {
				System.out.println("C'è una pecora nella regione: "
						+ x.getPosizione());
			} else if (x.getTipoPecora() == (Costanti.TIPO_PECORA_MONTONE)) {
				System.out.println("C'è un montone nella regione: "
						+ x.getPosizione());
			} else
				System.out.println("C'è un agnello nella regione: "
						+ x.getPosizione());
		}

	}

	/**
	 * comunico il movimento spontaneo della pecora nera
	 * 
	 * @author Valerio De Maria
	 */
	public void muoviPecoraNera(int posizione) {
		System.out.println("La pecora nera è scappata nella regione: "
				+ posizione);

	}

	/**
	 * mostra la posizione delle pecore sulla mappa
	 * 
	 * @param pecore
	 * @author Valerio De Maria
	 */
	public void settaPecore(List<Pecora> pecore) {
		System.out
				.println("Le pecore sono posizionate inizialmente nel modo seguente: ");
		for (Pecora x : pecore) {
			if (x.getTipoPecora() == (Costanti.TIPO_PECORA_PECORA)) {
				System.out.println("C'è una pecora nella regione: "
						+ x.getPosizione());
			} else if (x.getTipoPecora() == (Costanti.TIPO_PECORA_MONTONE)) {
				System.out.println("C'è un montone nella regione: "
						+ x.getPosizione());
			} else
				System.out.println("C'è un agnello nella regione: "
						+ x.getPosizione());
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void nomiGiocatori(List<String> nomi) {
		for (int i = 0; i <= nomi.size() - 1; i++) {
			System.out.println("Il giocatore del turno " + i + " è "
					+ nomi.get(i));
		}

		numGiocatori = nomi.size();

	}

	/**
	 * @author Valerio De Maria
	 */
	public void soldiPastori(List<Integer> soldi) {
		for (int i = 0; i <= soldi.size() - 1; i++) {
			System.out.println("Il pastore " + i + " ha " + soldi.get(i)
					+ " denari");
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void tesseraIniziale(Tessera tesseraIniziale) {
		System.out.println("La tua tessera iniziale è:  "
				+ tesseraIniziale.getTipo());

	}

	/**
	 * @author Valerio De Maria
	 */
	public void posizionamentoPastoreCorretto() {
		System.out.println("hai posizionato il pastore in maniera corretta");

	}

	/**
	 * @author Valerio De Maria
	 */
	public void posizionamentoPastoreErrato() {
		System.out
				.println("la strada che hai inserito non è valida o è occupata!");

	}

	/**
	 * @author Valerio De Maria
	 */
	public void aggiornamentoPosizionePastore(int turno, int pastore,
			int posizione) {
		System.out.println("Il giocatore del turno " + turno
				+ " ha posizionato il pastore " + pastore + " in posizione: "
				+ posizione);

	}

	/**
	 * 
	 * @param mosseDisponibili
	 * @author Valerio De Maria
	 */
	public void visualizzaMosseDisponibili(List<MosseEnum> mosseDisponibili) {
		System.out.println("Puoi effettuare le seguenti mosse:");
		for (int i = 0; i <= mosseDisponibili.size() - 1; i++) {
			System.out.println(i + ") " + mosseDisponibili.get(i));
		}

	}

	/**
	 * 
	 * @param primo
	 * @param secondo
	 * @return
	 * @author Valerio De Maria
	 */
	public int selezionaPastore(int primo, int secondo) {
		System.out
				.println("decidi quale pastore vuoi usare per giocare il tuo turno: "
						+ primo + " oppure " + secondo);
		int pastore;
		do {
			pastore = in.nextInt();
		} while (!(pastore == primo) && !(pastore == secondo));
		return pastore;

	}

	public void movimentoPastore(int posizione, String giocatore, int pastore) {
		System.out.println(giocatore + " ha mosso il pastore " + pastore
				+ " in posizione " + posizione);

	}

	/**
	 * @author Valerio De Maria
	 */
	public void acquistoTessera(TipoTerreno terreno, String giocatore,
			int pastore) {
		System.out.println(giocatore + " ha acquistato una tessera " + terreno
				+ " mediante il pastore " + pastore);

	}

	/**
	 * @author Valerio De Maria
	 */
	public void movimentoPecora(int pecora, int strada, String giocatore,
			int pastore) {
		System.out.println(giocatore + " ha mosso la pecora " + pecora
				+ " lungo la strada " + strada + " tramite il pastore "
				+ pastore);

	}

	/**
	 * @author Valerio De Maria
	 */
	public void abbattimento(int regione, int pecora, String giocatore,
			int pastore) {
		System.out.println(giocatore + " ha abbattuto la pecora " + pecora
				+ " nella regione " + regione + " tramite il pastore "
				+ pastore);

	}

	/**
	 * @author Valerio De Maria
	 */
	public void accoppiamento(int regione, String giocatore, int pastore) {
		System.out.println(giocatore
				+ " ha fatto accoppiare due ovini nella regione " + regione
				+ " mediante il pastore " + pastore);

	}

	/**
	 * 
	 * @param max
	 * @return
	 * @author Valerio De Maria
	 */
	public int chiediMossa(int max) {
		System.out.println("scrivi il numero della mossa che vuoi fare:");
		System.out.println("il max è " + max);
		int scelta;
		do {
			scelta = in.nextInt();
		} while (scelta < 0 || scelta > max);

		System.out.println("hai selezionato " + scelta);

		return scelta;
	}

	/**
	 * @author Valerio De Maria
	 */
	public void mossaSbagliata() {
		System.out.println("La mossa che hai fatto è sbagliata");

	}

	/**
	 * @author Valerio De Maria
	 */
	public void mossaCorretta() {
		System.out.println("La mossa è stata eseguita correttamente");

	}

	/**
	 * @author Valerio De Maria
	 */
	public void faseFinale() {
		System.out.println("Da ora inizia la fase finale del gioco!");
	}

	/**
	 * @author Valerio De Maria
	 */
	public void punteggiFinali(List<Integer> punteggiFinali, List<String> nomi) {
		System.out.println("I punteggi finali sono i seguenti:");
		for (int i = 0; i <= punteggiFinali.size() - 1; i++) {
			System.out.println(nomi.get(i) + " ha ottenuto: "
					+ punteggiFinali.get(i));
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaDenaro(List<Integer> denaroPastori) {

		for (int i = 0; i <= denaroPastori.size() - 1; i++) {
			System.out.println("Il denaro del pastore " + i + " è "
					+ denaroPastori.get(i));
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void comunicaNumeroRecinti(int recinti) {
		System.out.println("I recinti usati sono: " + recinti);

	}

	/**
	 * @author Valerio De Maria
	 */
	public void richiestaPosizionamentoPastore() {
		System.out.println("Posiziona pastore");

		controllore.posizioneInserita(in.nextInt());

	}

	/**
	 * @author Valerio De Maria
	 */
	public void richiestaMossa(List<MosseEnum> mosseDisponibili) {

		System.out.println("Le mosse che puoi fare sono le seguenti:");
		for (int i = 0; i <= mosseDisponibili.size() - 1; i++) {

			System.out.println(i + ") " + mosseDisponibili.get(i));

		}

		System.out.println("Inserisci il numero della mossa che vuoi fare:");
		int mossaScelta;
		do {
			mossaScelta = in.nextInt();
		} while (mossaScelta < 0 || mossaScelta > mosseDisponibili.size());

		// in base alla mossa scelta chiedo info aggiuntive e poi invio la mossa
		switch (mosseDisponibili.get(mossaScelta)) {

		case MUOVI_PASTORE:

			System.out.println("Su che strada vuoi muovere il pastore?");
			controllore.muoviPastore(in.nextInt(), pastoreTurno);
			break;

		case MUOVI_PECORA:

			System.out.println("Che pecora vuoi muovere?");
			int pecoraScelta = in.nextInt();
			System.out.println("Su quale strada la vuoi muovere?");
			controllore.muoviPecora(in.nextInt(), pecoraScelta, pastoreTurno);
			break;

		case COMPRA_TESSERA:

			System.out
					.println("Seleziona il numero corrispondete al tipo di terreno di cui vuoi comprare la tessera:");
			System.out
					.println("1-ACQUA,2-FORESTA,3-GRANO,4-PRATERIA,5-ROCCIA,6-SABBIA");
			int scelta;
			do {
				scelta = in.nextInt();
			} while (scelta < 0 || scelta > 6);

			controllore.compraTessera(scelta, pastoreTurno);
			break;

		case ABBATTI:
			System.out.println("In che regione vuoi abbattere la pecora?");
			int regione = in.nextInt();
			System.out.println("Che pecora vuoi abbattere?");
			controllore.abbatti(regione, in.nextInt(), pastoreTurno);
			break;

		case ACCOPPIA:
			System.out
					.println("Inc che regione vuoi fare accoppiare loe pecore?");
			controllore.accoppia(in.nextInt(), pastoreTurno);
			break;

		case MUOVI_PECORA_NERA:
			System.out.println("Su quale strada vuoi muovere la pecora nera?");
			controllore.spostaPecoraNera(in.nextInt(), pastoreTurno);
			break;

		default:
			break;

		}

		// controllore.mossaFatta(mossaScelta);

	}

	/**
	 * @author Valerio De Maria
	 */
	public void muoviLupo(int posizione) {
		System.out.println("Il lupo si è mosso nella regione: " + posizione);

	}

	/**
	 * @author Valerio De Maria
	 */
	public void aggiornamentoPostDisconnessione(List<Pecora> pecore,
			int posPecoraNera, int posLupo, List<Pastore> pastori) {
		System.out.println("Bentornato!");

		System.out.println("Le pecore sono posizionate nel modo seguente: ");
		for (Pecora x : pecore) {
			if (x.getTipoPecora() == (Costanti.TIPO_PECORA_PECORA)) {
				System.out.println("C'è una pecora nella regione: "
						+ x.getPosizione());
			} else if (x.getTipoPecora() == (Costanti.TIPO_PECORA_MONTONE)) {
				System.out.println("C'è un montone nella regione: "
						+ x.getPosizione());
			} else
				System.out.println("C'è un agnello nella regione: "
						+ x.getPosizione());
		}

		System.out.println("La pecora nera è in: " + posPecoraNera);
		System.out.println("Il lupo è in: " + posLupo);

		for (int i = 0; i <= pastori.size() - 1; i++) {
			System.out.println("Il pastore " + i + " sta sulla strada: "
					+ pastori.get(i).getPosizione());
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public void segnalaDisconnessione() {
		System.out.println("!!CONNESSIONE CON IL SERVER CADUTA!!");

	}

	/**
	 * @author Valerio De Maria
	 */
	public void riceviStrade(List<Strada> strade) {

	}
/**
 * @author Valerio De Maria
 */
	public void spostamentoPecoraNera(int strada, String giocatore, int pastore) {
		System.out.println(giocatore
				+ " ha mosso la pecora nera lungo la strada " + strada
				+ " mediante il pastore " + pastore);
		;

	}

	/**
	 * @author Valerio De Maria
	 */
	public void disconnessione(String nome) {
		System.out.println(nome + " si è disconnesso");

	}

	/**
	 * @author Valerio De Maria
	 */
	public void riconnessione(String nome) {
		System.out.println(nome + " si è riconnesso");

	}

	/**
	 * @author Valerio De Maria
	 */
	public void esclusione(String nome) {
		System.out.println(nome + " è stato escluso dalla partita");

	}

	public void setTest() {
		
	}

}
