package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneToClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;

import java.io.Serializable;
import java.util.List;

/**
 * passo la regione dove si vuole far accoppiare le pecore
 * 
 * @author Valerio De Maria
 * 
 */
public class Accoppia implements Mossa, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2429637265309606641L;
	private int regione;
	private int pastore;
	private String giocatore;

	/**
	 * COSTRUTTORE
	 * 
	 * @param regione
	 * @author Valerio De Maria
	 */
	public Accoppia(int regione) {

		this.regione = regione;
	}

	/**
	 * eseguo la mossa all'interno di partita
	 * 
	 * @author Valerio De Maria
	 */
	public void eseguiMossa(Partita partita, String giocatore, int pastore)
			throws GameException {

		partita.accoppia(regione);
		this.giocatore = giocatore;
		this.pastore = pastore;

	}

	/**
	 * comunico ai giocatori la mossa fatta
	 * 
	 * @author Valerio De Maria
	 */
	public void aggiornaClients(
			List<InterfacciaComunicazioneToClient> giocatori, int turno,List<Boolean>giocatoriConnessi) {
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			if ((i != (turno - 1))) {
				if(giocatoriConnessi.get(i).booleanValue()==true){
				giocatori.get(i).comunicaAccoppiamento(regione, giocatore,
						pastore);
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
		mosseFatte.add(MosseEnum.ACCOPPIA);
		return mosseFatte;

	}

}
