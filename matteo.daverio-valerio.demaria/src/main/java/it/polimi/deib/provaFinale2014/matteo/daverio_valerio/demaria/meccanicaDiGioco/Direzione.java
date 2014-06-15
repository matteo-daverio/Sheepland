package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import java.io.Serializable;


/**
 * 
 * @author Matteo Daverio
 * 
 */
public class Direzione implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7586290979331087384L;
	private final int posizione;
	private final String verso;

	// costruttore
	public Direzione(int posizione, String verso) {
		this.posizione = posizione;
		this.verso = verso;
	}

	// getter

	/**
	 * 
	 * @return posizione strada adiacente
	 * @author Matteo Daverio
	 */
	public int getPosizione() {
		return posizione;
	}

	/**
	 * 
	 * @return direzione verso cui il pastore dovrebbe dirigersi per raggiungere
	 *         questa strada
	 * @author Matteo Daverio
	 */
	public String getVerso() {
		return verso;
	}

}
