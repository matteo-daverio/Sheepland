package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneToClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
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
	private int pastore;
	private String giocatore;

	// costruttore
	public Accoppia(int regione) {

		this.regione = regione;
	}

	public void eseguiMossa(Partita partita,String giocatore, int pastore) throws GameException {

		partita.accoppia(regione);
		this.giocatore=giocatore;
		this.pastore=pastore;

	}

	public void aggiornaClients(
			List<InterfacciaComunicazioneToClient> giocatori,int turno) {
		for (int i=0;i<=giocatori.size();i++) {
	           if(i!=(turno-1)){
				giocatori.get(turno-1).comunicaAccoppiamento(regione,giocatore,pastore);
	           }
			}		

	}

	public List<MosseEnum> aggiornaMosseFatte(List<MosseEnum> mosseFatte) {
		mosseFatte.add(MosseEnum.ACCOPPIA);
		return mosseFatte;

	}

}
