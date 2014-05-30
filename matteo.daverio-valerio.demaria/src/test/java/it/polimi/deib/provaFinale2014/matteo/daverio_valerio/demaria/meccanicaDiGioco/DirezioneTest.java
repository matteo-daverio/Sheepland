package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import org.junit.Before;
import org.junit.Test;

public class DirezioneTest {

	private Partita partita;

	@Before
	/**
	 * 
	 * @throws Exception
	 * @author Matteo Daverio
	 */
	public void setUp() throws Exception {
		partita = new Partita();
		partita.start();
	}
	
	@Test
	/**
	 * test del getter per la posizione della strada adiacente
	 * 
	 * @author Matteo Daverio
	 */
	public void getPosizioneTest() {
		assertEquals("Strada adiacente errata",1,partita.getStrade().get(0).getStrade().get(0).getPosizione());
	}
	
	@Test
	/**
	 * test del getter per la direzione verso la strada adiacente
	 * 
	 * @author Matteo Daverio
	 */
	public void getDirezioneTest() {
		assertEquals("Direzione adiacente errata","nord-est",partita.getStrade().get(0).getStrade().get(0).getVerso());
	}

}
