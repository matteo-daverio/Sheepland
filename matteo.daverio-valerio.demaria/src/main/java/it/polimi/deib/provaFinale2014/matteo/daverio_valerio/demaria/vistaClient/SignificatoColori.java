package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * classe che si occupa di capire la posizione su cui ha cliccato l'utente
 * 
 * @author Matteo Daverio
 *
 */
public class SignificatoColori {
	private static final Map<Color, Posizione> mappaColori = new HashMap<Color, Posizione>();

	/**
	 * avvia creazione hashmap
	 * 
	 * @author Matteo Daverio
	 */
	public SignificatoColori() {
		inizializza();
	}

	/**
	 * costruzione hashmap che contiene la corrispondenza tra posizione e colore
	 * 
	 * @author Matteo Daverio
	 */
	private static void inizializza() {

		// strade
		mappaColori.put(new Color(50, 0, 0), new Posizione("Strada", 0));
		mappaColori.put(new Color(0, 50, 0), new Posizione("Strada", 1));
		mappaColori.put(new Color(0, 0, 50), new Posizione("Strada", 2));
		mappaColori.put(new Color(50, 50, 0), new Posizione("Strada", 3));
		mappaColori.put(new Color(50, 0, 50), new Posizione("Strada", 4));
		mappaColori.put(new Color(50, 50, 50), new Posizione("Strada", 5));
		mappaColori.put(new Color(0, 50, 50), new Posizione("Strada", 6));
		mappaColori.put(new Color(100, 0, 0), new Posizione("Strada", 7));
		mappaColori.put(new Color(100, 100, 0), new Posizione("Strada", 8));
		mappaColori.put(new Color(100, 100, 100), new Posizione("Strada", 9));
		mappaColori.put(new Color(0, 100, 0), new Posizione("Strada", 10));
		mappaColori.put(new Color(0, 100, 100), new Posizione("Strada", 11));
		mappaColori.put(new Color(0, 0, 100), new Posizione("Strada", 12));
		mappaColori.put(new Color(100, 0, 100), new Posizione("Strada", 13));
		mappaColori.put(new Color(50, 50, 100), new Posizione("Strada", 14));
		mappaColori.put(new Color(50, 100, 50), new Posizione("Strada", 15));
		mappaColori.put(new Color(100, 50, 50), new Posizione("Strada", 16));
		mappaColori.put(new Color(100, 100, 50), new Posizione("Strada", 17));
		mappaColori.put(new Color(100, 50, 100), new Posizione("Strada", 18));
		mappaColori.put(new Color(50, 100, 100), new Posizione("Strada", 19));
		mappaColori.put(new Color(0, 50, 100), new Posizione("Strada", 20));
		mappaColori.put(new Color(50, 0, 100), new Posizione("Strada", 21));
		mappaColori.put(new Color(0, 100, 50), new Posizione("Strada", 22));
		mappaColori.put(new Color(100, 0, 50), new Posizione("Strada", 23));
		mappaColori.put(new Color(50, 100, 0), new Posizione("Strada", 24));
		mappaColori.put(new Color(100, 50, 0), new Posizione("Strada", 25));
		mappaColori.put(new Color(150, 0, 0), new Posizione("Strada", 26));
		mappaColori.put(new Color(0, 150, 0), new Posizione("Strada", 27));
		mappaColori.put(new Color(0, 0, 150), new Posizione("Strada", 28));
		mappaColori.put(new Color(150, 150, 0), new Posizione("Strada", 29));
		mappaColori.put(new Color(150, 0, 150), new Posizione("Strada", 30));
		mappaColori.put(new Color(0, 150, 150), new Posizione("Strada", 31));
		mappaColori.put(new Color(50, 100, 150), new Posizione("Strada", 32));
		mappaColori.put(new Color(100, 50, 150), new Posizione("Strada", 33));
		mappaColori.put(new Color(50, 150, 100), new Posizione("Strada", 34));
		mappaColori.put(new Color(100, 150, 50), new Posizione("Strada", 35));
		mappaColori.put(new Color(150, 50, 100), new Posizione("Strada", 36));
		mappaColori.put(new Color(150, 100, 50), new Posizione("Strada", 37));
		mappaColori.put(new Color(0, 50, 150), new Posizione("Strada", 38));
		mappaColori.put(new Color(50, 0, 150), new Posizione("Strada", 39));
		mappaColori.put(new Color(0, 150, 50), new Posizione("Strada", 40));
		mappaColori.put(new Color(50, 150, 0), new Posizione("Strada", 41));

		// regioni
		mappaColori.put(new Color(255, 0, 0), new Posizione("Regione", 0));
		mappaColori.put(new Color(0, 255, 0), new Posizione("Regione", 1));
		mappaColori.put(new Color(0, 0, 255), new Posizione("Regione", 2));
		mappaColori.put(new Color(255, 250, 50), new Posizione("Regione", 3));
		mappaColori.put(new Color(255, 250, 250), new Posizione("Regione", 4));
		mappaColori.put(new Color(50, 100, 255), new Posizione("Regione", 5));
		mappaColori.put(new Color(255, 250, 150), new Posizione("Regione", 6));
		mappaColori.put(new Color(0, 150, 255), new Posizione("Regione", 7));
		mappaColori.put(new Color(200, 200, 0), new Posizione("Regione", 8));
		mappaColori.put(new Color(255, 150, 0), new Posizione("Regione", 9));
		mappaColori.put(new Color(0, 255, 50), new Posizione("Regione", 10));
		mappaColori.put(new Color(255, 0, 200), new Posizione("Regione", 11));
		mappaColori.put(new Color(50, 0, 255), new Posizione("Regione", 12));
		mappaColori.put(new Color(255, 50, 0), new Posizione("Regione", 13));
		mappaColori.put(new Color(0, 255, 250), new Posizione("Regione", 14));
		mappaColori.put(new Color(255, 0, 150), new Posizione("Regione", 15));
		mappaColori.put(new Color(0, 255, 150), new Posizione("Regione", 16));
		mappaColori.put(new Color(255, 0, 50), new Posizione("Regione", 17));
		mappaColori.put(new Color(150, 255, 0), new Posizione("Regione", 18));
		
		// errore
		mappaColori.put(new Color(0,0,0), new Posizione("Mare",0));

	}

	/**
	 * interroga la hashmap ricevendo un colore e restituendo la posizione a esso collegata
	 * 
	 * @param c
	 * @return posizione su cui l'utente ha cliccato
	 * @author Matteo Daverio
	 */
	public Posizione getPosizione(Color c) {
		return mappaColori.get(c);
	}
}
