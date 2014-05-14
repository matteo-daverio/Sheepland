package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import java.util.ArrayList;

public class Partita {

	private int numeroGiocatori;

	ArrayList<Server> giocatori = new ArrayList<Server>();

	// costruttori

	public Partita() // costruttore vuoto
	{

	}

	// getter e setter

	public void setNumeroGiocatori(int numeroGiocatori) {
		this.numeroGiocatori = numeroGiocatori;
	}

	public void addGiocatore(Server s) // aggiunge i client a giocatori
	{
		giocatori.add(s);
	}

	public void start() // avvia la partita
	{
		while (!(numeroGiocatori == giocatori.size())) {
			// non tutti i giocatori si sono aggiunti alla partita
		}
		
		//la connessione con tutti i giocatori è andata a buon fine

	}

}
