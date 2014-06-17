package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazione;

import static org.junit.Assert.*;

import java.io.IOException;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ServerApplication;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;

import org.junit.Before;
import org.junit.Test;

public class PartitaDueGiocatoriTest {

	ControllorePartitaClient giocatore1;
	ControllorePartitaClient giocatore2;

	@Before
	public void setUp() throws InterruptedException {
		Thread t1;
		AvvioServer avvioServer;
		avvioServer=new AvvioServer();
		t1=new Thread(avvioServer);
		t1.start();
		
		wait(1000);
		giocatore1 = new ControllorePartitaClient("rmi", "cl", "127.0.0.1");
		giocatore2 = new ControllorePartitaClient("socket", "cl", "127.0.0.1");

	}

	@Test
	public void testPartita() throws IOException {

		boolean autenticato = false;
		giocatore1.start();
		giocatore2.start();

		autenticato = giocatore1.logIn("matteo", "cfvff");
		assertTrue(autenticato);

		autenticato = giocatore2.logIn("valerio", "bgbfbfg");
		assertTrue(autenticato);

	}

}

class AvvioServer implements Runnable {

	public void run() {
		ServerApplication.main(null);
	}

	
}



