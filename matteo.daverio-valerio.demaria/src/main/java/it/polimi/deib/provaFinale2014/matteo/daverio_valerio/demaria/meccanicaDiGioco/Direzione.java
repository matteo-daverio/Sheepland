package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

/**
 * 
 * @author Matteo Daverio
 * 
 */
public class Direzione {

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
	 * @return direzione verso cui il pastore dovrebbe dirigersi per raggiungere questa strada
	 * @author Matteo Daverio
	 */
	public String getVerso() {
		return verso;
	}

}
