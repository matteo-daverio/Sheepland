package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import org.junit.Before;
import org.junit.Test;

public class PecoraNeraTest {
    
	private Partita partita;
	private PecoraNera pecoraNera;
	
	@Before
	public void setUp()throws Exception{
	
		partita =new Partita();
		pecoraNera= new PecoraNera();
	}
	
	@Test
	public void costruttoreTest() {
		assertEquals("posizione iniziale sbagliata",Costanti.POSIZIONE_SHEEPBURG,pecoraNera.getPosizione());
		assertEquals("tipo sbagliato",Costanti.TIPO_PECORA_PECORANERA,pecoraNera.getTipoPecora());			
	}
	
	

}
