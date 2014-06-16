package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * classe per gestire la mappa del lupo
 * 
 * @author Matteo Daverio
 *
 */
public class PosizioniLupo {

	private static final Map<Posizione, Point> mappaPunti = new HashMap<Posizione, Point>();

	/**
	 * costruttore
	 * 
	 * @author Matteo Daverio
	 */
	public PosizioniLupo() {
		inizializzaMappaPunti();
	}

	/**
	 * inizializza la mappa dei punti
	 * 
	 * @author Matteo Daverio
	 */
	public static void inizializzaMappaPunti() {

		// posizione regioni

		mappaPunti.put(new Posizione("Regione", 0), new Point(711, 591));
		mappaPunti.put(new Posizione("Regione", 1), new Point(600, 535));
		mappaPunti.put(new Posizione("Regione", 2), new Point(596, 634));
		mappaPunti.put(new Posizione("Regione", 3), new Point(733, 713));
		mappaPunti.put(new Posizione("Regione", 4), new Point(874, 631));
		mappaPunti.put(new Posizione("Regione", 5), new Point(888, 437));
		mappaPunti.put(new Posizione("Regione", 6), new Point(772, 342));
		mappaPunti.put(new Posizione("Regione", 7), new Point(535, 220));
		mappaPunti.put(new Posizione("Regione", 8), new Point(398, 222));
		mappaPunti.put(new Posizione("Regione", 9), new Point(470, 478));
		mappaPunti.put(new Posizione("Regione", 10), new Point(460, 742));
		mappaPunti.put(new Posizione("Regione", 11), new Point(584, 886));
		mappaPunti.put(new Posizione("Regione", 12), new Point(726, 966));
		mappaPunti.put(new Posizione("Regione", 13), new Point(860, 864));
		mappaPunti.put(new Posizione("Regione", 14), new Point(981, 719));
		mappaPunti.put(new Posizione("Regione", 15), new Point(1008, 495));
		mappaPunti.put(new Posizione("Regione", 16), new Point(1053, 328));
		mappaPunti.put(new Posizione("Regione", 17), new Point(979, 189));
		mappaPunti.put(new Posizione("Regione", 18), new Point(829, 103));

		// posizione strade

		mappaPunti.put(new Posizione("Strada", 0), new Point(406, 449));
		mappaPunti.put(new Posizione("Strada", 1), new Point(539, 393));
		mappaPunti.put(new Posizione("Strada", 2), new Point(485, 302));
		mappaPunti.put(new Posizione("Strada", 3), new Point(622, 355));
		mappaPunti.put(new Posizione("Strada", 4), new Point(712, 312));
		mappaPunti.put(new Posizione("Strada", 5), new Point(720, 189));
		mappaPunti.put(new Posizione("Strada", 6), new Point(785, 288));
		mappaPunti.put(new Posizione("Strada", 7), new Point(883, 200));
		mappaPunti.put(new Posizione("Strada", 8), new Point(845, 337));
		mappaPunti.put(new Posizione("Strada", 9), new Point(816, 425));
		mappaPunti.put(new Posizione("Strada", 10), new Point(900, 381));
		mappaPunti.put(new Posizione("Strada", 11), new Point(1002, 297));
		mappaPunti.put(new Posizione("Strada", 12), new Point(970, 414));
		mappaPunti.put(new Posizione("Strada", 13), new Point(1058, 444));
		mappaPunti.put(new Posizione("Strada", 14), new Point(956, 511));
		mappaPunti.put(new Posizione("Strada", 15), new Point(956, 607));
		mappaPunti.put(new Posizione("Strada", 16), new Point(1037, 684));
		mappaPunti.put(new Posizione("Strada", 17), new Point(925, 731));
		mappaPunti.put(new Posizione("Strada", 18), new Point(938, 900));
		mappaPunti.put(new Posizione("Strada", 19), new Point(852, 809));
		mappaPunti.put(new Posizione("Strada", 20), new Point(801, 859));
		mappaPunti.put(new Posizione("Strada", 21), new Point(797, 1000));
		mappaPunti.put(new Posizione("Strada", 22), new Point(731, 910));
		mappaPunti.put(new Posizione("Strada", 23), new Point(598, 1046));
		mappaPunti.put(new Posizione("Strada", 24), new Point(671, 872));
		mappaPunti.put(new Posizione("Strada", 25), new Point(598, 830));
		mappaPunti.put(new Posizione("Strada", 26), new Point(478, 943));
		mappaPunti.put(new Posizione("Strada", 27), new Point(550, 751));
		mappaPunti.put(new Posizione("Strada", 28), new Point(467, 683));
		mappaPunti.put(new Posizione("Strada", 29), new Point(534, 622));
		mappaPunti.put(new Posizione("Strada", 30), new Point(535, 500));
		mappaPunti.put(new Posizione("Strada", 31), new Point(593, 576));
		mappaPunti.put(new Posizione("Strada", 32), new Point(660, 614));
		mappaPunti.put(new Posizione("Strada", 33), new Point(656, 521));
		mappaPunti.put(new Posizione("Strada", 34), new Point(688, 411));
		mappaPunti.put(new Posizione("Strada", 35), new Point(728, 470));
		mappaPunti.put(new Posizione("Strada", 36), new Point(793, 518));
		mappaPunti.put(new Posizione("Strada", 37), new Point(858, 574));
		mappaPunti.put(new Posizione("Strada", 38), new Point(803, 613));
		mappaPunti.put(new Posizione("Strada", 39), new Point(800, 723));
		mappaPunti.put(new Posizione("Strada", 40), new Point(737, 658));
		mappaPunti.put(new Posizione("Strada", 41), new Point(673, 743));

	}

	/**
	 * restituisce il punto su cui collocare il lupo in relazione alla posizione
	 * 
	 * @param posizione
	 * @return punto su cui collocare il lupo
	 * @author Matteo Daverio
	 */
	public Point getLocalizzazione(Posizione posizione) {
		return mappaPunti.get(posizione);
	}
	
}
