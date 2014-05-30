package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Mosse;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import mosse.Mossa;
import mosse.MuoviPastore;

public class ControllorePartitaClassica implements Runnable {

	private ArrayList<Gestione> connessioni;
	private Partita partita;
	private boolean finePartita, faseFinale;
	private ArrayList<InterfacciaComunicazioneClient> giocatori =new ArrayList<InterfacciaComunicazioneClient>();

	// costruttore
	public ControllorePartitaClassica(ArrayList<Gestione> connessioni,
			Partita partita) {

		this.connessioni = connessioni;
		this.partita = partita;

	}
	
	
	private void comunicaMovimentoPecoraNera(int nuovaPosizione){
		
		for (InterfacciaComunicazioneClient x : giocatori) {
			
			x.comunicaMovimentoPecoraNera(nuovaPosizione);

		}
	}
	
	private void comunicaMosseDisponibili(){
		//TODO
	}
	
	private Mossa riceviMossa(){
		
		return new MuoviPastore(3);
	}

	private void giocaTurno() {

		partita.muoviPecoraNera();
		
		//dico al client che la pecora nera si è mossa
		comunicaMovimentoPecoraNera(partita.getPecoraNera().getPosizione());
		
		for (int i = 0; i <= Costanti.NUMERO_MOSSE_GIOCATORE; i++) {
		
			comunicaMosseDisponibili();
			
			Mossa mossa =riceviMossa();
			
			try {
				mossa.eseguiMossa(partita);
			} catch (Exception e) {}

			
			
			
			
			
			// TODO comunico al client le mosse che può fare, le faccio eseguire
			// e controllo che muova almeno una volta il pastore e che non
			// esegua la stessa mossa due volta di fila
		}

	}

	private void conteggioPunti() {

	}

	private void aggiungiPastori() {

		for (int i = 0; i <= connessioni.size(); i++) {

			partita.aggiungiPastore(connessioni.get(i).getNome(), i + 1);

		}

	}

	private void inviaPartita() {

		for (InterfacciaComunicazioneClient x : giocatori) {
			
			x.inviaPartita(partita);

		}
	}
	
	private void trasferisciGestioneComunicazione(){
		
		for (Gestione x : connessioni) {
            if(x.getTipoConnessione().equals("socket")){
            	giocatori.add(new ComunicazioneSocket(x.getSocket()));
            }
            else{
            	
            	giocatori.add(new ComunicazioneRMI(x.getNome(),x.getPassword()));
            }
			
		}
	}

	public void run() {

		partita.start();

		aggiungiPastori();
		
		//creo le classi di comunicazione che si occuperrano di comunicare con i client in maniera trasparente rispetto alla modalità di connessione
		trasferisciGestioneComunicazione();
		
		
		
		inviaPartita();
		
		// TODO richiesta posizione iniziale dei pastori

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
