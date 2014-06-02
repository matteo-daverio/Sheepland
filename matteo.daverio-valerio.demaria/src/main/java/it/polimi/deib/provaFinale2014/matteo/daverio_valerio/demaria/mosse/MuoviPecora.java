package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import java.util.ArrayList;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
/**
 * passo la pecora che voglio muovere e la strada su cui si trova il pastore
 * 
 * @author Valerio De Maria
 *
 */
public class MuoviPecora implements Mossa {
	
	private Strada strada;
	private int pecora;
	
	//costruttore
	public MuoviPecora(Strada strada, int pecora){
		
		this.strada=strada;
	    this.pecora=pecora;
	    
	}
	
	public void eseguiMossa(Partita partita) throws IllegalStreetException, IllegalShireException {
		
		partita.muoviPecora(pecora, strada);;
	}

	public void aggiornaClients(
			ArrayList<InterfacciaComunicazioneClient> giocatori) {
		for (InterfacciaComunicazioneClient x : giocatori) {

			x.comunicaMovimentoPecora(pecora, strada);
		}
		
	}

}
