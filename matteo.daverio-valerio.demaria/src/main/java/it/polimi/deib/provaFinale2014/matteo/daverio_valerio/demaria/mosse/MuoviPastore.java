package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneToClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * passo la posizione dove voglio muovere il pastore
 * 
 * @author Valerio De Maria
 * 
 */
public class MuoviPastore implements Mossa, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5408031958687129369L;
	private int posizione;
	private int pastore;
	private String giocatore;

	// costruttore
	public MuoviPastore(int posizione) {

		this.posizione = posizione;

	}

	public void eseguiMossa(Partita partita, String giocatore, int pastore)
			throws GameException {

		System.out.println("muovo il pastore");
		partita.muoviPastore(posizione, pastore);
		this.giocatore = giocatore;
		this.pastore = pastore;

	}

	public void aggiornaClients(List<InterfacciaComunicazioneToClient> giocatori,
			int turno) {
		System.out.println("aggiorno i clients");
		System.out.println("size giocatori: "+giocatori.size());
		for (int i = 0; i <= giocatori.size()-1; i++) {
			if (i != (turno - 1)) {
				System.out.println("invio aggiornamento");
				giocatori.get(turno - 1).comunicaMovimentoPastore(posizione,
						giocatore, pastore);
			}
		}

	}

	public List<MosseEnum> aggiornaMosseFatte(List<MosseEnum> mosseFatte) {
		List<MosseEnum> m = new ArrayList<MosseEnum>();
		for (MosseEnum x : mosseFatte) {
			m.add(x);
		}
		System.out.println("aggiungo alle mosse fatte il movimento pastore");
		m.add(MosseEnum.MUOVI_PASTORE);
		return m;

	}

}
