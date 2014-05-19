package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

//TestCase di pecora
public class PecoraTest {

	private Pecora pecora;
	private Strada strada;

	@Before
	// prima di fare i test creo un'istanza di pecora
	public void setUp() throws Exception {
		 pecora = new Pecora(3, Costanti.TIPO_PECORA_PECORA);
	}

	@Test
	// controllo che i parametri della pecora creata siano giusti
	public void parametriInizialiPecora() {
		assertEquals("posizione iniziale di pecora non corretta", 3,
				pecora.getPosizione());
		assertEquals("tipo di pecora non corretto",
				Costanti.TIPO_PECORA_PECORA, pecora.getTipoPecora());
	}
	
	@Test
	public void movimentoPecoraTest(){
		strada=new Strada(41,3,2,6);
		pecora.muoviPecora(strada);
		assertEquals("movimento sbagliato della pecora",2,pecora.getPosizione());
	}

}
