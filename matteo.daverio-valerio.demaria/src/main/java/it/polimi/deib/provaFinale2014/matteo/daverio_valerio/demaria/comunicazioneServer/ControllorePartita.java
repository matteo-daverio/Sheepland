package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * gestisce l'evolversi della partita
 * 
 * @author Valerio De Maria
 * 
 */
public abstract class ControllorePartita implements Runnable {

	private List<Gestione> connessioni;
	private Partita partita;
	private boolean finePartita, faseFinale;
	private List<InterfacciaComunicazioneClient> giocatori = new ArrayList<InterfacciaComunicazioneClient>();
	private List<MosseEnum> mosseDisponibili = new ArrayList<MosseEnum>();
	private List<MosseEnum> mosseFatte = new ArrayList<MosseEnum>();

	// costruttore
	public ControllorePartita(List<Gestione> connessioni, Partita partita) {

		this.connessioni = connessioni;
		this.partita = partita;

	}

	/**
	 * comunico a tutti i giocatori il movimento della pecora nera
	 * 
	 * @param nuovaPosizione
	 * @author ValerioDe Maria
	 */
	private void comunicaMovimentoPecoraNera(int nuovaPosizione) {

		for (InterfacciaComunicazioneClient x : giocatori) {

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
			System.out.println("il client può muovere solo il pastore");
			return mosseDisponibili;
		} else {
			// se ho fatto almeno una mossa
			if (mosseFatte.size() > 0) {
				System.out.println("il client ha fatto almeno una mossa");
				// in base all'ultima mossa fatta
				switch (mosseFatte.get(mosseFatte.size() - 1)) {

				case ABBATTI:
					System.out.println("non può abbattere");
					mosseDisponibili.add(MosseEnum.ACCOPPIA);
					mosseDisponibili.add(MosseEnum.COMPRA_TESSERA);
					mosseDisponibili.add(MosseEnum.MUOVI_PASTORE);
					mosseDisponibili.add(MosseEnum.MUOVI_PECORA);
					break;
				case ACCOPPIA:
					System.out.println("non può accoppiare");
					mosseDisponibili.add(MosseEnum.ABBATTI);
					mosseDisponibili.add(MosseEnum.COMPRA_TESSERA);
					mosseDisponibili.add(MosseEnum.MUOVI_PASTORE);
					mosseDisponibili.add(MosseEnum.MUOVI_PECORA);
					break;
				case COMPRA_TESSERA:
					System.out.println("non può comprare");
					mosseDisponibili.add(MosseEnum.ACCOPPIA);
					mosseDisponibili.add(MosseEnum.ABBATTI);
					mosseDisponibili.add(MosseEnum.MUOVI_PASTORE);
					mosseDisponibili.add(MosseEnum.MUOVI_PECORA);
					break;
				case MUOVI_PASTORE:
					System.out.println("non può muovere il pastore");
					mosseDisponibili.add(MosseEnum.ACCOPPIA);
					mosseDisponibili.add(MosseEnum.ABBATTI);
					mosseDisponibili.add(MosseEnum.MUOVI_PECORA);
					mosseDisponibili.add(MosseEnum.COMPRA_TESSERA);
					break;
				case MUOVI_PECORA:
					System.out.println("non può muovere la pecora");
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
				System.out.println("prima mossa->può fare quello che vuole");
			}
			return mosseDisponibili;
		}

	}

	/**
	 * metodo che riceve la mossa inviata dal client
	 * 
	 * @param mosseDisponibili
	 * @return
	 * @author Valerio De Maria
	 */
	public Mossa riceviMossa(List<MosseEnum> mosseDisponibili) {

		return giocatori.get(partita.getTurno() - 1).riceviMossa(
				mosseDisponibili);
	}

	/**
	 * per ogni client richiedo un pong
	 * 
	 * @param mosseDisponibili
	 * @author Valerio De Maria
	 */
	private void controllaConnessioneClients(List<MosseEnum> mosseDisponibili) {
		Mossa mossa;
		for (InterfacciaComunicazioneClient x : giocatori) {
			mossa = x.riceviMossa(mosseDisponibili);
		}
	}

	/**
	 * svolgo le azioni da compiere per ogni turno
	 * 
	 * @author Valerio De Maria
	 */
	private void giocaTurno() {

		// la pecora nera muove all'inizio di ogni nuovo turno
		partita.muoviPecoraNera();

		// dico ai client che la pecora nera si è mossa
		comunicaMovimentoPecoraNera(partita.getPecoraNera().getPosizione());

		mosseFatte.clear();
		// il giocatore compie le mosse che può fare nel turno
		for (int i = 0; i <= Costanti.NUMERO_MOSSE_GIOCATORE; i++) {

			// avviso il giocatore di turno che è il suo momento
			giocatori.get(partita.getTurno() - 1).comunicaInizioTurno();

			mosseDisponibili.clear();

			controllaConnessioneClients(mosseDisponibili);
			mosseDisponibili = calcolaMosseDisponibili(mosseFatte);

			Mossa mossa = riceviMossa(mosseDisponibili);

			try {
				System.out.println("vado ad eseguire la mossa");
				mossa.eseguiMossa(partita);

				// faccio eseguire su tutti i client la mossa fatta
				System.out.println("vado ad aggiornare clients");
				mossa.aggiornaClients(giocatori, partita.getTurno());

				System.out.println("vado ad aggiornare mosse fatte");
				mosseFatte = mossa.aggiornaMosseFatte(mosseFatte);

			} catch (Exception e) {
			}

		}// fine ciclo for

	}

	private void conteggioPunti() {
		// TODO
	}

	/**
	 * la creazione dei pastori dipende dal numero dei giocatori della partita
	 * 
	 * @author Valerio De Maria
	 */
	abstract void aggiungiPastori(List<Gestione> connessioni, Partita partita);

	/**
	 * invio ad ogni client l'istanza della partita in corso
	 * 
	 * @author Valerio De Maria
	 * @throws RemoteException
	 */
	private void inviaPartita() {

		for (InterfacciaComunicazioneClient x : giocatori) {

			x.inviaPartita(partita);

		}
	}

	/**
	 * ritorna true se il nome passato come parametro è il nome di uno dei
	 * giocatori della partita
	 * 
	 * @param nome
	 * @return
	 * @author Valerio De Maria
	 */
	public boolean contieneClient(String nome) {

		for (InterfacciaComunicazioneClient x : giocatori) {
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
		for (InterfacciaComunicazioneClient x : giocatori) {
			if (x.getNome().equals(nome)) {
				// se il client che si è disconesso è Socket aggiorno la socket
				if (x.getTipoConnessione().equals("socket")) {
					x.setSocket(socket);
				}
				// invio la partita in corso sia che si sia disoconnesso un
				// client RMI che uno Socket
				x.inviaPartita(partita);
			}
		}
	}

	/**
	 * per ogni client creo una nuova classe di comunicazione soket o RMI
	 * 
	 * @author Valerio De Maria
	 */
	private void trasferisciGestioneComunicazione() {

		for (Gestione x : connessioni) {
			if (x.getTipoConnessione().equals("socket")) {
				giocatori.add(new ComunicazioneSocket(x.getSocket(), x
						.getBufferIn(), x.getBufferOut(), x.getNome()));
			} else {

				giocatori.add(new ComunicazioneRMI(x.getNome(), x
						.getInterfacciaClient()));
			}

		}
	}

	abstract void posizionaPastori(
			List<InterfacciaComunicazioneClient> giocatori, Partita partita);

	private void comunicaFinePartita() {
		// TODO
	}

	public void inviaDatiGiocatori() {

		List<String> nomi = new ArrayList<String>();
		List<Integer> soldi = new ArrayList<Integer>();
		List<Tessera> tessereIniziali = new ArrayList<Tessera>();

		// creo la lista di nomi
		for (InterfacciaComunicazioneClient x : giocatori) {
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

		// invio le liste a tutti i giocatori
		for (InterfacciaComunicazioneClient x : giocatori) {
			x.inviaDatiGiocatori(nomi, soldi, tessereIniziali);
		}
	}

	public void impostaDenaro() {
		if (giocatori.size() > 2) {
			for (Pastore x : partita.getPastori()) {
				x.setDenaro(20);
			}
		} else {
			partita.getPastori().get(0).setDenaro(30);
			partita.getPastori().get(1).setDenaro(30);
			partita.getPastori().get(2).setDenaro(0);
			partita.getPastori().get(3).setDenaro(0);
		}
	}

	public void impostaTesseraIniziale() {
		List<Tessera> tessereIniziali = new ArrayList<Tessera>();

		// creo la lista di tessere iniziali
		tessereIniziali.add(new Tessera(TipoTerreno.ACQUA, 0));
		tessereIniziali.add(new Tessera(TipoTerreno.FORESTA, 0));
		tessereIniziali.add(new Tessera(TipoTerreno.GRANO, 0));
		tessereIniziali.add(new Tessera(TipoTerreno.PRATERIA, 0));
		tessereIniziali.add(new Tessera(TipoTerreno.ROCCIA, 0));
		tessereIniziali.add(new Tessera(TipoTerreno.SABBIA, 0));

		int top;
		if (giocatori.size() > 2) {
			top = partita.getPastori().size() - 1;
		} else
			top = 2;

		for (int i = 0; i <= top; i++) {

			// scelgo un numero casuale tra 0 ed il numero di tessere iniziali
			// rimasto -1
			int scelta = (int) (Math.random() * Costanti.NUMERO_TIPI_TERRENO)
					- i + 1;
			// aggiungo la tessera iniziale scelta alle tessere del pastore
			partita.getPastori().get(i)
					.aggiungiTessera(tessereIniziali.get(scelta));
			// tolgo la tessera asseganta dalle tessereIniziali disponibili
			tessereIniziali.remove(scelta);
		}

	}
	
	public void inviaPosizioneInizialePecore(){
		for(InterfacciaComunicazioneClient x:giocatori){
			x.comunicaPecore(partita.getPecore());
		}
	}

	public void inizializza() {
		
		// creo le classi di comunicazione che si occuperrano di comunicare con
		// i client in maniera trasparente rispetto alla modalità di connessione
		trasferisciGestioneComunicazione();
		
		partita.start();
		
		inviaPosizioneInizialePecore();

		aggiungiPastori(connessioni, partita);

		impostaDenaro();

		impostaTesseraIniziale();

		inviaDatiGiocatori();
	}

	/**
	 * ciclo principale della partita
	 * 
	 * @author Valerio De Maria
	 */
	public void run() {

		inizializza();

		System.out.println("invio la partita " + giocatori.get(0).getNome()
				+ " e " + giocatori.get(1).getNome());
		inviaPartita();

		posizionaPastori(giocatori, partita);

		finePartita = false;
		faseFinale = false;
		int f = 0;
		while (!finePartita) {

			giocaTurno();
			partita.incrementaTurno();

			if (partita.getTurno() > connessioni.size()) {
				partita.muoviLupo();
				partita.setTurno(1);
			}
			comunicaCambioTurno();
			System.out.println("ora è il turno di "
					+ giocatori.get(partita.getTurno() - 1).getNome());
			f++;
			if (f == 4) {
				finePartita = true;
			}
			if (partita.getContatoreRecinti() == Costanti.NUMERO_RECINTI_NORMALI) {
				comunicaFaseFinale();
				faseFinale = true;
			}
			if (faseFinale && partita.getTurno() == connessioni.size()) {
				finePartita = true;
			}

		}// fine del while

		System.out.println("la partita è finita, conto i punti");
		conteggioPunti();

		System.out.println("comunico ai client che la partita è finita");
		comunicaFinePartita();

	}

	/**
	 * comunico ai giocatori che è iniziata la fase finale
	 * 
	 * @author Valerio De Maria
	 */
	private void comunicaFaseFinale() {
		for (InterfacciaComunicazioneClient x : giocatori) {
			x.comunicaFaseFinale();
		}

	}

	/**
	 * comunico ai giocatori che il turno è cambiato
	 * 
	 * @author Valerio De Maria
	 */
	private void comunicaCambioTurno() {
		for (InterfacciaComunicazioneClient x : giocatori) {
			x.comunicaCambioTurno();
		}

	}
}
