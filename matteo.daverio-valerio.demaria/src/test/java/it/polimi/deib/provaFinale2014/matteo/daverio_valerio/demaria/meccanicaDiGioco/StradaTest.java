package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StradaTest {

	private Strada strada;
	private Direzione direzione;

	@Before
	/**
	 * creo un istanza di strada
	 * 
	 * @author Matteo Daverio
	 */
	public void setUp() {
		strada = new Strada(5, 3, 2, 6);
	}

	@Test
	/**
	 * testo i metodi dei recinti
	 * 
	 * @author Matteo Daverio
	 */
	public void testRecintata() {
		assertFalse(strada.recintata());
		strada.aggiungiRecinto();
		assertTrue(strada.recintata());
	}

	@Test
	/**
	 * getter posizione
	 * 
	 * @author Matteo Daverio
	 */
	public void testGetPosizione() {
		assertEquals("errore getter posizione", 5, strada.getPosizione());
	}
	
	@Test
	/**
	 * getter regione destra
	 * 
	 * @author Matteo Daverio
	 */
	public void testGetRegioneDestra() {
		assertEquals("errore getter regione destra",3,strada.getRegioneDestra());
	}

	@Test
	/**
	 * getter regione sinistra
	 * 
	 * @author Matteo Daverio
	 */
	public void testGetRegioneSinistra() {
		assertEquals("errore getter regione sinistra",2,strada.getRegioneSinistra());
	}

	@Test
	/**
	 * getter numero dado
	 * 
	 * @author Matteo Daverio
	 */
	public void testGetDado() {
		assertEquals("errore getter numero dado",6,strada.getNumeroDado());
	}
	
	@Test
	/** 
	 * test aggiunta strade
	 * 
	 * @author Matteo Daverio
	 */
	public void testAggiuntaStrade(){
		direzione=new Direzione(25, "nord");
		strada.aggiungiStrade(25, "nord");
		assertTrue(direzione.getPosizione()==strada.getStrade().get(0).getPosizione());
		assertTrue(direzione.getVerso().equals(strada.getStrade().get(0).getVerso()));
		assertTrue(strada.getStrade().size()==1);
	}
	
}
