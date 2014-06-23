package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaComunicazioneToServer;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Abbatti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Accoppia;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.CompraTessera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.MuoviPastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.MuoviPecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.MuoviPecoraNera;
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
	private List<Integer> stradeDisponibili = new ArrayList<Integer>();
	private List<MosseEnum> mosseDisponibili = new ArrayList<MosseEnum>();
	private boolean test = false;
	private static final Logger LOG = Logger.getLogger(ClientRMI.class
			.getName());

	/**
	 * COSTRUTTORE
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

		if (!test) {
			schermo.start();
		} else {
			schermo.setTest();
			schermo.start();
		}

	}

	/**
	 * aggiorna l'oggetto schermo
	 * 
	 * @param schermo
	 * @author Matteo Daverio
	 */
	public void setSchermo(InterfacciaGrafica schermo) {
		this.schermo = schermo;
	}

	// / METODI CHE ESEGUONO I METODI DELL'INTERFACCIA GRAFICA //////

	/**
	 * avverte il client che non c'è più una connessione con il server
	 * 
	 * @author Valerio De Maria
	 */
	public void segnalaDisconnessione() {
		schermo.segnalaDisconnessione();
	}

	/**
	 * invia alla grafica i dati aggiornati dopo che il client si è riconnesso
	 * 
	 * @param pecore
	 * @param posPecoraNera
	 * @param posLupo
	 * @param pastori
	 * @author Valerio De Maria
	 */
	public void aggiornamentoPostDisconnessione(List<Pecora> pecore,
			int posPecoraNera, int posLupo, List<Pastore> pastori) {
		schermo.aggiornamentoPostDisconnessione(pecore, posPecoraNera, posLupo,
				pastori);
	}

	/**
	 * 
	 * @param nomi
	 * @author Valerio De Maria
	 */
	public void riceviNomiGiocatori(List<String> nomi) {
		schermo.nomiGiocatori(nomi);
	}

	/**
	 * 
	 * @param soldi
	 * @author Valerio De Maria
	 */
	public void riceviSoldiPastori(List<Integer> soldi) {
		schermo.soldiPastori(soldi);
	}

	/**
	 * 
	 * @param tesseraIniziale
	 * @author Valerio De Maria
	 */
	public void riceviTesseraIniziale(Tessera tesseraIniziale) {
		schermo.tesseraIniziale(tesseraIniziale);
	}

	/**
	 * 
	 * @param pecore
	 * @author Valerio De Maria
	 */
	public void settaPecore(List<Pecora> pecore) {
		schermo.settaPecore(pecore);
	}

	/**
	 * 
	 * @param strade
	 * @author Valerio De Maria
	 */
	public void riceviStrade(List<Strada> strade) {
		schermo.riceviStrade(strade);
	}

	/**
	 * dice al client dove ha posizionato il pastore l'avversario
	 * 
	 * @param turno
	 * @param pastore
	 * @param posizione
	 * @author Valerio De Maria
	 */
	public void aggiornamentoPosizionePastore(int turno, int pastore,
			int posizione) {
		schermo.aggiornamentoPosizionePastore(turno, pastore, posizione);
	}

	/**
	 * invia alla grafica la richiesta di posizionamento del pastore
	 * 
	 * @param stradeDisponibili
	 * @author Valerio De Maria
	 */
	public void richiestaPosizionamentoPastore(List<Integer> stradeDisponibili) {

		// aggiorno la mia lista locale di strade diponibili
		this.stradeDisponibili.clear();
		for (Integer x : stradeDisponibili) {
			this.stradeDisponibili.add(x);
		}

		schermo.richiestaPosizionamentoPastore();
	}

	/**
	 * dice alla grafica che è cambiato il turno e gli dice chi è il giocatore
	 * del turno attuale
	 * 
	 * @param giocatore
	 * @param pecore
	 * @author Valerio De Maria
	 */
	public void cambioTurno(String giocatore, List<Pecora> pecore) {
		schermo.cambioTurno(giocatore, pecore);
	}

	/**
	 * dice alla grafica che è il turno del giocatore
	 * 
	 * @param pecore
	 * @param turno
	 * @author Valerio De Maria
	 */
	public void iniziaTurno(List<Pecora> pecore, int turno) {

		schermo.iniziaTurno(pecore, turno);
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

	/**
	 * comunico che il lupo si è mosso
	 * 
	 * @param posizione
	 * @author Valerio De Maria
	 */
	public void movimentoLupo(int posizione) {
		schermo.muoviLupo(posizione);
	}

	/**
	 * aggiorno la grafica riguardo al movimento di un pastore avversario
	 * 
	 * @param posizione
	 * @param giocatore
	 * @param pastore
	 * @author Valerio De Maria
	 */
	public void movimentoPastore(int posizione, String giocatore, int pastore) {
		schermo.movimentoPastore(posizione, giocatore, pastore);
	}

	/**
	 * aggiorno la grafica riguardo all'acquisto di una tessera da parte di un
	 * avversario
	 * 
	 * @param terreno
	 * @param giocatore
	 * @param pastore
	 * @author Valerio De Maria
	 */
	public void acquistoTessera(TipoTerreno terreno, String giocatore,
			int pastore) {
		schermo.acquistoTessera(terreno, giocatore, pastore);
	}

	/**
	 * aggiorno la grafica riguardo al movimento di una pecora da parte di un
	 * avversario
	 * 
	 * @param pecora
	 * @param strada
	 * @param giocatore
	 * @param pastore
	 */
	public void movimentoPecora(int pecora, int strada, String giocatore,
			int pastore) {
		schermo.movimentoPecora(pecora, strada, giocatore, pastore);
	}

	/**
	 * aggiorno la grafica riguardo all'abbattimento fatto da un giocatore
	 * avversario
	 * 
	 * @param regione
	 * @param pecora
	 * @param giocatore
	 * @param pastore
	 * @author Valerio De Maria
	 */
	public void abbattimento(int regione, int pecora, String giocatore,
			int pastore) {
		schermo.abbattimento(regione, pecora, giocatore, pastore);
	}

	/**
	 * aggiorno la grafica riguardo all'accoppiamento fatto da un giocatore
	 * avversario
	 * 
	 * @param regione
	 * @param giocatore
	 * @param pastore
	 * @author Valerio De Maria
	 */
	public void accoppiamento(int regione, String giocatore, int pastore) {
		schermo.accoppiamento(regione, giocatore, pastore);
	}

	/**
	 * aggiorno la grafica riguardo allo spostamento della pecora nera fatto da
	 * un giocatore avversario
	 * 
	 * @param strada
	 * @param giocatore
	 * @param pastore
	 */
	public void spostamentoPecoraNera(int strada, String giocatore, int pastore) {
		schermo.spostamentoPecoraNera(strada, giocatore, pastore);
	}

	/**
	 * do alla grafica una serie di mosse disponibili e chiedo di effettuare una
	 * mossa
	 * 
	 * @param mosseDisponibili
	 * @author Valerio De Maria
	 */
	public void richiestaMossa(List<MosseEnum> mosseDisponibili) {

		// aggiorno la mia lista locale di mosse disponibili
		for (MosseEnum x : mosseDisponibili) {

			this.mosseDisponibili.add(x);

		}

		schermo.richiestaMossa(mosseDisponibili);
	}

	/**
	 * comunico che la mossa fatta era sbagliata
	 * 
	 * @author Valerio De Maria
	 */
	public void mossaSbagliata() {
		schermo.mossaSbagliata();
	}

	/**
	 * comunico che la mossa fatta era corretta
	 * 
	 * @author Valerio De Maria
	 */
	public void mossaCorretta() {
		schermo.mossaCorretta();
	}

	/**
	 * @author Valerio De Maria
	 */
	public void faseFinale() {
		schermo.faseFinale();
	}

	/**
	 * 
	 * @param punteggiFinali
	 * @param nomi
	 * @author Valerio De Maria
	 */
	public void punteggiFinali(List<Integer> punteggiFinali, List<String> nomi) {
		schermo.punteggiFinali(punteggiFinali, nomi);
	}

	/**
	 * 
	 * @param denaroPastori
	 * @author Valerio De Maria
	 */
	public void comunicaDenaro(List<Integer> denaroPastori) {
		schermo.comunicaDenaro(denaroPastori);
	}

	/**
	 * 
	 * @param recinti
	 * @author Valerio De Maria
	 */
	public void comunicaNumeroRecinti(int recinti) {
		schermo.comunicaNumeroRecinti(recinti);
	}

	/**
	 * 
	 * @param nome
	 * @author Valerio De Maria
	 */
	public void disconnessione(String nome) {
		schermo.disconnessione(nome);
	}

	/**
	 * 
	 * @param nome
	 * @author Valerio De Maria
	 */
	public void riconnessione(String nome) {
		schermo.riconnessione(nome);
	}

	/**
	 * 
	 * @param nome
	 * @author Valerio De Maria
	 */
	public void esclusione(String nome) {
		schermo.esclusione(nome);
	}

	// METODI ESEGUITI DALLA GRAFICA

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

	/**
	 * la grafica invia la posizione al controllore client
	 * 
	 * @param posizione
	 * @author Valerio De Maria
	 */
	public void posizioneInserita(int posizione) {

		boolean pastorePosizionato = false;

		for (Integer x : this.stradeDisponibili) {
			if (posizione == x) {
				pastorePosizionato = true;
			}
		}

		if (pastorePosizionato) {
			schermo.posizionamentoPastoreCorretto();
			client.inviaPosizionePastore(posizione);
		} else {
			schermo.posizionamentoPastoreErrato();
			schermo.richiestaPosizionamentoPastore();
		}

	}

	/**
	 * 
	 * @param posizione
	 * @param pastoreTurno
	 * @author Valerio De Maria
	 */
	public void muoviPastore(int posizione, int pastoreTurno) {
		client.inviaMossa(new MuoviPastore(posizione), pastoreTurno);
	}

	/**
	 * 
	 * @param strada
	 * @param pecoraScelta
	 * @param pastoreTurno
	 * @author Valerio De Maria
	 */
	public void muoviPecora(int strada, int pecoraScelta, int pastoreTurno) {
		client.inviaMossa(new MuoviPecora(strada, pecoraScelta), pastoreTurno);
	}

	/**
	 * 
	 * @param strada
	 * @param pastoreTurno
	 * @author Valerio De Maria
	 */
	public void spostaPecoraNera(int strada, int pastoreTurno) {
		client.inviaMossa(new MuoviPecoraNera(strada), pastoreTurno);
	}

	/**
	 * 
	 * @param tipoTerreno
	 * @param pastoreTurno
	 * @author Valerio De Maria
	 */
	public void compraTessera(int tipoTerreno, int pastoreTurno) {

		switch (tipoTerreno) {
		case 1:
			client.inviaMossa(new CompraTessera(TipoTerreno.ACQUA),
					pastoreTurno);
			break;
		case 2:
			client.inviaMossa(new CompraTessera(TipoTerreno.FORESTA),
					pastoreTurno);
			break;
		case 3:
			client.inviaMossa(new CompraTessera(TipoTerreno.GRANO),
					pastoreTurno);
			break;
		case 4:
			client.inviaMossa(new CompraTessera(TipoTerreno.PRATERIA),
					pastoreTurno);
			break;
		case 5:
			client.inviaMossa(new CompraTessera(TipoTerreno.ROCCIA),
					pastoreTurno);
			break;
		case 6:
			client.inviaMossa(new CompraTessera(TipoTerreno.SABBIA),
					pastoreTurno);
			break;

		default:
			break;
		}

	}

	/**
	 * 
	 * @param regione
	 * @param pScelta
	 * @param pastoreTurno
	 * @author Valerio De Maria
	 */
	public void abbatti(int regione, int pScelta, int pastoreTurno) {
		client.inviaMossa(new Abbatti(regione, pScelta), pastoreTurno);
	}

	/**
	 * 
	 * @param regione
	 * @param pastoreTurno
	 * @author Valerio De Maria
	 */
	public void accoppia(int regione, int pastoreTurno) {
		client.inviaMossa(new Accoppia(regione), pastoreTurno);
	}

	/**
	 * @author Valerio De Maria
	 */
	public void riceviAggiornamenti() {
		try {
			client.riceviAggiornamenti();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Errore connesione", e);
		} catch (GameException e) {
			LOG.log(Level.SEVERE, "Errore connesione", e);
		}
	}

	/**
	 * @author Matteo Daverio
	 */
	public void setTest() {
		test = true;
	}

}
