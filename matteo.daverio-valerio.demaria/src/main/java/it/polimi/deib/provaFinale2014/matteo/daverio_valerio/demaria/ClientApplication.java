package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;

import java.util.Scanner;

/**
 * classe iniziale del client
 * @author Valerio De Maria
 *
 */
public class ClientApplication {

	static String grafica = null;
	static String protocollo = null;
	static String IP = null;
	static Scanner in = new Scanner(System.in);

	/**
	 * MAIN del client
	 * 
	 * @param args
	 * @throws NoMoreCardsException
	 * @throws NoMoneyException
	 * @throws IllegalShireTypeException
	 * @throws NoSheepInShireException
	 * @throws IllegalShireException
	 * @throws CannotProcreateException
	 * @throws IllegalStreetException
	 * @author Valerio De Maria
	 */
	public static void main(String[] args) throws NoMoreCardsException,
			NoMoneyException, IllegalShireTypeException,
			NoSheepInShireException, IllegalShireException,
			CannotProcreateException, IllegalStreetException {

		protocollo = richiestaProtocolloComunicazione();

		IP = richiestaIP();
		
		grafica = richiestaGrafica();
	

		ControllorePartitaClient control = new ControllorePartitaClient(
				protocollo, grafica, IP);

		control.start();

	}

	/**
	 * chiedo il tipo di grafica che si vuole
	 * 
	 * @return
	 * @author Valerio De Maria
	 */
	private static String richiestaGrafica() {
		String risposta;
		System.out.println("Scegliere un il metodo di grafica ( gui / cl )");
		do {
			risposta = in.nextLine();
		} while (!risposta.equals("gui") && !risposta.equals("cl"));
		return risposta;
	}

	/**
	 * chiedo l'IP del server del gioco
	 * 
	 * @return
	 * @author Valerio De Maria
	 */
	private static String richiestaIP() {
		String IP;
		System.out.println("Inserire IP server");
		do {
			IP = in.nextLine();
		} while (IP.equals(""));
		return IP;
	}

	/**
	 * chiedo il tipo di comunicazione desiderata
	 * 
	 * @return
	 * @author Valerio De Maria
	 */
	public static String richiestaProtocolloComunicazione() {
		String risposta;
		System.out
				.println("Inserire il protocollo di comunicazione con il Server (rmi / socket)");
		do {
			risposta = in.nextLine();
		} while (!risposta.equals("rmi") && !risposta.equals("socket"));
		return risposta;
	}

}
