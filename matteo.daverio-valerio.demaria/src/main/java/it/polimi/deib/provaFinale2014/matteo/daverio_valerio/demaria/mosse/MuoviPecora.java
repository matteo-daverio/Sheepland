package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneToClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;

import java.io.Serializable;
import java.util.List;

/**
 * passo la pecora che voglio muovere e la strada su cui si trova il pastore
 * 
 * @author Valerio De Maria
 * 
 */
public class MuoviPecora implements Mossa,Serializable {

	private int strada;
	private int pecora;
	private int pastore;
	private String giocatore;

	// costruttore
	public MuoviPecora(int strada, int pecora) {

		this.strada = strada;
		this.pecora = pecora;

	}

	public void eseguiMossa(Partita partita,String giocatore,int pastore) throws GameException {

		partita.muoviPecora(pecora, strada,pastore);
		this.giocatore=giocatore;
		this.pastore=pastore;
		;
	}

	public void aggiornaClients(
			List<InterfacciaComunicazioneToClient> giocatori,int turno) {
		for (int i=0;i<=giocatori.size();i++) {
	           if(i!=(turno-1)){
				giocatori.get(turno-1).comunicaMovimentoPecora(pecora, strada,giocatore,pastore);
	           }
			}		

	}

	public List<MosseEnum> aggiornaMosseFatte(List<MosseEnum> mosseFatte) {
		mosseFatte.add(MosseEnum.MUOVI_PECORA);
		return mosseFatte;

	}

}
