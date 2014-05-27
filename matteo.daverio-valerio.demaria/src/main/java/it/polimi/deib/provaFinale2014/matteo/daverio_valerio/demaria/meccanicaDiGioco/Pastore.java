package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;

import java.util.ArrayList;
/**
 * 
 * @author Matteo Daverio
 *
 */
public class Pastore {

	private int posizione;
	private int denaro;
	private ArrayList<Tessera> tessere = new ArrayList<Tessera>();
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
	
	// ritorna la posizione del pastore
	public int getPosizione() {
		return posizione;
	}

	// ritorna il turno di gioco del pastore
	public int getTurnoDiGioco() {
		return turnoDiGioco;
	}
	
	// setta la posizione del pastore
	public void setPosizione(int posizione) {
		this.posizione = posizione;
	}

	// ritorna il denaro posseduto
	public int getDenaro() {
		return denaro;
	}

	// setta il denaro posseduto
	public void setDenaro(int denaro) {
		this.denaro = denaro;
	}

	// ritorna le tessere possedute dal pastore
	public ArrayList<Tessera> getTessere() {
		return tessere;
	}

	// aggiunge tessere al pastore
	public void aggiungiTessera(Tessera tessera) {
		tessere.add(tessera);
	}

	// ritorna il nome del giocatore
	public String getNomeGiocatore() {
		return nomeGiocatore;
	}

}
