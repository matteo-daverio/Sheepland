package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.util.ArrayList;

public class ControllorePartitaDueGiocatori implements Runnable {

	private ArrayList<Gestione> connessioni;
	private Partita partita;
	private boolean finePartita,faseFinale;

	//costruttore
	public ControllorePartitaDueGiocatori(ArrayList<Gestione> connessioni,
			Partita partita) {
		
		this.connessioni=connessioni;
		this.partita=partita;

	}

	private void giocaTurno() {

		partita.muoviPecoraNera();
		for (int i = 0; i <= Costanti.NUMERO_MOSSE_GIOCATORE; i++) {
			// TODO comunico al client le mosse che può fare, le faccio eseguire
			// e controllo che muova almeno una volta il pastore e che non
			// esegua la stessa mossa due volta di fila
		}
		
		
	}

	private void conteggioPunti(){
		
	}
	
	public void run() {

		partita.start();

		// TODO comunico che la partita è inziata

		finePartita=false;
		faseFinale=false;
		while (!finePartita) {

			giocaTurno();
			partita.incrementaTurno();
			if (partita.getTurno() > connessioni.size()) {
				partita.muoviLupo();
				partita.setTurno(1);	
			}
			if (partita.getContatoreRecinti()==Costanti.NUMERO_RECINTI_NORMALI){
				// TODO comunica ai client che sono in fase finale
				faseFinale=true;
			}
			if (faseFinale && partita.getTurno()==connessioni.size()){
				finePartita=true;
			}

		}//fine del while

		conteggioPunti();
		// TODO comunica risulatati

		
	}
}
