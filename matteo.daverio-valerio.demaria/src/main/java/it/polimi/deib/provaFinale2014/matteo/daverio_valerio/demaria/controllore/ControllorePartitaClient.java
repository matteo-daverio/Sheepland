package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore;

import java.io.IOException;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaComunicazioneClient;
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
	 * tenta di effettuare il login con il nome e password passati
	 * ritorna TRUE in caso di autenticazione avvenuta con successo
	 * ritorna FALSE in caso di password sbagliata o errori di connessione
	 * @param nome
	 * @param password
	 * @return
	 * @throws IOException
	 * @author Valerio De Maria
	 */
	public boolean logIn(String nome,String password) throws IOException{
		
		return client.effettuaLogin(nome, password);
		
	}

	/**
	 * @author Valerio De Maria
	 */
	public void start() {

		if (protocollo.equals("rmi")) {
			System.out.println("istanzio un rmi");
			client = new ClientRMI(IP, Costanti.PORTA_RMI);
		} else
			client = new ClientSocket(IP, Costanti.PORTA_SOCKET);

		if (grafica.equals("gui")) {
			// TODO
		} else {
			schermo=new CommandLine();
		}
		
		schermo.start(this);

	}

}
