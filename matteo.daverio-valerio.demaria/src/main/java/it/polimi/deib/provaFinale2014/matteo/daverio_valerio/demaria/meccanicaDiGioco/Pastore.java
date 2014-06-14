package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Matteo Daverio
 * 
 */
public class Pastore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 580595892472677932L;
	private int posizione;
	private int denaro;
	private List<Tessera> tessere = new ArrayList<Tessera>();
	private String nomeGiocatore;
	private int turnoDiGioco;

	/**
	 * costruttore
	 * 
	 * @param nomeGiocatore
	 * @param turnoDiGioco
	 * @author Matteo Daverio
	 */
	public Pastore(String nomeGiocatore, int turnoDiGioco) {
		this.nomeGiocatore = nomeGiocatore;
		this.turnoDiGioco = turnoDiGioco;
		posizione = (int) (Math.random() * Costanti.NUMERO_STRADE);
		denaro = 20;
	}

	// getter e setter

	/**
	 * 
	 * @return posizione del pastore
	 */
	public int getPosizione() {
		return posizione;
	}

	/**
	 * 
	 * @return turno di gioco del pastore
	 */
	public int getTurnoDiGioco() {
		return turnoDiGioco;
	}

	/**
	 * setta la posizione del pastore
	 * 
	 * @param posizione
	 */
	public void setPosizione(int posizione) {
		this.posizione = posizione;
	}

	/**
	 * 
	 * @return denaro posseduto dal pastore
	 */
	public int getDenaro() {
		return denaro;
	}

	/**
	 * setta il denaro posseduto
	 * 
	 * @param denaro
	 */
	public void setDenaro(int denaro) {
		this.denaro = denaro;
	}

	/**
	 * 
	 * @return tessere possedute dal pastore
	 */
	public List<Tessera> getTessere() {
		return tessere;
	}

	/**
	 * aggiunge tessere al pastore
	 * 
	 * @param tessera
	 */
	public void aggiungiTessera(Tessera tessera) {
		tessere.add(tessera);
	}

	/**
	 * 
	 * @return nome del giocatore
	 */
	public String getNomeGiocatore() {
		return nomeGiocatore;
	}

}
