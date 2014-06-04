package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * decide quando crare una nuova partita
 * 
 * @author Valerio De Maria
 * 
 */
public class GestorePartite implements Runnable {

	private static ArrayList<Gestione> connessioni = new ArrayList<Gestione>();

	ExecutorService executor = Executors.newCachedThreadPool(); // pool di
																// thread

	/**
	 * aggiungo una nuova connessione a quelle in attesa di iniziare
	 * 
	 * @param g
	 * @author Valerio De Maria
	 */
	public static void addConnessione(Gestione g) {
		connessioni.add(g);
	}

	public void run() {

		// creo una nuova partita
		Partita partita = new Partita();
		// aggiungo una nuova partita in corso al pool di thread
		executor.submit(new ControllorePartitaMoltiGiocatori(connessioni, partita));
		executor.submit(new ControllorePartitaDueGiocatori(connessioni, partita));

	}

}
