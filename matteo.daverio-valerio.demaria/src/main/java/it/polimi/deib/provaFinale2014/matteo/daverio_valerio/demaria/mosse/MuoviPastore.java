package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Mosse;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;

import java.util.List;

/**
 * passo la posizione dove voglio muovere il pastore
 * 
 * @author Valerio De Maria
 * 
 */
public class MuoviPastore implements Mossa {

	private int posizione;

	// costruttore
	public MuoviPastore(int posizione) {

		this.posizione = posizione;

	}

	public void eseguiMossa(Partita partita) throws NoMovementException,
			NoMoneyException, InvalidMovementException {

		partita.muoviPastore(posizione);

	}

	public void aggiornaClients(
			List<InterfacciaComunicazioneClient> giocatori,int turno) {

		for (int i=0;i<=giocatori.size();i++) {
	           if(i!=(turno-1)){
				giocatori.get(turno-1).comunicaMovimentoPastore(posizione);
	           }
			}		

	}

	public void aggiornaMosseFatte(List<Mosse> mosseFatte) {
		mosseFatte.add(Mosse.MUOVI_PASTORE);

	}

}
