package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.TimerTask;
import java.util.Timer;

/**
 * decide quando crare una nuova partita
 * 
 * @author Valerio De Maria
 * 
 */
public class GestorePartite implements Runnable {

	private static ArrayList<Gestione> connessioni = new ArrayList<Gestione>();
	private static List<Gestione> giocatori = new ArrayList<Gestione>();
	private static List<ControllorePartita> partiteInCorso = new ArrayList<ControllorePartita>();

	ExecutorService executor = Executors.newCachedThreadPool(); // pool di
																// thread

	private static boolean giocatoreInGioco(Gestione g) {
		for (Gestione x : giocatori) {
			if ((x.getNome().equals(g.getNome()))) {
				return true;
			}
		}
		return false;
	}

	private static boolean passwordSbagliata(Gestione g) {
		for (Gestione x : giocatori) {
			if ((x.getNome().equals(g.getNome()))) {
				if (x.getPassword().equals(g.getPassword())) {
					return false;
				}
			}
		}
		return true;
	}

	private static void reintegraClient(String nome, Socket socket) {
		for (ControllorePartita x : partiteInCorso) {
			if(x.contieneClient(nome)){
				x.aggiornaComunicazione(nome, socket);
			}
		}
	}

	/**
	 * aggiungo una nuova connessione a quelle in attesa di iniziare se il
	 * giocatore non è tra quelli in gioco
	 * 
	 * @param g
	 * @author Valerio De Maria
	 */
	public synchronized static boolean addConnessione(Gestione g) {
		if (!giocatoreInGioco(g)) {
			connessioni.add(g);
			giocatori.add(g);
			return true;
		} else {
			if (passwordSbagliata(g)) {
				return false;
			} else {
				reintegraClient(g.getNome(),g.getSocket());
				return true;
			}

		}

	}
    class MyTask extends TimerTask{

		@Override
	     public void run() {
			if (connessioni.size() > 1) {
				Partita p = new Partita();
				if (connessioni.size() == 2) {
					ControllorePartitaDueGiocatori controllore = new ControllorePartitaDueGiocatori(
							connessioni, p);
					partiteInCorso.add(controllore);
					executor.submit(controllore);
				} else {
					ControllorePartitaMoltiGiocatori controllore = new ControllorePartitaMoltiGiocatori(
							connessioni, p);
					partiteInCorso.add(controllore);
					executor.submit(controllore);
				}
				connessioni.clear();
			} else
				return;

		}
			
		
    	
    }
	public void run() {
		
		// inizializzo il timer
		Timer timer = new Timer();
		MyTask task= new MyTask();
		timer.schedule(task, 0, 5*1000);

		// creo una nuova partita
		Partita partita = new Partita();
		// aggiungo una nuova partita in corso al pool di thread
		executor.submit(new ControllorePartitaMoltiGiocatori(connessioni,
				partita));
		executor.submit(new ControllorePartitaDueGiocatori(connessioni, partita));

	}

}
