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
		partita.start();
	}

	@Test
	// all'inizio il lupo deve stare a sheepsburg
	public void posizioneInizialeDelLupoTest() {
		assertEquals("il lupo non si trova inizialmente a sheepsburg",
				Costanti.POSIZIONE_SHEEPBURG, lupo.getPosizione());
	}
		
	@Test
	public void getPosizioneLupoTest(){
		assertEquals("getter posizione sbagliato",0,lupo.getPosizione());
	}
	
	@Test
	public void setPosizioneLupoTest(){
		lupo.setPosizione(1);
		assertEquals("setter posizione sbagliato",1,lupo.getPosizione());
	}
	
	@Test
	// movimento con strada non recintata
	public void movimento1LupoTest() {
		partita.start();
		
		assertEquals("movimento non corretto", 1,
				lupo.muoviLupo(6, partita.getStrade()));
	}

	@Test
	// movimento con tutte strade recintate
	public void movimento2LupoTest() {
		partita.start();
		partita.getStrade().get(33).aggiungiRecinto();
		partita.getStrade().get(35).aggiungiRecinto();
		partita.getStrade().get(36).aggiungiRecinto();
		partita.getStrade().get(38).aggiungiRecinto();
		partita.getStrade().get(40).aggiungiRecinto();
		partita.getStrade().get(32).aggiungiRecinto();

		assertEquals("movimento non corretto", 1,
				lupo.muoviLupo(6, partita.getStrade()));
	}

	@Test
	// movimento con una strada recintata e almeno una confinante no
	public void movimento3LupoTest() {
		partita.start();
		partita.getStrade().get(33).aggiungiRecinto();

		assertEquals("movimento non corretto", 0,
				lupo.muoviLupo(6, partita.getStrade()));
	}

	@Test
	// movimento con valore del dado non presente in nessuna strada confinante
	public void movimento4LupoTest() {
		partita.start();
		lupo.setPosizione(9);

		assertEquals("movimento non corretto", 9,
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
