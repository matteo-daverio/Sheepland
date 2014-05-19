package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;

import org.junit.Before;
import org.junit.Test;

public class PartitaTest {

	private Partita partita;
	
	@Before
	public void setUp() throws Exception {
      partita=new Partita();
      partita.start();
	}
	
	//TODO test sui getters e setters
	
	@Test
	public void costruttoreTest() {
		assertEquals("turno sbagliato",1,partita.getTurno());
		assertEquals("contatore recinti sbagliato",0,partita.getContatoreRecinti());		
	}
	
	@Test
	public void incrementaTurnoTest(){
		partita.setNumeroGiocatori(3);
		partita.incrementaTurno();
		assertEquals("incrementatore turno sbagliato",2,partita.getTurno());
		partita.incrementaTurno();
		partita.incrementaTurno();
		assertEquals("incrementatore turno sbagliato",1,partita.getTurno());
	}
	
	@Test
	public void addPecoraTest(){
		Pecora pecora =new Pecora(3,Costanti.TIPO_PECORA_MONTONE);
		partita.addPecora(pecora);
		assertEquals("add pecora sbagliato",1,partita.getPecore().size());
	}

}
