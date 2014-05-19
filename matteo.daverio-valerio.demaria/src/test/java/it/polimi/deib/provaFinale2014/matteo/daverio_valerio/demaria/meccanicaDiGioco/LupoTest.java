package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import java.util.ArrayList;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.*;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.*;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;

//TestCase della classe Lupo
public class LupoTest {

	private Lupo lupo;
	private Partita partita;

	@Before
	// prima di fare i test creo un'istanza di lupo e partita
	public void setUp() throws Exception {
		partita = new Partita();

		lupo = new Lupo();
	}

	@Test
	// all'inizio il lupo deve stare a sheepsburg
	public void posizioneInizialeDelLupoTest() {
		assertEquals("il lupo non si trova inizialmente a sheepsburg",
				Costanti.POSIZIONE_SHEEPBURG, lupo.getPosizione());
	}

	// @Ignore("problemi con strade.add nella classe partita")

	// TODO: testare il movimento da sheepburg con: -strada non recintata, -
	// strada recintata(ma alcune senza recinto), - strade tutte recintate, -
	// dado non presente
	
	@Test
	// testo il movimento corretto del lupo
	public void movimentoLupoTest() {
		partita.start();
		if (!partita.getStrade().get(33).recintata()
				|| (partita.getStrade().get(33).recintata()
						&& partita.getStrade().get(35).recintata()
						&& partita.getStrade().get(36).recintata()
						&& partita.getStrade().get(38).recintata()
						&& partita.getStrade().get(40).recintata() && partita
						.getStrade().get(32).recintata()))
			assertEquals("movimento non corretto", 1,
					lupo.muoviLupo(6, partita.getStrade()));

	}

	@Test
	public void mangiaPecoraTest() {
		ArrayList<Pecora> pecore = new ArrayList<Pecora>();
		pecore.add(new Pecora(1, Costanti.TIPO_PECORA_PECORA));
		lupo.setPosizione(1);
		lupo.mangiaPecora(pecore);
		assertEquals(pecore.size(), 0);
		// per confrotare se due arraylist sono uguali:
		// Assert.assertEquals(Object expected, Object actual);

	}

}
