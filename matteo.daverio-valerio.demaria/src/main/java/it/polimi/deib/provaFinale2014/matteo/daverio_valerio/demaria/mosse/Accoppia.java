package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Mosse;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;

import java.util.ArrayList;

/**
 * passo la regione dove si vuole far accoppiare le pecore
 * 
 * @author Valerio De Maria
 * 
 */
public class Accoppia implements Mossa {

	private int regione;

	// costruttore
	public Accoppia(int regione) {

		this.regione = regione;
	}

	public void eseguiMossa(Partita partita) throws IllegalShireException,
			CannotProcreateException {

		partita.accoppia(regione);

	}

	public void aggiornaClients(
			ArrayList<InterfacciaComunicazioneClient> giocatori,int turno) {
		for (int i=0;i<=giocatori.size();i++) {
	           if(i!=(turno-1)){
				giocatori.get(turno-1).comunicaAccoppiamento(regione);
	           }
			}		

	}

	public void aggiornaMosseFatte(ArrayList<Mosse> mosseFatte) {
		mosseFatte.add(Mosse.ACCOPPIA);

	}

}
