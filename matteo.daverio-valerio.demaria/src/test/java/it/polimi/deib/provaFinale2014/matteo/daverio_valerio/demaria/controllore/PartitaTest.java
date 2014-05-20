package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;

import org.junit.Before;
import org.junit.Test;

public class PartitaTest {

	private Partita partita;

	@Before
	public void setUp() throws Exception {
		partita = new Partita();
		partita.start();
		partita.aggiungiPastore("Valerio", 1);

	}

	// TODO test sui getters e setters

	@Test
	public void costruttoreTest() {
		assertEquals("turno sbagliato", 1, partita.getTurno());
		assertEquals("contatore recinti sbagliato", 0,
				partita.getContatoreRecinti());
	}

	@Test
	public void creazionePecoreTest() {
		assertEquals("pecore non crate correttamente", 18, partita.getPecore()
				.size());
	}

	@Test
	public void posizionePecoraNeraTest() {
		assertEquals("pecora nera non a sheepsburg", 0, partita.getPecoraNera()
				.getPosizione());
	}

	@Test
	public void posizioneLupoTest() {
		assertEquals("lupo non a sheepsburg", 0, partita.getLupo()
				.getPosizione());
	}

	@Test
	public void aggiungiPastoreTest() {
		assertEquals("pastore non aggiunto", 1, partita.getPastori().size());
		assertEquals("nome pastore sbagliato", "Valerio", partita.getPastori()
				.get(0).getNomeGiocatore());
		assertEquals("turno pastore sbagliato", 1, partita.getPastori().get(0)
				.getTurnoDiGioco());
	}

	@Test
	public void incrementaTurnoTest() {
		partita.setNumeroGiocatori(3);
		partita.incrementaTurno();
		assertEquals("incrementatore turno sbagliato", 2, partita.getTurno());
		partita.incrementaTurno();
		partita.incrementaTurno();
		assertEquals("incrementatore turno sbagliato", 1, partita.getTurno());
	}

	@Test
	// movimento pastore con strada sgombra e limitrofa
	public void muoviPastoreTest1() {
		partita.getPastori().get(0).setPosizione(30);
		partita.muoviPastore(1);
		assertEquals("movimento pastore non corretto", 1, partita.getPastori()
				.get(0).getPosizione());
		assertEquals("il pastore spende denaro per movimenti gratis", 20,
				partita.getPastori().get(0).getDenaro());
		assertEquals("errore posizionamento recinto", true, partita.getStrade()
				.get(30).recintata());

	}

	@Test
	// movimento pastore con strada sgombra e ma non limitrofa con soldi
	public void muoviPastoreTest2() {
		partita.getPastori().get(0).setPosizione(30);
		partita.muoviPastore(25);
		assertEquals("movimento pastore non corretto", 25, partita.getPastori()
				.get(0).getPosizione());
		assertEquals("non viene speso 1 denaro", 19, partita.getPastori()
				.get(0).getDenaro());
		assertEquals("errore posizionamento recinto", true, partita.getStrade()
				.get(30).recintata());

	}

	@Test
	// movimento pastore con strada occupata da un altro pastore
	public void muoviPastoreTest3() {
		partita.aggiungiPastore("Matteo", 2);
		partita.getPastori().get(1).setPosizione(25);
		partita.getPastori().get(0).setPosizione(30);
		partita.muoviPastore(25);
		assertEquals("movimento pastore non corretto", 30, partita.getPastori()
				.get(0).getPosizione());
		assertEquals("viene speso denaro senza muoversi", 20, partita
				.getPastori().get(0).getDenaro());
		assertEquals("errore posizionamento recinto", false, partita
				.getStrade().get(30).recintata());

	}

	@Test
	// movimento pastore con strada recintata
	public void muoviPastoreTest4() {
		partita.getPastori().get(0).setPosizione(30);
		partita.getStrade().get(25).aggiungiRecinto();
		partita.muoviPastore(25);
		assertEquals("movimento pastore non corretto", 30, partita.getPastori()
				.get(0).getPosizione());
		assertEquals("viene speso denaro senza muoversi", 20, partita
				.getPastori().get(0).getDenaro());
		assertEquals("errore posizionamento recinto", false, partita
				.getStrade().get(30).recintata());

	}

}
