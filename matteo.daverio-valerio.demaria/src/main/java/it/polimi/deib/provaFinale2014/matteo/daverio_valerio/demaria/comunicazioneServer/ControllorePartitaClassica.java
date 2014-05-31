package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.util.ArrayList;


import mosse.Mossa;
import mosse.MuoviPastore;
/**
 *gestisce l'evolversi della partita in caso di più di due giocatori 
 *
 * @author Valerio De Maria
 *
 */
public class ControllorePartitaClassica implements Runnable {

	private ArrayList<Gestione> connessioni;
	private Partita partita;
	private boolean finePartita, faseFinale, pastoreMosso;
	private ArrayList<InterfacciaComunicazioneClient> giocatori = new ArrayList<InterfacciaComunicazioneClient>();

	// costruttore
	public ControllorePartitaClassica(ArrayList<Gestione> connessioni,
			Partita partita) {

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

	private void comunicaMosseDisponibili() {
		// TODO
	}

	private Mossa riceviMossa() {

		return new MuoviPastore(3);
	}

	/**
	 * svolgo le azioni da compiere per ogni turno
	 * 
	 * @author Valerio De Maria
	 */
	private void giocaTurno() {

		// la pecora nera muove all'inizio di ogni nuovo turno
		partita.muoviPecoraNera();

		// dico al client che la pecora nera si è mossa
		comunicaMovimentoPecoraNera(partita.getPecoraNera().getPosizione());

		pastoreMosso = false;
		// il giocatore compie le mosse che può fare nel turno
		for (int i = 0; i <= Costanti.NUMERO_MOSSE_GIOCATORE; i++) {

			comunicaMosseDisponibili();

			Mossa mossa = riceviMossa();

			try {
				mossa.eseguiMossa(partita);
			} catch (Exception e) {
			}

			// TODO controllo che muova almeno una volta il pastore e che non
			// esegua la stessa mossa due volta di fila
		}

	}

	private void conteggioPunti() {

	}

	/**
	 * creo un pastore per ogni giocatore la sequenza di turni dei giocatori è
	 * data da chi si è connesso per primo
	 * 
	 * @author Valerio De Maria
	 */
	private void aggiungiPastori() {

		for (int i = 0; i <= connessioni.size(); i++) {

			partita.aggiungiPastore(connessioni.get(i).getNome(), i + 1);

		}

	}

	/**
	 * invio ad ogni client l'istanza della partita in corso
	 * 
	 * @author Valerio De Maria
	 */
	private void inviaPartita() {

		for (InterfacciaComunicazioneClient x : giocatori) {

			x.inviaPartita(partita);

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
				giocatori.add(new ComunicazioneSocket(x.getSocket()));
			} else {

				giocatori
						.add(new ComunicazioneRMI(x.getNome(), x.getPassword()));
			}

		}
	}

	private void posizionaPastori() {
		// TODO devo chiedere al client dove vuole mettere inizialmente il suo
		// pastore
	}
	
	private void comunicaFinePartita(){
		
	}

	/**
	 * ciclo principale della partita
	 * 
	 * @author Valerio De Maria
	 */
	public void run() {

		partita.start();

		aggiungiPastori();

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
