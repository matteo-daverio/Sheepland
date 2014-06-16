package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneToClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;

import java.io.Serializable;
import java.util.List;

/**
 * passo la pecora che voglio muovere e la strada su cui si trova il pastore
 * 
 * @author Valerio De Maria
 * 
 */
public class MuoviPecora implements Mossa, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4743896350395248508L;
	private int strada;
	private int pecora;
	private int pastore;
	private String giocatore;

	/**
	 * COSTRUTTORE
	 * 
	 * @param strada
	 * @param pecora
	 * @author Valerio De Maria
	 */
	public MuoviPecora(int strada, int pecora) {

		this.strada = strada;
		this.pecora = pecora;

	}

	/**
	 * eseguo la mossa su partita
	 * 
	 * @author Valerio De Maria
	 */
	public void eseguiMossa(Partita partita, String giocatore, int pastore)
			throws GameException {

		partita.muoviPecora(pecora, strada, pastore);
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
			List<InterfacciaComunicazioneToClient> giocatori, int turno,List<Boolean> giocatoriConnessi) {
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if ((i != (turno - 1))) {
				if(giocatoriConnessi.get(i).booleanValue()==true){
				giocatori.get(i).comunicaMovimentoPecora(pecora, strada,
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
		mosseFatte.add(MosseEnum.MUOVI_PECORA);
		return mosseFatte;

	}

}
