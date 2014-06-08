package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;

import java.io.Serializable;
import java.util.List;

/**
 * passo la regione dove si vuole far accoppiare le pecore
 * 
 * @author Valerio De Maria
 * 
 */
public class Accoppia implements Mossa,Serializable {

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
			List<InterfacciaComunicazioneClient> giocatori,int turno) {
		for (int i=0;i<=giocatori.size();i++) {
	           if(i!=(turno-1)){
				giocatori.get(turno-1).comunicaAccoppiamento(regione);
	           }
			}		

	}

	public void aggiornaMosseFatte(List<MosseEnum> mosseFatte) {
		mosseFatte.add(MosseEnum.ACCOPPIA);

	}

}
