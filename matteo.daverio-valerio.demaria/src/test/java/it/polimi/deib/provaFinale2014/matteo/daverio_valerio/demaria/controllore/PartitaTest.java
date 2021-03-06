package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;

import org.junit.Before;
import org.junit.Test;

public class PartitaTest {

	private Partita partita;

	@Before
	public void setUp() throws Exception {
		partita = new MiaPartita();
		partita.start();
		partita.aggiungiPastore("Valerio", 1);
		partita.aggiungiPastore("Matteo", 2);
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
		assertEquals("pastore non aggiunto", 2, partita.getPastori().size());
		assertEquals("nome pastore sbagliato", "Valerio", partita.getPastori()
				.get(0).getNomeGiocatore());
		assertEquals("turno pastore sbagliato", 1, partita.getPastori().get(0)
				.getTurnoDiGioco());
		assertEquals("nome pastore sbagliato", "Matteo", partita.getPastori()
				.get(1).getNomeGiocatore());
		assertEquals("turno pastore sbagliato", 2, partita.getPastori().get(1)
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

	@Test(expected = GameException.class)
	// testo l'eccezione NoMovementException
	public void verificaNoMovementException() throws GameException {
		partita.getPastori().get(0).setPosizione(30);
		partita.muoviPastore(30,0);
	}

	@Test(expected = GameException.class)
	// testo l'eccezione NoMoneyException
	public void verificaMoneyException() throws GameException {
		partita.getPastori().get(0).setPosizione(1);
		partita.getPastori().get(0).setDenaro(0);
		partita.muoviPastore(17,0);
		// TODO controlla il movimento pastore in 17, ogni tanto da errore
	}

	@Test(expected = GameException.class)
	// testo l'eccezione InvalidMovementException per strada recintata
	public void verificaInvalidMovementException1() throws GameException {
		partita.getPastori().get(0).setPosizione(30);
		partita.getStrade().get(17).aggiungiRecinto();
		partita.muoviPastore(17,0);
	}

	@Test(expected = GameException.class)
	// testo l'eccezione InvalidMovementException per strada occupata
	public void verificaInvalidMovementException2() throws GameException {
		partita.getPastori().get(0).setPosizione(30);
		partita.aggiungiPastore("Matteo", 2);
		partita.getPastori().get(1).setPosizione(17);
		partita.muoviPastore(17,0);
	}

	@Test
	// movimento pastore con strada sgombra e limitrofa
	public void muoviPastoreTest1() throws GameException {
		partita.getPastori().get(0).setPosizione(30);
		partita.muoviPastore(1,0);
		assertEquals("movimento pastore non corretto", 1, partita.getPastori()
				.get(0).getPosizione());
		assertEquals("il pastore spende denaro per movimenti gratis", 20,
				partita.getPastori().get(0).getDenaro());
		assertEquals("errore posizionamento recinto", true, partita.getStrade()
				.get(30).recintata());

	}

	@Test
	// movimento pastore con strada sgombra e non limitrofa con soldi
	public void muoviPastoreTest2() throws GameException {
		partita.getPastori().get(0).setPosizione(30);
		partita.getPastori().get(0).setDenaro(20);
		assertFalse(partita.getStrade().get(25).recintata());
		partita.muoviPastore(25,0);
		assertEquals("movimento pastore non corretto", 25, partita.getPastori()
				.get(0).getPosizione());
		assertEquals("non viene speso 1 denaro", 19, partita.getPastori()
				.get(0).getDenaro());
		assertEquals("errore posizionamento recinto", true, partita.getStrade()
				.get(30).recintata());

	}

	@Test
	public void muoviLupoTest() {
		int numeroPecore = partita.getPecore().size();
		partita.getLupo().setPosizione(4);
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(13);
		}
		partita.muoviLupo();
		assertEquals("Il lupo si sposta male", 13, partita.getLupo()
				.getPosizione());
		assertEquals("Il lupo non mangia", numeroPecore - 1, partita
				.getPecore().size());
	}

	@Test
	/**
	 * abbattimento pecora con pecore adiacenti
	 * 
	 * @throws NoSheepInShireException
	 * @throws NoMoneyException
	 * @throws IllegalShireException
	 * @author Matteo Daverio
	 */
	public void abbattiTest() throws GameException {
		int numeroPecore = partita.getPecore().size();
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(5);
		}
		partita.getPastori().get(0).setPosizione(10);
		// abbatte la pecora numero 0 in posizione 5
		partita.abbatti(5,0);
		assertEquals("Numero pecore errato", numeroPecore - 1, partita
				.getPecore().size());
	}

	@Test(expected = GameException.class)
	/**
	 * controllo che se il pastore si trova in una strada adiacente alla regione
	 * richiesta, ma in quella regione non ci sono pecore, solleva un'eccezione
	 * 
	 * @throws NoSheepInShireException
	 * @throws NoMoneyException
	 * @throws IllegalShireException
	 * @author Matteo Daverio
	 */
	public void noSheepInShireExceptionTest1() throws GameException {
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(5);
		}
		partita.getPastori().get(0).setPosizione(24);
		partita.abbatti(3,0);
	}
	
	@Test(expected = GameException.class)
	/**
	 * tutte le pecore eccetto la pecora 0 sono in posizione 5, tento abbattimento
	 * della pecora 0 in posizione 5
	 * 
	 * @throws NoSheepInShireException
	 * @throws NoMoneyException
	 * @throws IllegalShireException
	 * @author Matteo Daverio
	 */
	public void noSheepInShireExceptionTest2() throws GameException {
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(3);
		}
		partita.getPecore().get(0).setPosizione(8);
		partita.getPastori().get(0).setPosizione(24);
		partita.abbatti(3,0);
	}

	@Test(expected = GameException.class)
	/**
	 * controllo che se il pastore non ha denaro, e si trova in una regione con
	 * delle pecore abbattibili, ma è visto da altri pastori, solleva
	 * l'eccezione
	 * 
	 * @throws NoSheepInShireException
	 * @throws NoMoneyException
	 * @throws IllegalShireException
	 * @author Matteo Daverio
	 */
	public void noMoneyExceptionTest() throws GameException {
		partita.getPastori().get(0).setDenaro(0);
		partita.getPastori().get(0).setPosizione(24);
		partita.getPastori().get(1).setPosizione(25);
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(3);
		}
		partita.abbatti(3,0);
	}

	@Test(expected = GameException.class)
	/**
	 * controllo che solleva un eccezione tentare un abbattimento su una regione
	 * sprovvista di pecore
	 * 
	 * @throws NoSheepInShireException
	 * @throws NoMoneyException
	 * @throws IllegalShireException
	 * @author Matteo Daverio
	 */
	public void illegalShireExceptionTest() throws GameException {
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(5);
		}
		partita.getPastori().get(0).setPosizione(23);
		partita.abbatti(9,0);
	}

	@Test
	/**
	 * accoppiamento realizzabile
	 * 
	 * @throws IllegalShireException
	 * @throws CannotProcreateException
	 * @author Matteo Daverio
	 */
	public void accoppiaTest() throws GameException {
		int numeroPecore = partita.getPecore().size();
		partita.getPecore().get(0).setTipoPecora(Costanti.TIPO_PECORA_MONTONE);
		partita.getPecore().get(1).setTipoPecora(Costanti.TIPO_PECORA_PECORA);
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(5);
		}
		partita.getPastori().get(0).setPosizione(10);
		partita.accoppia(5);
		// accoppiamento con tutte le pecore nella stessa regione produce
		// un'agnello
		assertEquals("Errore accoppiamento", numeroPecore + 1, partita
				.getPecore().size());
		partita.getPecore().get(0).setPosizione(11);
		partita.getPecore().get(1).setPosizione(11);
		partita.getPastori().get(0).setPosizione(23);
		partita.accoppia(11);
		// accoppiamento con solo due pecore produce l'agnello
		assertEquals("Errore accoppiamento", numeroPecore + 2, partita
				.getPecore().size());
	}

	@Test(expected = GameException.class)
	/**
	 * solleva eccezione se nella regione non ci sono sia montoni sia pecore
	 * 
	 * @throws IllegalShireException
	 * @throws CannotProcreateException
	 * @author Matteo Daverio
	 */
	public void cannotProcreateException1() throws GameException {
		partita.getPecore().get(0).setTipoPecora(Costanti.TIPO_PECORA_MONTONE);
		partita.getPecore().get(1).setTipoPecora(Costanti.TIPO_PECORA_AGNELLO);
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(5);
		}
		partita.getPecore().get(0).setPosizione(11);
		partita.getPecore().get(1).setPosizione(11);
		partita.getPastori().get(0).setPosizione(23);
		partita.accoppia(11);
	}

	@Test(expected = GameException.class)
	/**
	 * solleva eccezione se non ci sono pecore nella regione scelta
	 * 
	 * @throws IllegalShireException
	 * @throws CannotProcreateException
	 * @author Matteo Daverio
	 */
	public void cannotProcreateException2() throws GameException {
		partita.getPecore().get(0).setTipoPecora(Costanti.TIPO_PECORA_MONTONE);
		partita.getPecore().get(1).setTipoPecora(Costanti.TIPO_PECORA_PECORA);
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(5);
		}

		partita.getPastori().get(0).setPosizione(23);
		partita.accoppia(11);
	}

	@Test(expected = GameException.class)
	/**
	 * solleva eccezione se il terreno inserito non è adiacente al pastore
	 * 
	 * @throws IllegalShireException
	 * @throws CannotProcreateException
	 * @author Matteo Daverio
	 */
	public void illegalShireException() throws GameException {
		partita.getPecore().get(0).setTipoPecora(Costanti.TIPO_PECORA_MONTONE);
		partita.getPecore().get(1).setTipoPecora(Costanti.TIPO_PECORA_PECORA);
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(5);
		}
		partita.getPastori().get(0).setPosizione(23);
		partita.accoppia(5);
	}

	@Test
	// TODO da finire
	public void compraTesseraTest() throws NoMoreCardsException,
			NoMoneyException, IllegalShireTypeException {

	}

	@Test(expected = GameException.class)
	/**
	 * attende eccezione se il pastore vuole acquistare delle tessere di una tipologia di cui non ce ne sono più
	 * 
	 * @throws NoMoreCardsException
	 * @throws NoMoneyException
	 * @throws IllegalShireTypeException
	 */
	public void noMoreCardsExceptionTest() throws GameException {
		partita.getPastori().get(0).setPosizione(0);
		for (int i = 0; i <= 6; i++) {
			partita.compraTessera(TipoTerreno.PRATERIA,0);
		}
	}

	@Test(expected = GameException.class)
	/**
	 * attende eccezione se il giocatore tenta di acquistare una tessera senza avere soldi
	 * 
	 * @throws NoMoreCardsException
	 * @throws NoMoneyException
	 * @throws IllegalShireTypeException
	 */
	public void noMoneyExceptionTest2() throws GameException {
		partita.getPastori().get(0).setPosizione(0);
		partita.getPastori().get(0).setDenaro(0);
		partita.compraTessera(TipoTerreno.PRATERIA,0);
		partita.compraTessera(TipoTerreno.PRATERIA,0);
	}

	@Test(expected = GameException.class)
	/**
	 * attende eccezione se un pastore tenta di acquistare una tipologia di terreno non adiacente a lui
	 * 
	 * @throws NoMoreCardsException
	 * @throws NoMoneyException
	 * @throws IllegalShireTypeException
	 * @author Matteo Daverio
	 */
	public void illegalShireTypeExceptionTest() throws GameException {
		partita.getPastori().get(0).setPosizione(0);
		partita.compraTessera(TipoTerreno.SABBIA,0);
	}

	/**
	 * override lancio dado di partita
	 * 
	 * @author Matteo
	 * 
	 */
	class MiaPartita extends Partita {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		int lancioDado() {
			return 6;
		}
	}

}
