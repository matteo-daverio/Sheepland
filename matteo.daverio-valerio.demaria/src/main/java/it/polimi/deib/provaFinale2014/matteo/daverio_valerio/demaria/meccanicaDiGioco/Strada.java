package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Matteo Daverio
 * 
 */
public class Strada implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -497606577208406140L;
	private final int posizione;
	// numero da fare con il dado per passare
	private final int numeroDado; 
	private final int regioneDestra;
	private final int regioneSinistra;
	private boolean recinto;
	private List<Direzione> strade;

	/**
	 * costruttore strada
	 * da modificare quando creeremo il metodo inizializzazione
	 * 
	 * @param posizione
	 * @param regioneDestra
	 * @param regioneSinistra
	 * @param numeroDado
	 * @author Matteo Daverio
	 */
	public Strada(int posizione, int regioneDestra, int regioneSinistra,
			int numeroDado) 
	
	{
		recinto = false;
		this.posizione = posizione;
		this.regioneDestra = regioneDestra;
		this.regioneSinistra = regioneSinistra;
		this.numeroDado = numeroDado;
		this.strade = new ArrayList<Direzione>();
	}

	// getter e setter

	/**
	 * 
	 * @return se la strada è recintata
	 * @author Matteo Daverio
	 */
	public boolean recintata() {
		return recinto;
	}

	/**
	 * recinta la strada
	 * 
	 * @author Matteo Daverio
	 */
	public void aggiungiRecinto() {
		recinto = true;
	}

	/**
	 * 
	 * @return lista di strade adiacenti
	 * @author Matteo Daverio
	 */
	public List<Direzione> getStrade() {
		return strade;
	}

	/**
	 * aggiungi strade adiacenti
	 * 
	 * @param posizioneStrada
	 * @param verso
	 * @author Matteo Daverio
	 */
	public void aggiungiStrade(int posizioneStrada, String verso) {
		Direzione direzione = new Direzione(posizioneStrada, verso);
		strade.add(direzione);
	}

	/**
	 * 
	 * @return posizione strada
	 * @author Matteo Daverio
	 */
	public int getPosizione() {
		return posizione;
	}

	/**
	 * 
	 * @return numero regione a destra
	 * @author Matteo Daverio
	 */
	public int getRegioneDestra() {
		return regioneDestra;
	}

	/**
	 * 
	 * @return numero regione a sinistra
	 * @author Matteo Daverio
	 */
	public int getRegioneSinistra() {
		return regioneSinistra;
	}

	/**
	 * 
	 * @return numero dado richiesto per attraversare la strada
	 * @author Matteo Daverio
	 */
	public int getNumeroDado() {
		return numeroDado;
	}

}
