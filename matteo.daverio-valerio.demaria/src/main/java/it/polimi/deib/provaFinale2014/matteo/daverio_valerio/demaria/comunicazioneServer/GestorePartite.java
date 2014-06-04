package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.util.ArrayList;
import java.util.List;
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
	private static List<Gestione> giocatoriInGioco = new ArrayList<Gestione>();

	ExecutorService executor = Executors.newCachedThreadPool(); // pool di
																// thread

	private static boolean giocatoreInGioco(Gestione g){
		for(Gestione x:giocatoriInGioco){
			if((x.getNome().equals(g.getNome()))&&(x.getPassword().equals(g.getPassword()))){
				return true;
			}
		}
		return false;
	}
	/**
	 * aggiungo una nuova connessione a quelle in attesa di iniziare
	 * se il giocatore non è tra quelli in gioco
	 * @param g
	 * @author Valerio De Maria
	 */
	public synchronized static boolean addConnessione(Gestione g) {
		if(!giocatoreInGioco(g)){
		 connessioni.add(g);
		 giocatoriInGioco.add(g);
		 return true;
		}
		else return false;
	}

	public void run() {

		// creo una nuova partita
		Partita partita = new Partita();
		// aggiungo una nuova partita in corso al pool di thread
		executor.submit(new ControllorePartitaMoltiGiocatori(connessioni, partita));
		executor.submit(new ControllorePartitaDueGiocatori(connessioni, partita));

	}

}
