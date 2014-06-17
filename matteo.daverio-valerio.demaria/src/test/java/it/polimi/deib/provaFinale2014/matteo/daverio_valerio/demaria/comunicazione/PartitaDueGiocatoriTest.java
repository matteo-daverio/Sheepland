package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazione;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ServerApplication;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient.CommandLine;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient.GuiImpl;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient.InterfacciaGrafica;

import org.junit.Before;
import org.junit.Test;

public class PartitaDueGiocatoriTest {

	Client giocatore1;
	Client giocatore2;
	
	Comunicazione client1, client2;

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
	
	public void connetti(){
		InterfacciaGrafica schermo;
		controllore=new ControllorePartitaClient(protocollo, tipo, ip);
		controllore.setTest();
		schermo=new Comunicazione(controllore);
		controllore.start();
		controllore.setSchermo(schermo);
	}
	
	public boolean login(String user,String pass) throws IOException{
		return controllore.logIn(user, pass);
	}
}

class Comunicazione implements InterfacciaGrafica{

	ControllorePartitaClient controllorePartita;
	
	public Comunicazione(ControllorePartitaClient controllore){
		controllorePartita=controllore;
	}
	
	public void start() {
		
	}

	public void nomiGiocatori(List<String> nomi) {
		// TODO Auto-generated method stub
		
	}

	public void soldiPastori(List<Integer> soldi) {
		// TODO Auto-generated method stub
		
	}

	public void tesseraIniziale(Tessera tesseraIniziale) {
		// TODO Auto-generated method stub
		
	}

	public void settaPecore(List<Pecora> pecore) {
		// TODO Auto-generated method stub
		
	}

	public void riceviStrade(List<Strada> strade) {
		// TODO Auto-generated method stub
		
	}

	public void richiestaPosizionamentoPastore() {
		// TODO Auto-generated method stub
		
	}

	public void richiestaMossa(List<MosseEnum> mosseDisponibili) {
		// TODO Auto-generated method stub
		
	}

	public void posizionamentoPastoreCorretto() {
		// TODO Auto-generated method stub
		
	}

	public void posizionamentoPastoreErrato() {
		// TODO Auto-generated method stub
		
	}

	public void aggiornamentoPosizionePastore(int turno, int pastore,
			int posizione) {
		// TODO Auto-generated method stub
		
	}

	public void movimentoPastore(int posizione, String giocatore, int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void acquistoTessera(TipoTerreno terreno, String giocatore,
			int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void movimentoPecora(int pecora, int strada, String giocatore,
			int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void abbattimento(int regione, int pecora, String giocatore,
			int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void accoppiamento(int regione, String giocatore, int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void spostamentoPecoraNera(int strada, String giocatore, int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void iniziaTurno(List<Pecora> pecore, int turno) {
		// TODO Auto-generated method stub
		
	}

	public void cambioTurno(String giocatore, List<Pecora> pecore) {
		// TODO Auto-generated method stub
		
	}

	public void muoviPecoraNera(int posizione) {
		// TODO Auto-generated method stub
		
	}

	public void muoviLupo(int posizione) {
		// TODO Auto-generated method stub
		
	}

	public void mossaSbagliata() {
		// TODO Auto-generated method stub
		
	}

	public void mossaCorretta() {
		// TODO Auto-generated method stub
		
	}

	public void faseFinale() {
		// TODO Auto-generated method stub
		
	}

	public void punteggiFinali(List<Integer> punteggiFinali, List<String> nomi) {
		// TODO Auto-generated method stub
		
	}

	public void comunicaDenaro(List<Integer> denaroPastori) {
		// TODO Auto-generated method stub
		
	}

	public void comunicaNumeroRecinti(int recinti) {
		// TODO Auto-generated method stub
		
	}

	public void aggiornamentoPostDisconnessione(List<Pecora> pecore,
			int posPecoraNera, int posLupo, List<Pastore> pastori) {
		// TODO Auto-generated method stub
		
	}

	public void segnalaDisconnessione() {
		// TODO Auto-generated method stub
		
	}

	public void disconnessione(String nome) {
		// TODO Auto-generated method stub
		
	}

	public void riconnessione(String nome) {
		// TODO Auto-generated method stub
		
	}

	public void esclusione(String nome) {
		// TODO Auto-generated method stub
		
	}

	public void setTest() {
		// TODO Auto-generated method stub
		
	}
	
}



