package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Mosse;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
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
	private List<Mosse> mosseDisponibili = new ArrayList<Mosse>();
	private List<Mosse> mosseFatte = new ArrayList<Mosse>();

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

	private List<Mosse> calcolaMosseDisponibili(List<Mosse> mosseFatte) {
		List<Mosse> mosseDisponibili = new ArrayList<Mosse>();
		// se è l'ultima mossa del turno e non ho ancora mosso il pastore
		if ((mosseFatte.size() == Costanti.NUMERO_MOSSE_GIOCATORE - 1)
				&& (mosseFatte.get(0) != Mosse.MUOVI_PASTORE)
				&& (mosseFatte.get(1) != Mosse.MUOVI_PASTORE)) {
			mosseDisponibili.add(Mosse.MUOVI_PASTORE);
			return mosseDisponibili;
		} else {
			// se ho fatto almeno una mossa
			if (mosseFatte.size() >= 0) {
				// in base all'ultima mossa fatta
				switch (mosseFatte.get(mosseFatte.size())) {

				case ABBATTI:
					mosseDisponibili.add(Mosse.ACCOPPIA);
					mosseDisponibili.add(Mosse.COMPRA_TESSERA);
					mosseDisponibili.add(Mosse.MUOVI_PASTORE);
					mosseDisponibili.add(Mosse.MUOVI_PECORA);
					break;
				case ACCOPPIA:
					mosseDisponibili.add(Mosse.ABBATTI);
					mosseDisponibili.add(Mosse.COMPRA_TESSERA);
					mosseDisponibili.add(Mosse.MUOVI_PASTORE);
					mosseDisponibili.add(Mosse.MUOVI_PECORA);
					break;
				case COMPRA_TESSERA:
					mosseDisponibili.add(Mosse.ACCOPPIA);
					mosseDisponibili.add(Mosse.ABBATTI);
					mosseDisponibili.add(Mosse.MUOVI_PASTORE);
					mosseDisponibili.add(Mosse.MUOVI_PECORA);
					break;
				case MUOVI_PASTORE:
					mosseDisponibili.add(Mosse.ACCOPPIA);
					mosseDisponibili.add(Mosse.ABBATTI);
					mosseDisponibili.add(Mosse.MUOVI_PECORA);
					mosseDisponibili.add(Mosse.COMPRA_TESSERA);
					break;
				case MUOVI_PECORA:
					mosseDisponibili.add(Mosse.ACCOPPIA);
					mosseDisponibili.add(Mosse.ABBATTI);
					mosseDisponibili.add(Mosse.COMPRA_TESSERA);
					mosseDisponibili.add(Mosse.MUOVI_PASTORE);
					break;
				default:
					break;
				}// fine switch
			}// fine if numero mosse fatte maggiore di zero
			else {
				// la mia prima mossa può essere una qualsiasi
				mosseDisponibili.add(Mosse.ACCOPPIA);
				mosseDisponibili.add(Mosse.ABBATTI);
				mosseDisponibili.add(Mosse.COMPRA_TESSERA);
				mosseDisponibili.add(Mosse.MUOVI_PASTORE);
				mosseDisponibili.add(Mosse.MUOVI_PECORA);
			}
			return mosseDisponibili;
		}

	}

	// TODO
	private boolean clientConnesso() {
		return giocatori.get(partita.getTurno() - 1).ping();
	}

	/**
	 * metodo che riceve la mossa inviata dal client
	 * 
	 * @param mosseDisponibili
	 * @return
	 * @author Valerio De Maria
	 */
	public Mossa riceviMossa(List<Mosse> mosseDisponibili) {

		return giocatori.get(partita.getTurno() - 1).riceviMossa(
				mosseDisponibili);
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

			mosseDisponibili.clear();
			mosseDisponibili = calcolaMosseDisponibili(mosseFatte);

			Mossa mossa = riceviMossa(mosseDisponibili);

			try {
				mossa.eseguiMossa(partita);

				// faccio eseguire su tutti i client la mossa fatta
				mossa.aggiornaClients(giocatori, partita.getTurno());

				mossa.aggiornaMosseFatte(mosseFatte);
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

	private void posizionaPastori() {
		// TODO devo chiedere al client dove vuole mettere inizialmente il suo
		// pastore
	}

	private void comunicaFinePartita() {
		// TODO
	}

	/**
	 * ciclo principale della partita
	 * 
	 * @author Valerio De Maria
	 */
	public void run() {

		aggiungiPastori(connessioni, partita);

		// creo le classi di comunicazione che si occuperrano di comunicare con
		// i client in maniera trasparente rispetto alla modalità di connessione
		trasferisciGestioneComunicazione();

		inviaPartita();

		posizionaPastori();

		finePartita = false;
		faseFinale = false;
		while (!finePartita) {

			giocaTurno();
			partita.incrementaTurno();
			if (partita.getTurno() > connessioni.size()) {
				partita.muoviLupo();
				partita.setTurno(1);
			}
			if (partita.getContatoreRecinti() == Costanti.NUMERO_RECINTI_NORMALI) {
				// TODO comunica ai client che sono in fase finale
				faseFinale = true;
			}
			if (faseFinale && partita.getTurno() == connessioni.size()) {
				finePartita = true;
			}

		}// fine del while

		conteggioPunti();

		comunicaFinePartita();

	}
}
