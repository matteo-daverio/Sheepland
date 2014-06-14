package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;

import java.util.List;

/**
 * 
 * @author Matteo Daverio
 * 
 */
public class PecoraNera extends Pecora {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3357558608672302317L;

	/**
	 * costruttore
	 * 
	 * @author Matteo Daverio
	 */
	public PecoraNera() {
		super(Costanti.POSIZIONE_SHEEPBURG, Costanti.TIPO_PECORA_PECORANERA);
	}

	/**
	 * movimento a inizio turno della pecora nera
	 * 
	 * @param lancioDado
	 * @param pastori
	 * @param strade
	 * @return nuova posizione della pecora nera
	 * @author Matteo Daverio
	 */

	public int fugaPecoraNera(int lancioDado, List<Pastore> pastori,
			List<Strada> strade) {
		Strada strada = esisteStrada(lancioDado, strade);
		if (strada != null && !strada.recintata()
				&& !stradaOccupata(strada, pastori)) {
			if (strada.getRegioneDestra() == this.getPosizione()) {
				this.setPosizione(strada.getRegioneSinistra());
			} else {
				this.setPosizione(strada.getRegioneDestra());
			}
		}

		return this.getPosizione();
	}

	/**
	 * 
	 * @param lancioDado
	 * @param strade
	 * @return se esiste, la strada su cui la pecora nera dovrebbe saltare in
	 *         base al lancio del dado
	 * @author Matteo Daverio
	 */

	private Strada esisteStrada(int lancioDado, List<Strada> strade) {
		for (Strada strada : strade) {
			if (stradaAdiacente(strada) && strada.getNumeroDado() == lancioDado) {
				return strada;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param strada
	 * @return se la pecora nera è in un terreno adiacente alla strada passata
	 * @author Matteo Daverio
	 */

	private boolean stradaAdiacente(Strada strada) {
		return strada.getRegioneDestra() == this.getPosizione()
				|| strada.getRegioneSinistra() == this.getPosizione();
	}

	/**
	 * 
	 * @param strada
	 * @param pastori
	 * @return se la strada è occupata da un pastore
	 * @author Matteo Daverio
	 */
	private boolean stradaOccupata(Strada strada, List<Pastore> pastori) {
		for (Pastore pastore : pastori) {
			if (pastore.getPosizione() == strada.getPosizione()) {
				return true;
			}
		}
		return false;
	}

}
