package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneToClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;

import java.io.Serializable;
import java.util.List;

public class MuoviPecoraNera implements Mossa,Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6395928492801885070L;
	private int strada;
	private int pastore;
	private String giocatore;

	/**
	 * COSTRUTTORE
	 * 
	 * @param strada
	 * @author Valerio De Maria
	 */
	public MuoviPecoraNera(int strada) {

		this.strada = strada;

	}

	/**
	 * eseguo la mossa su partita
	 * 
	 * @author Valerio De Maria
	 */
	public void eseguiMossa(Partita partita, String giocatore, int pastore)
			throws GameException {

		partita.spostaPecoraNera(strada, pastore);
		this.giocatore = giocatore;
		this.pastore = pastore;
		;
	}

	/**
	 * comunico ai giocatori lo svolgimento della mossa
	 * 
	 * @author Valerio De Maria
	 */
	public void aggiornaClients(
			List<InterfacciaComunicazioneToClient> giocatori, int turno,List<Boolean>giocatoriConnessi) {
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if ((i != (turno - 1))) {
				if(giocatoriConnessi.get(i).booleanValue()==true){
				giocatori.get(i).comunicaSpostamentoPecoraNera(strada,
						giocatore, pastore);
				}
			} else {
				giocatori.get(i).mossaCorretta();
			}
		}

	}

	/**
	 * @author Valerio De Maria
	 */
	public List<MosseEnum> aggiornaMosseFatte(List<MosseEnum> mosseFatte) {
		mosseFatte.add(MosseEnum.MUOVI_PECORA_NERA);
		return mosseFatte;

	}
	
}
