package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;

import java.io.Serializable;
import java.util.List;

/**
 * passo la posizione dove voglio muovere il pastore
 * 
 * @author Valerio De Maria
 * 
 */
public class MuoviPastore implements Mossa,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5408031958687129369L;
	private int posizione;

	// costruttore
	public MuoviPastore(int posizione) {

		this.posizione = posizione;

	}

	public void eseguiMossa(Partita partita) throws NoMovementException,
			NoMoneyException, InvalidMovementException {

		System.out.println("muovo il pastore");
		partita.muoviPastore(posizione);

	}

	public void aggiornaClients(
			List<InterfacciaComunicazioneClient> giocatori,int turno) {
System.out.println("aggiorno i clients");
		for (int i=0;i<=giocatori.size();i++) {
	           if(i!=(turno-1)){
				giocatori.get(turno-1).comunicaMovimentoPastore(posizione);
	           }
			}		

	}

	public void aggiornaMosseFatte(List<MosseEnum> mosseFatte) {
		System.out.println("aggiungo alle mosse fatte il movimento pastore");
		mosseFatte.add(MosseEnum.MUOVI_PASTORE);
        
	}

}
