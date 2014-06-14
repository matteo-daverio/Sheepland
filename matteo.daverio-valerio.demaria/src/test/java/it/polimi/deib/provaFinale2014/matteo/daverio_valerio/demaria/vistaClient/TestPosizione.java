package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestPosizione {

	Posizione posizione;
	Posizione posizione2;
	Posizione posizione3;
	Posizione posizione4;

	@Before
	/**
	 * setUp per creare oggetto posizione per metodi getter
	 * 
	 * @author Matteo Daverio
	 */
	public void setUp() {
		posizione = new Posizione("Strada", 5);
	}

	@Test
	/**
	 * test classe hashCode, deve produrre stessa hash per oggetti uguali, ma diversa per oggetti diversi
	 * 
	 * @author Matteo Daverio
	 */
	public void testHashCode() {
		posizione2 = new Posizione("Strada", 32);
		posizione3 = new Posizione("Strada", 32);
		posizione4 = new Posizione("Regione", 13);
		assertTrue(posizione2.hashCode() == posizione3.hashCode());
		assertFalse(posizione2.hashCode() == posizione4.hashCode());
	}

	@Test
	/**
	 * getter tipo
	 * 
	 * @author Matteo Daverio
	 */
	public void testGetTipo() {
		assertEquals("Errore return tipo", "Strada", posizione.getTipo());
	}

	@Test
	/**
	 * getter posizione
	 * 
	 * @author Matteo Daverio
	 */
	public void testGetPosizione() {
		assertEquals("Errore return posizione", 5, posizione.getPosizione());
	}

	@Test
	/**
	 * setter tipo
	 * 
	 * @author Matteo Daverio
	 */
	public void testSetTipo() {
		posizione2 = new Posizione("Strada", 32);
		posizione2.setTipo("Regione");
		assertEquals("Errore settaggio tipo", "Regione", posizione2.getTipo());
	}

	@Test
	/**
	 * setter posizione
	 * 
	 * @author Matteo Daverio
	 */
	public void testSetPosizione() {
		posizione2 = new Posizione("Strada", 32);
		posizione2.setPosizione(4);
		assertEquals("Errore settaggio tipo", 4, posizione2.getPosizione());
	}

	@Test
	/**
	 * test metodo equals
	 * 
	 * @author Matteo Daverio
	 */
	public void testEqualsObject() {
		posizione2 = new Posizione("Strada", 32);
		posizione3 = new Posizione("Strada", 32);
		assertTrue(posizione2.equals(posizione3));
	}

}
