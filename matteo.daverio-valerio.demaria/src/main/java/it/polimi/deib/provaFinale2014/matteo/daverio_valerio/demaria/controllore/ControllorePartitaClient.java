package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore;

import java.io.IOException;
import java.util.List;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaComunicazioneToServer;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Abbatti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Accoppia;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.MuoviPastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.MuoviPecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient.CommandLine;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient.GuiImpl;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient.InterfacciaGrafica;

/**
 * unisce la parte di comunicazione con la grafica del client
 * 
 * @author Valerio De Maria
 * 
 */
public class ControllorePartitaClient {

	private final String protocollo, grafica, IP;
	private InterfacciaComunicazioneToServer client;
	private InterfacciaGrafica schermo;
	private Partita partita;

	/**
	 * costruttore
	 * 
	 * @param protocollo
	 * @param grafica
	 * @param IP
	 * @author Valerio De Maria
	 */

	public ControllorePartitaClient(String protocollo, String grafica, String IP) {

		this.protocollo = protocollo;
		this.grafica = grafica;
		this.IP = IP;

	}

	public void riceviNomiGiocatori(List<String> nomi) {
		schermo.nomiGiocatori(nomi);
	}

	public void riceviSoldiPastori(List<Integer> soldi) {
		schermo.soldiPastori(soldi);
	}

	public void riceviTessereInizialiPastori(List<Tessera> tessereIniziali) {
		schermo.tessereInizialiPastori(tessereIniziali);
	}

	public void settaPecore(List<Pecora> pecore) {
		schermo.settaPecore(pecore);
	}

	public void aggiornamentoPosizionePastore(int turno, int pastore,
			int posizione) {
		schermo.aggiornamentoPosizionePastore(turno, pastore, posizione);
	}

	public int posizionamentoPastore(List<Integer> stradeDisponibili) {

		boolean sceltaCorretta = false;

		int scelta = schermo.posizionaPastore();

		for (Integer x : stradeDisponibili) {
			if (scelta == x.intValue()) {
				schermo.posizionamentoPastoreCorretto();
				return scelta;
			}
		}

		do {

			schermo.posizionamentoPastoreErrato();
			scelta = schermo.posizionaPastore();

			for (Integer x : stradeDisponibili) {
				if (scelta == x.intValue()) {
					schermo.posizionamentoPastoreCorretto();
					sceltaCorretta = true;
				}
			}

		} while (!sceltaCorretta);

		return scelta;
	}

	public void cambioTurno(String giocatore) {
		schermo.cambioTurno(giocatore);
	}

	public void iniziaTurno() {

		schermo.iniziaTurno();
	}

	/**
	 * comunico che la pecora nera si è mossa
	 * 
	 * @param posizione
	 * @author Valerio De Maria
	 */
	public void movimentoPecoraNera(int posizione) {

		schermo.muoviPecoraNera(posizione);
	}
	
	public void movimentoPastore(int posizione,String giocatore,int pastore){
		schermo.movimentoPastore(posizione,giocatore,pastore);
	}
	
	public void acquistoTessera(TipoTerreno terreno,String giocatore,int pastore){
		schermo.acquistoTessera(terreno,giocatore,pastore);
	}
	
	public void movimentoPecora(int pecora, int strada,String giocatore, int pastore){
		schermo.movimentoPecora(pecora,strada,giocatore,pastore);
	}
	
	public void abbattimento(int regione, int pecora,String giocatore, int pastore){
		schermo.abbattimento(regione,pecora,giocatore,pastore);
	}
	public void accoppiamento(int regione,String giocatore, int pastore){
		schermo.accoppiamento(regione,giocatore,pastore);
	}

	/**
	 * metodo che la grafica chiama per eseguire una mossa
	 * 
	 * @return
	 * @author Valerio De Maria
	 */
	public Mossa richiediMossa(List<MosseEnum> mosseDisponibili) {
		
		int mossaScelta;
		
		schermo.visualizzaMosseDisponibili(mosseDisponibili);
		
		mossaScelta=schermo.chiediMossa(mosseDisponibili.size()-1);
		
		switch(mosseDisponibili.get(mossaScelta)){
		
		case MUOVI_PASTORE:
			int posizione;
			posizione=schermo.scegliStrada();
			return new MuoviPastore(posizione);
		
		case MUOVI_PECORA:
			int pecoraScelta;
			int strada;
			pecoraScelta=schermo.scegliPecora();
			strada=schermo.scegliStrada();
			return new MuoviPecora(strada,pecoraScelta);
			
		case COMPRA_TESSERA:
			//TODO
			break;
		case ABBATTI:
			int regione;
			int pScelta;
			pScelta=schermo.scegliPecora();
			regione=schermo.scegliRegione();
			return new Abbatti(regione,pScelta);
		
		case ACCOPPIA:
			int reg;
			reg=schermo.scegliRegione();
			return new Accoppia(reg);
		
		default:
		break;
		}
		
		return new MuoviPastore(9);
	}
	
	public int selezionaPastore(int primo, int secondo){
		
		return schermo.selezionaPastore(primo,secondo);
	}

	/**
	 * tenta di effettuare il login con il nome e password passati ritorna TRUE
	 * in caso di autenticazione avvenuta con successo ritorna FALSE in caso di
	 * password sbagliata o errori di connessione
	 * 
	 * @param nome
	 * @param password
	 * @return
	 * @throws IOException
	 * @author Valerio De Maria
	 */
	public boolean logIn(String nome, String password) throws IOException {

		boolean autenticato = false;
		
		autenticato = client.effettuaLogin(nome, password);
		
		return autenticato;
	}
	
	public void riceviAggiornamenti(){
		try {
			client.riceviAggiornamenti();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * metodo principale del controllore partita
	 * 
	 * @author Valerio De Maria
	 */
	public void start() {

		if (protocollo.equals("rmi")) {
			client = new ClientRMI(IP, Costanti.PORTA_RMI, this);
		} else
			client = new ClientSocket(IP, Costanti.PORTA_SOCKET, this);

		if (grafica.equals("gui")) {
			schermo = new GuiImpl(this);
		} else {
			schermo = new CommandLine(this);
		}

		schermo.start();

	}

}
