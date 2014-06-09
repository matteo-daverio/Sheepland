package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore;

import java.io.IOException;

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
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient.CommandLine;
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

	/**
	 * riceve la partita e comunica allo schermo la posizione iniziale delle
	 * pecore
	 * 
	 * @param partita
	 * @author Valerio De Maria
	 */
	public void riceviPartita(Partita partita) {

		this.partita = partita;
		schermo.inizializzaPecore(partita.getPecore());
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
	 * aggiorno il movimento della pecora nera e lo comunico a schermo
	 * 
	 * @param posizione
	 * @author Valerio De Maria
	 */
	public void movimentoPecoraNera(int posizione) {

		partita.getPecoraNera().setPosizione(posizione);
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

		boolean autenticato=false;
		autenticato=client.effettuaLogin(nome, password);
		if(autenticato){
			client.attendiPartita();
		}
		return autenticato;
	}

	/**
	 * @author Valerio De Maria
	 */
	public void start() {

		if (protocollo.equals("rmi")) {
			System.out.println("istanzio un rmi");
			client = new ClientRMI(IP, Costanti.PORTA_RMI,this);
		} else
			client = new ClientSocket(IP, Costanti.PORTA_SOCKET,this);

		if (grafica.equals("gui")) {
			// TODO
		} else {
			schermo = new CommandLine();
		}

		schermo.start(this);

	}

}
