package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLine implements InterfacciaGrafica {

	private Scanner in = new Scanner(System.in);
	private String nome, password;
	private boolean autenticato = false;
	ControllorePartitaClient controllore;

	public CommandLine(ControllorePartitaClient controllore) {

		this.controllore = controllore;
	}

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
				e.printStackTrace();
			}
		}
		
		System.out.println("Autenticazione riuscita, attendere inizio partita");

	    controllore.riceviAggiornamenti();

	}

	public int posizionaPastore() {
		System.out
				.println("Dove vuoi posizionare inizialmente il tuo pastore?");
		int posPastore;
		do {
			posPastore = in.nextInt();
		} while (posPastore < 0 && posPastore > 41);

		return posPastore;

	}

	public void iniziaTurno() {
		System.out.println("Ora è il tuo turno!");

	}

	public void cambioTurno(String giocatore) {
		System.out.println("Ora è il turno di "+giocatore);
		
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

	public void nomiGiocatori(List<String> nomi) {
		for (int i = 0; i <= nomi.size() - 1; i++) {
			System.out.println("Il giocatore del turno " + i + " è "
					+ nomi.get(i));
		}

	}

	public void soldiPastori(List<Integer> soldi) {
		for (int i = 0; i <= soldi.size() - 1; i++) {
			System.out.println("Il pastore " + i + " ha " + soldi.get(i)
					+ " denari");
		}

	}

	public void tesseraIniziale(Tessera tesseraIniziale) {
			System.out.println("La tua tessera iniziale è:  " +tesseraIniziale.getTipo());

	}
	
	public void posizionamentoPastoreCorretto() {
		System.out.println("hai posizionato il pastore in maniera corretta");

	}

	public void posizionamentoPastoreErrato() {
		System.out
				.println("la strada che hai inserito non è valida o è occupata!");

	}

	public void aggiornamentoPosizionePastore(int turno, int pastore,
			int posizione) {
		System.out.println("Il giocatore del turno " + turno
				+ " ha posizionato il pastore " + pastore + " in posizione: "
				+ posizione);

	}

	public void visualizzaMosseDisponibili(List<MosseEnum> mosseDisponibili) {
		System.out.println("Puoi effettuare le seguenti mosse:");
		for(int i=0; i<=mosseDisponibili.size()-1;i++){
			System.out.println(i+") "+mosseDisponibili.get(i));
		}
		
	}

	public int selezionaPastore(int primo, int secondo) {
		System.out.println("decidi quale pastore vuoi usare per giocare il tuo turno: "+primo+" oppure "+secondo);
		int pastore;
		do{
			pastore=in.nextInt();
		}while(!(pastore==primo)&&!(pastore==secondo));
		return pastore;
		
	}

	public void movimentoPastore(int posizione, String giocatore, int pastore) {
		System.out.println(giocatore+" ha mosso il pastore "+pastore+" in posizione "+posizione);
		
	}

	public void acquistoTessera(TipoTerreno terreno, String giocatore,
			int pastore) {
		System.out.println(giocatore+" ha acquistato una tessera "+ terreno+ " mediante il pastore "+pastore);
		
	}

	public void movimentoPecora(int pecora, int strada, String giocatore,
			int pastore) {
		System.out.println(giocatore+" ha mosso la pecora "+pecora+" lungo la strada "+strada+" tramite il pastore "+pastore);
		
	}

	public void abbattimento(int regione, int pecora, String giocatore,
			int pastore) {
		System.out.println(giocatore+" ha abbattuto la pecora "+pecora+" nella regione "+regione+" tramite il pastore "+pastore);
		
	}

	public void accoppiamento(int regione, String giocatore, int pastore) {
		System.out.println(giocatore+" ha fatto accoppiare due ovini nella regione "+regione+" mediante il pastore "+pastore);
		
	}

	public int chiediMossa(int max) {
		System.out.println("scrivi il numero della mossa che vuoi fare:");
		System.out.println("il max è "+max);
		int scelta;
		do{
			scelta=in.nextInt();
		}while (scelta<0 || scelta>max);
		
		System.out.println("hai selezionato "+scelta);
		
		return scelta;
	}

	public int scegliRegione() {
		System.out.println("Seleziona la regione:");
		
		return in.nextInt();
	}

	public int scegliStrada() {
		System.out.println("Scegli la strada:");
		return in.nextInt();
	}

	public int scegliPecora() {
		System.out.println("Scegli la pecora:");
		return in.nextInt();
	}

	public void mossaSbagliata() {
		System.out.println("La mossa che hai fatto è sbagliata");
		
	}

	public int scegliTipoTerreno() {
		System.out.println("Seleziona il numero corrispondete al tipo di terreno di cui vuoi comprare la tessera:");
		System.out.println("1-ACQUA,2-FORESTA,3-GRANO,4-PRATERIA,5-ROCCIA,6-SABBIA");
		int scelta;
		do{
			scelta=in.nextInt();
		}while(scelta<0||scelta>6);
		return scelta;
	}

	public void mossaCorretta() {
		System.out.println("La mossa è stata eseguita correttamente");
		
	}

	public void faseFinale() {
		System.out.println("Da ora inizia la fase finale del gioco!");		
	}

	public void punteggiFinali(List<Integer> punteggiFinali,List<String> nomi) {
		System.out.println("I punteggi finali sono i seguenti:");
		for (int i=0;i<=punteggiFinali.size()-1;i++){
			System.out.println(nomi.get(i)+" ha ottenuto: "+punteggiFinali.get(i));
		}
		
	}

	public void comunicaDenaro(int denaro) {
		System.out.println("Il tuo denaro è: "+ denaro);
		
	}

	public void comunicaNumeroRecinti(int recinti) {
		System.out.println("I recinti usati sono: "+recinti);
		
	}



}
