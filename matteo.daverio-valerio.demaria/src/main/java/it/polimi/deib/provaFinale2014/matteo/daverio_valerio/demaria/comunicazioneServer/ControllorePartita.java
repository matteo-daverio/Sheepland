package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Mosse;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.GestorePartite.MyTask;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.MuoviPastore;

import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * gestisce l'evolversi della partita in caso di più di due giocatori
 * 
 * @author Valerio De Maria
 * 
 */
public abstract class ControllorePartita implements Runnable {

	private ArrayList<Gestione> connessioni;
	private Partita partita;
	private boolean finePartita, faseFinale;
	private ArrayList<InterfacciaComunicazioneClient> giocatori = new ArrayList<InterfacciaComunicazioneClient>();
	private ArrayList<Mosse> mosseDisponibili = new ArrayList<Mosse>();
	private ArrayList<Mosse> mosseFatte = new ArrayList<Mosse>();

	// costruttore
	public ControllorePartita(ArrayList<Gestione> connessioni,
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
	
	/**
	 * creo la lista di mosse disponibili da inviare al client
	 * @param mosseFatte
	 * @return mosseDisponibili
	 * @author Valerio De Maria
	 */

	private ArrayList<Mosse> calcolaMosseDisponibili(ArrayList<Mosse> mosseFatte) {
		ArrayList<Mosse> mosseDisponibili =new ArrayList<Mosse>();
		if((mosseFatte.size()==Costanti.NUMERO_MOSSE_GIOCATORE-1)&&(mosseFatte.get(0)!=Mosse.MUOVI_PASTORE)&&(mosseFatte.get(1)!=Mosse.MUOVI_PASTORE)){
			 mosseDisponibili.add(Mosse.MUOVI_PASTORE);
			 return mosseDisponibili;
			}
		else{
			if(mosseFatte.size()>=0){
			switch(mosseFatte.get(mosseFatte.size())){
			
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
			}//fine switch
			}//fine if numero mosse fatte maggiore di zero
			else{
				mosseDisponibili.add(Mosse.ACCOPPIA);
				mosseDisponibili.add(Mosse.ABBATTI);
				mosseDisponibili.add(Mosse.COMPRA_TESSERA);
				mosseDisponibili.add(Mosse.MUOVI_PASTORE);
				mosseDisponibili.add(Mosse.MUOVI_PECORA);
			}
			return mosseDisponibili;
		}

	}
	
	private boolean clientConnesso(){
		return giocatori.get(partita.getTurno()-1).ping();
	}

	public Mossa riceviMossa(ArrayList<Mosse> mosseDisponibili) {

		return giocatori.get(partita.getTurno() - 1).riceviMossa(mosseDisponibili);
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

		
		mosseFatte.clear();
		// il giocatore compie le mosse che può fare nel turno
		for (int i = 0; i <= Costanti.NUMERO_MOSSE_GIOCATORE; i++) {
          if(clientConnesso()){
			mosseDisponibili = calcolaMosseDisponibili(mosseFatte);

			Mossa mossa = riceviMossa(mosseDisponibili);

			try {
				mossa.eseguiMossa(partita);
			} catch (Exception e) {
			}

			// faccio eseguire su tutti i client la mossa fatta
			mossa.aggiornaClients(giocatori,partita.getTurno());

			// aggiungo all'arrayList la mossa fatta
			mossa.aggiornaMosseFatte(mosseFatte);
          
         }
          else{
        	  i=Costanti.NUMERO_MOSSE_GIOCATORE;
          }

		}// fine ciclo for

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
	 * @throws RemoteException
	 */
	private void inviaPartita() {

		for (InterfacciaComunicazioneClient x : giocatori) {

			x.inviaPartita(partita);

		}
	}
	
	public boolean contieneClient(String nome){
	
		for(InterfacciaComunicazioneClient x:giocatori){
			if(x.getNome().equals(nome)){
				return true;
			}
		}
		return false;
		
	}
	
	public void aggiornaComunicazione(String nome, Socket socket){
		for(InterfacciaComunicazioneClient x:giocatori){
			if(x.getNome().equals(nome)){
			 if(x.getTipoConnessione().equals("socket")){
				 x.setSocket(socket);
			 }
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
				giocatori.add(new ComunicazioneSocket(x.getSocket(),x.getBufferIn(),x.getBufferOut(),x.getNome()));
			} else {

				giocatori
						.add(new ComunicazioneRMI(x.getNome(),x.getInterfacciaClient()));
			}

		}
	}

	private void posizionaPastori() {
		// TODO devo chiedere al client dove vuole mettere inizialmente il suo
		// pastore
	}

	private void comunicaFinePartita() {

	}

	/**
	 * ciclo principale della partita
	 * 
	 * @author Valerio De Maria
	 */
	public void run() {


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
