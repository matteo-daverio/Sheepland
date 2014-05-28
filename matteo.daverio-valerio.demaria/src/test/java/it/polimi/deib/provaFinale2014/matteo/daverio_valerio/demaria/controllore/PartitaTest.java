package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore;

import static org.junit.Assert.*;


import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;
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

	@Test(expected = NoMovementException.class)
	// testo l'eccezione NoMovementException
	public void verificaNoMovementException() throws NoMovementException,
			NoMoneyException, InvalidMovementException {
		partita.getPastori().get(0).setPosizione(30);
		partita.muoviPastore(30);
	}

	@Test(expected = NoMoneyException.class)
	// testo l'eccezione NoMoneyException
	public void verificaMoneyException() throws NoMovementException,
			NoMoneyException, InvalidMovementException {
		partita.getPastori().get(0).setPosizione(1);
		partita.getPastori().get(0).setDenaro(0);
		partita.muoviPastore(17);
		// TODO controlla il movimento pastore in 17, ogni tanto da errore
	}

	@Test(expected = InvalidMovementException.class)
	// testo l'eccezione InvalidMovementException per strada recintata
	public void verificaInvalidMovementException1() throws NoMovementException,
			NoMoneyException, InvalidMovementException {
		partita.getPastori().get(0).setPosizione(30);
		partita.getStrade().get(17).aggiungiRecinto();
		partita.muoviPastore(17);
	}

	@Test(expected = InvalidMovementException.class)
	// testo l'eccezione InvalidMovementException per strada occupata
	public void verificaInvalidMovementException2() throws NoMovementException,
			NoMoneyException, InvalidMovementException {
		partita.getPastori().get(0).setPosizione(30);
		partita.aggiungiPastore("Matteo", 2);
		partita.getPastori().get(1).setPosizione(17);
		partita.muoviPastore(17);
	}

	@Test
	// movimento pastore con strada sgombra e limitrofa
	public void muoviPastoreTest1() throws NoMovementException,
			NoMoneyException, InvalidMovementException {
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
	// movimento pastore con strada sgombra e non limitrofa con soldi
	public void muoviPastoreTest2() throws NoMovementException,
			NoMoneyException, InvalidMovementException {
		partita.getPastori().get(0).setPosizione(30);
		partita.getPastori().get(0).setDenaro(20);
		assertFalse(partita.getStrade().get(25).recintata());
		partita.muoviPastore(25);
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
		for (Pecora pecora : partita.getPecore())
			pecora.setPosizione(13);
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
	public void abbattiTest() throws NoSheepInShireException, NoMoneyException,
			IllegalShireException {
		int numeroPecore = partita.getPecore().size();
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(5);
		}
		partita.getPastori().get(0).setPosizione(10);
		partita.abbatti(5);
		assertEquals("Numero pecore errato", numeroPecore - 1, partita
				.getPecore().size());
	}

	@Test(expected = NoSheepInShireException.class)
	/**
	 * controllo che se il pastore si trova in una strada adiacente alla regione
	 * richiesta, ma in quella regione non ci sono pecore, solleva un'eccezione
	 * 
	 * @throws NoSheepInShireException
	 * @throws NoMoneyException
	 * @throws IllegalShireException
	 * @author Matteo Daverio
	 */
	public void noSheepInShireExceptionTest() throws NoSheepInShireException,
			NoMoneyException, IllegalShireException {
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(5);
		}
		partita.getPastori().get(0).setPosizione(24);
		partita.abbatti(3);
	}

	@Test(expected = NoMoneyException.class)
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
	public void noMoneyExceptionTest() throws NoSheepInShireException,
			NoMoneyException, IllegalShireException {
		partita.getPastori().get(0).setDenaro(0);
		partita.getPastori().get(0).setPosizione(24);
		partita.getPastori().get(1).setPosizione(25);
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(3);
		}
		partita.abbatti(3);
	}

	@Test(expected = IllegalShireException.class)
	/**
	 * controllo che solleva un eccezione tentare un abbattimento su una regione
	 * sprovvista di pecore
	 * 
	 * @throws NoSheepInShireException
	 * @throws NoMoneyException
	 * @throws IllegalShireException
	 * @author Matteo Daverio
	 */
	public void illegalShireExceptionTest() throws NoSheepInShireException,
			NoMoneyException, IllegalShireException {
		partita.getPastori().get(0).setPosizione(23);
		partita.abbatti(9);
	}

	@Test
	/**
	 * accoppiamento realizzabile
	 * 
	 * @throws IllegalShireException
	 * @throws CannotProcreateException
	 * @author Matteo Daverio
	 */
	public void accoppiaTest() throws IllegalShireException,
			CannotProcreateException {
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

	@Test(expected = CannotProcreateException.class)
	/**
	 * solleva eccezione se nella regione non ci sono sia montoni sia pecore
	 * 
	 * @throws IllegalShireException
	 * @throws CannotProcreateException
	 * @author Matteo Daverio
	 */
	public void cannotProcreateException1() throws IllegalShireException,
			CannotProcreateException {
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

	@Test(expected = CannotProcreateException.class)
	/**
	 * solleva eccezione se non ci sono pecore nella regione scelta
	 * 
	 * @throws IllegalShireException
	 * @throws CannotProcreateException
	 * @author Matteo Daverio
	 */
	public void cannotProcreateException2() throws IllegalShireException,
			CannotProcreateException {
		partita.getPecore().get(0).setTipoPecora(Costanti.TIPO_PECORA_MONTONE);
		partita.getPecore().get(1).setTipoPecora(Costanti.TIPO_PECORA_PECORA);
		for (Pecora pecora : partita.getPecore()) {
			pecora.setPosizione(5);
		}

		partita.getPastori().get(0).setPosizione(23);
		partita.accoppia(11);
	}

	@Test(expected = IllegalShireException.class)
	/**
	 * solleva eccezione se il terreno inserito non è adiacente al pastore
	 * 
	 * @throws IllegalShireException
	 * @throws CannotProcreateException
	 * @author Matteo Daverio
	 */
	public void illegalShireException() throws IllegalShireException,
			CannotProcreateException {
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

	@Test(expected = NoMoreCardsException.class)
	/**
	 * attende eccezione se il pastore vuole acquistare delle tessere di una tipologia di cui non ce ne sono più
	 * 
	 * @throws NoMoreCardsException
	 * @throws NoMoneyException
	 * @throws IllegalShireTypeException
	 */
	public void noMoreCardsExceptionTest() throws NoMoreCardsException,
			NoMoneyException, IllegalShireTypeException {
		partita.getPastori().get(0).setPosizione(0);
		for (int i = 0; i <= 6; i++)
			partita.compraTessera(TipoTerreno.PRATERIA);
	}

	@Test(expected = NoMoneyException.class)
	/**
	 * attende eccezione se il giocatore tenta di acquistare una tessera senza avere soldi
	 * 
	 * @throws NoMoreCardsException
	 * @throws NoMoneyException
	 * @throws IllegalShireTypeException
	 */
	public void noMoneyExceptionTest2() throws NoMoreCardsException,
			NoMoneyException, IllegalShireTypeException {
		partita.getPastori().get(0).setPosizione(0);
		partita.getPastori().get(0).setDenaro(0);
		partita.compraTessera(TipoTerreno.PRATERIA);
		partita.compraTessera(TipoTerreno.PRATERIA);
	}

	@Test(expected = IllegalShireTypeException.class)
	/**
	 * attende eccezione se un pastore tenta di acquistare una tipologia di terreno non adiacente a lui
	 * 
	 * @throws NoMoreCardsException
	 * @throws NoMoneyException
	 * @throws IllegalShireTypeException
	 * @author Matteo Daverio
	 */
	public void illegalShireTypeExceptionTest() throws NoMoreCardsException,
			NoMoneyException, IllegalShireTypeException {
		partita.getPastori().get(0).setPosizione(0);
		partita.compraTessera(TipoTerreno.SABBIA);
	}

	/**
	 * override lancio dado di partita
	 * 
	 * @author Matteo
	 *
	 */
	class MiaPartita extends Partita {
		@Override
		int lancioDado() {
			return 6;
		}
	}

	

}
