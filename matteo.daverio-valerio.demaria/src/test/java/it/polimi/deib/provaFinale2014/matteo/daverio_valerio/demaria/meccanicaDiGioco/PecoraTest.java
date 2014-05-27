package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
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
	public void parametriInizialiPecoraTest() {
		assertEquals("posizione iniziale di pecora non corretta", 3,
				pecora.getPosizione());
		assertEquals("tipo di pecora non corretto",
				Costanti.TIPO_PECORA_PECORA, pecora.getTipoPecora());
	}
	
	@Test
	public void getPosizionePecoraTest(){
		assertEquals("getter posizione sbagliato",3,pecora.getPosizione());
	}
	
	@Test
	public void setPosizionePecoraTest(){
		pecora.setPosizione(4);
		assertEquals("setter posizione sbagliato",4,pecora.getPosizione());
	}

	@Test
	public void getTipoPecoraTest(){
		assertEquals("getter tipo sbagliato",Costanti.TIPO_PECORA_PECORA,pecora.getTipoPecora());
	}
	
	@Test
	public void setTipoPecoraTest(){
		pecora.setTipoPecora(Costanti.TIPO_PECORA_AGNELLO);;
		assertEquals("setter sbagliato",Costanti.TIPO_PECORA_AGNELLO,pecora.getTipoPecora());
	}
	
	@Test
	public void getTurnoTest(){
		assertEquals("getter turno sbagliato",0,pecora.getTurno());
	}
	
	@Test
	public void incrementaTurnoTest(){
		pecora.incrementaTurno();
		assertEquals("incrementa turno sbagliato",1,pecora.getTurno());
	}
	
	@Test
	public void movimentoPecoraTest(){
		strada=new Strada(41,3,2,6);
		pecora.muoviPecora(strada);
		assertEquals("movimento sbagliato della pecora",2,pecora.getPosizione());
	}

}
