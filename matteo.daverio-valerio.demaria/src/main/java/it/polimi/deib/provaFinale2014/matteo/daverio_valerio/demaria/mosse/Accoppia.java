package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import java.util.ArrayList;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
/**
 * passo la regione dove si vuole far accoppiare le pecore
 *  
 * @author Valerio De Maria
 *
 */
public class Accoppia implements Mossa{

	private int regione;
	
	//costruttore
	public Accoppia(int regione){
		
		this.regione=regione;
	}
	
	public void eseguiMossa(Partita partita) throws IllegalShireException, CannotProcreateException{
		
		partita.accoppia(regione);
		
	}

	public void aggiornaClients(
			ArrayList<InterfacciaComunicazioneClient> giocatori) {
		for (InterfacciaComunicazioneClient x : giocatori) {

			x.comunicaAbbattimento(regione);
		}
		
	}

}
