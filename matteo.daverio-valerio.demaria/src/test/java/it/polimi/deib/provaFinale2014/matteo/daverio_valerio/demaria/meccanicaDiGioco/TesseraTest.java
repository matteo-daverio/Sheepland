package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;

import org.junit.Before;
import org.junit.Test;

public class TesseraTest {

	Tessera tessera;
	
	@Before
	/**
	 * inizializza tessera
	 * 
	 * @author Matteo Daverio
	 */
	public void setUp() {
		tessera=new Tessera(TipoTerreno.ACQUA,3);
	}
	
	@Test
	/**
	 * getter tipo
	 * 
	 * @author Matteo Daverio
	 */
	public void testGetTipo() {
		assertEquals("Errore getter tipo",TipoTerreno.ACQUA,tessera.getTipo());
	}
	
	@Test
	/**
	 * getter costo
	 * 
	 * @author Matteo Daverio
	 */
	public void testGetCosto() {
		assertEquals("Errore getter costo",3,tessera.getCosto());
	}

}
