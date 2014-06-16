package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneToClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;

import java.io.Serializable;
import java.util.List;

/**
 * passo la regione dove si vuole abbattere una pecora
 * 
 * @author Valeri De Maria
 * 
 */
public class Abbatti implements Mossa, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8429587033170371570L;
	private int regione, pecora, pastore;
	private String giocatore;

	/**
	 * COSTRUTTORE
	 * 
	 * @param regione
	 * @param pecora
	 * @author Valerio De Maria
	 */
	public Abbatti(int regione, int pecora) {

		this.regione = regione;
		this.pecora = pecora;

	}

	/**
	 * eseguo la mossa su partita
	 * 
	 * @author Valerio De Maria
	 */
	public void eseguiMossa(Partita partita, String giocatore, int pastore)
			throws GameException {

		partita.abbatti(regione, pecora);
		this.giocatore = giocatore;
		this.pastore = pastore;

	}

	/**
	 * comunico lo svolgimento della mossa ai vari giocatori
	 * 
	 * @author Valerio De Maria
	 */
	public void aggiornaClients(
			List<InterfacciaComunicazioneToClient> giocatori, int turno,List<Boolean>giocatoriConnessi) {
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if ((i != (turno - 1))) {
				if(giocatoriConnessi.get(i).booleanValue()==true){
				giocatori.get(i).comunicaAbbattimento(regione, pecora,
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
		mosseFatte.add(MosseEnum.ABBATTI);
		return mosseFatte;

	}

}
