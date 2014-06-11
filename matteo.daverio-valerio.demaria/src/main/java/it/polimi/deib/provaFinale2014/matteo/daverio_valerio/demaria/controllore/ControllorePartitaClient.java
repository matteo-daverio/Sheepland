package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore;

import java.io.IOException;
import java.util.List;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaComunicazioneClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
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
	private InterfacciaComunicazioneClient client;
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
	
	public void aggiornamentoPosizionePastore(int turno,int pastore,int posizione){
		schermo.aggiornamentoPosizionePastore(turno, pastore, posizione);
	}

	public int posizionamentoPastore(List<Integer> stradeDisponibili){
		
		boolean sceltaCorretta=false;
		
		int scelta= schermo.posizionaPastore();
		
		for(Integer x:stradeDisponibili){
			if(scelta==x.intValue()){
				schermo.posizionamentoPastoreCorretto();
				return scelta;
			}
		}
		
		do{
		
		schermo.posizionamentoPastoreErrato();
		scelta=schermo.posizionaPastore();
		
		for(Integer x:stradeDisponibili){
			if(scelta==x.intValue()){
				schermo.posizionamentoPastoreCorretto();
				sceltaCorretta=true;
			}
		}
		
		}while (!sceltaCorretta);
		
		return scelta;
	}

	/*
	 * public int posizionaPastore(){ posizione=schermo.posizionaPastore();
	 * if(partita.stradaOccupata(posizione)){ return false; }
	 * 
	 * partita.getPastori().get(partita.getTurno()-1).setPosizione(posizione);
	 * 
	 * client.inviaPosizionePastore();
	 * 
	 * return true;
	 * 
	 * }
	 */
	public void iniziaTurno() {

		schermo.iniziaTurno();
	}

	/**
	 * aggiorno il movimento della pecora nera e lo comunico a schermo
	 * 
	 * @param posizione
	 * @author Valerio De Maria
	 */
	public void movimentoPecoraNera(int posizione) {

		schermo.muoviPecoraNera(posizione);
	}

	/**
	 * metodo che la grafica chiama per eseguire una mossa
	 * 
	 * @return
	 * @author Valerio De Maria
	 */
	public boolean eseguiMossa() {
		// TODO
		return true;
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
		if (autenticato) {
			client.attendiPartita();
		}
		return autenticato;
	}

	/**
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
