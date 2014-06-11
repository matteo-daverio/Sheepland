package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.LOGGER;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

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

	// lista client in attesa di giocare una nuova partita
	private static List<Gestione> connessioni = new ArrayList<Gestione>();
	// lista dei giocatori che hanno una partita in corso
	private static List<Gestione> giocatori = new ArrayList<Gestione>();
	// lista delle partite in corso
	private static List<ControllorePartita> partiteInCorso = new ArrayList<ControllorePartita>();

	// pool di thread
	ExecutorService executor = Executors.newCachedThreadPool();

	/**
	 * guardo se il client che ha fatto il login ha una partita in corso
	 * 
	 * @param g
	 * @author Valerio De Maria
	 */
	private synchronized static boolean giocatoreInGioco(Gestione g) {
		for (Gestione x : giocatori) {
			if ((x.getNome().equals(g.getNome()))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * se il client ha una partita in corso controllo che la password sia giusta
	 * 
	 * @param g
	 * @author Valerio De Maria
	 */
	private synchronized static boolean passwordSbagliata(Gestione g) {
		for (Gestione x : giocatori) {
			if ((x.getNome().equals(g.getNome()))) {
				if (x.getPassword().equals(g.getPassword())) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * aggiorno la classe di comunicazioneClient all'interno del controllore
	 * partita
	 * 
	 * @param nome
	 * @param socket
	 * @author Valerio De Maria
	 */
	private synchronized static void reintegraClient(String nome, Socket socket) {
		for (ControllorePartita x : partiteInCorso) {
			if (x.contieneClient(nome)) {
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
			System.out.println("aggiungo " + g.getNome());
			connessioni.add(g);
			giocatori.add(g);
			return true;
		} else {
			System.out.println(g.getNome() + " sta giocando");
			if (passwordSbagliata(g)) {
				return false;
			} else {
				System.out.println("Reintegro " + g.getNome());
				reintegraClient(g.getNome(), g.getSocket());
				return true;
			}

		}

	}

	/**
	 * innerClass che genera le partite
	 * 
	 * @author Valerio De Maria
	 * 
	 */
	class CreatorePartite extends TimerTask {

		@Override
		public void run() {
			// TODO codice talebano
			System.out.println("controllo se ci sono partite da far partitre");
			System.out.println("ho "+connessioni.size()+" giocatori in attesa di iniziare");
			
			if (connessioni.size() > 1) {
				Partita p = new Partita();
				ControllorePartita controllore = new ControllorePartita(
					connessioni, p);
				partiteInCorso.add(controllore);
				executor.submit(controllore);
				connessioni.clear();
			} else
				return;

		}

	}

	/**
	 * metodo run di Runnable
	 * 
	 * @author Valerio De Maria
	 */
	public void run() {

		// inizializzo il timer che fa partire partite anche con 2 giocatori
		Timer timer = new Timer();
		CreatorePartite task = new CreatorePartite();
		timer.schedule(task, 0, Costanti.PERIODO_AVVIO_PARTITE);

		// se i giocatori in attesa raggiungono il numero massimo di giocatori
		// in una partita faccio partire una nuova partita
		while (true) {
			if (connessioni.size() == Costanti.NUMERO_MASSIMO_GIOCATORI) {
				// TODO codice talebano
				System.out.println("ho raggiunto 4 giocatori!");
				Partita partita = new Partita();
				ControllorePartita controllore = new ControllorePartita(
						connessioni, partita);
				partiteInCorso.add(controllore);
				executor.submit(controllore);
				connessioni.clear();
			}
		}
	}

}
