package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

/**
 * classe di test per le hashmap presenti nella grafica 
 * 
 * @author Matteo Daverio
 *
 */
public class TestHashMap {

	private SignificatoColori significatoColori;
	private MappaturaPosizioni mappaturaPosizioni;
	private PosizioniAgnelli posizioniAgnelli;
	private PosizioniLupo posizioniLupo;
	private PosizioniMontoni posizioniMontoni;
	private PosizioniPecore posizioniPecore;
	
	
	@Before
	/**
	 * prima di fare i test creo le istanze delle hashmap
	 * 
	 * @author Matteo Daverio
	 */
	public void setUp() {
		significatoColori=new SignificatoColori();
		mappaturaPosizioni= new MappaturaPosizioni();
		posizioniAgnelli= new PosizioniAgnelli();
		posizioniLupo= new PosizioniLupo();
		posizioniMontoni= new PosizioniMontoni();
		posizioniPecore=new PosizioniPecore();
	}
	
	@Test
	/**
	 * test per la hashmap significatoColori
	 */
	public void testSignificatoColori() {
		assertEquals("errore",new Posizione("Regione", 14),significatoColori.getPosizione(new Color(0, 255, 250)));
	}
	
	@Test
	/**
	 * test per la hashmap mappaturaPosizioni
	 */
	public void testMappaturaPosizioni() {
		assertEquals("errore",new Point(803, 613),mappaturaPosizioni.getLocalizzazione(new Posizione("Strada", 38)));
	}

	@Test
	/**
	 * test per la hashmap posizioniAgnelli
	 */
	public void testPosizioniAgnelli() {
		assertEquals("errore",new Point(816, 154),posizioniAgnelli.getLocalizzazione(new Posizione("Regione", 18)));
	}
	
	
	@Test
	/**
	 * test per la hashmap posizioniLupo
	 */
	public void testPosizioniLupo() {
		assertEquals("errore",new Point(829, 103),posizioniLupo.getLocalizzazione(new Posizione("Regione", 18)));
	}
	
	
	@Test
	/**
	 * test per la hashmap posizioniMontoni
	 */
	public void testPosizioniMontoni() {
		assertEquals("errore",new Point(800, 202),posizioniMontoni.getLocalizzazione(new Posizione("Regione", 18)));
	}
	
	@Test
	/**
	 * test per la hashmap posizioniPecore
	 */
	public void testPosizioniPecore() {
		assertEquals("errore",new Point(793, 244),posizioniPecore.getLocalizzazione(new Posizione("Regione", 18)));
	}
	
	
	
}
