package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import static org.junit.Assert.assertEquals;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import org.junit.Before;
import org.junit.Test;

public class PecoraNeraTest {

	private Partita partita;
	private PecoraNera pecoraNera;

	@Before
	public void setUp() throws Exception {

		partita = new Partita();
		pecoraNera = new PecoraNera();
	}

	@Test
	public void costruttoreTest() {
		assertEquals("posizione iniziale sbagliata",
				Costanti.POSIZIONE_SHEEPBURG, pecoraNera.getPosizione());
		assertEquals("tipo sbagliato", Costanti.TIPO_PECORA_PECORANERA,
				pecoraNera.getTipoPecora());
	}

	@Test
	// fuga con strada sgombra
	public void fugaPecoraNeraTest1() {
		partita.start();
		assertEquals(
				"fuga della pecora nera errata",
				1,
				pecoraNera.fugaPecoraNera(6, partita.getPastori(),
						partita.getStrade()));
	}

	@Test
	// fuga con strada occupata da pastore
	public void fugaPecoraNeraTest2() {
		partita.start();
		partita.aggiungiPastore("Valerio", 1);
		partita.getPastori().get(0).setPosizione(33);
		assertEquals(
				"fuga della pecora nera errata",
				0,
				pecoraNera.fugaPecoraNera(6, partita.getPastori(),
						partita.getStrade()));
	}

	@Test
	// fuga con strada recintata
	public void fugaPecoraNeraTest3() {
		partita.start();
		partita.getStrade().get(33).aggiungiRecinto();
		assertEquals(
				"fuga della pecora nera errata",
				0,
				pecoraNera.fugaPecoraNera(6, partita.getPastori(),
						partita.getStrade()));
	}

}
