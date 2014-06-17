package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazione;

import static org.junit.Assert.*;

import java.io.IOException;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ServerApplication;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;

import org.junit.Before;
import org.junit.Test;

public class PartitaDueGiocatoriTest {

	Client giocatore1;
	Client giocatore2;

	@Before
	public void setUp() throws InterruptedException {
		Thread t1;
		AvvioServer avvioServer;
		avvioServer=new AvvioServer();
		t1=new Thread(avvioServer);
		t1.start();
		Thread.sleep(1000);
		giocatore1 =  new Client("rmi", "gui", "127.0.0.1");
		giocatore2 = new Client("socket", "gui", "127.0.0.1");

	}

	@Test
	public void testPartita() throws IOException {

		giocatore1.connetti();
		giocatore2.connetti();
		
		boolean autenticato = false;
		

		autenticato = giocatore1.login("matteo", "cfvff");
		assertTrue(autenticato);
		
		autenticato = giocatore2.login("matteo", "ciao");
		assertFalse(autenticato);

		autenticato = giocatore2.login("valerio", "bgbfbfg");
		assertTrue(autenticato);
		
		giocatore1.posizionamento(3);
	}

}

class AvvioServer implements Runnable {

	public void run() {
		ServerApplication.main(null);
	}

	
}

class Client {
	
	private String protocollo, tipo, ip;
	private ControllorePartitaClient controllore;
	
	Client(String a,String b, String c) {
		protocollo=a;
		tipo=b;
		ip=c;
	}
	
	public void connetti() {
		controllore = new ControllorePartitaClient(protocollo,tipo,ip);
		controllore.setTest();
		controllore.start();
	}
	
	public boolean login(String user, String psw) throws IOException {
		return controllore.logIn(user, psw);
	}
	
	public void posizionamento(int pos) {
		controllore.posizioneInserita(pos);
	}
	
	
	
}



