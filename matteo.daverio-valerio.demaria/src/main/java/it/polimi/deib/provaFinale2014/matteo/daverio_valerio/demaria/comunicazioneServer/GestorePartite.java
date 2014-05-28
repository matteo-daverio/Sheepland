package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.util.ArrayList;

/**
 * decide quando crare una nuova partita
 * 
 * @author Valerio De Maria
 *
 */
public class GestorePartite implements Runnable {
	
	
	private static ArrayList<Gestione> connessioni = new ArrayList<Gestione>();
	

	public static void addConnessione(Gestione g){
		connessioni.add(g);
	}
	
	
	public void run(){
		
		
	}
	

}
