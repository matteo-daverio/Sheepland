package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;

import java.io.IOException;
import java.util.Scanner;

public class ClientApplication {

	static String risposta = null;
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) throws NoMoreCardsException, NoMoneyException, IllegalShireTypeException, NoSheepInShireException, IllegalShireException, CannotProcreateException, IllegalStreetException {

		String protocollo;
		protocollo = richiestaProtocolloComunicazione();

		//String grafica;
		//grafica = richiestaGrafica();

		// TODO avvio istanza di grafica

		avvioClient(protocollo);

	}

	private static String richiestaGrafica() {
		System.out.println("Scegliere un il metodo di grafica ( gui / cl )");
		String risposta;
		do {
			risposta = in.nextLine();
		} while (risposta.equals("gui") || risposta.equals("cl"));
		return risposta;
	}

	private static void avvioClient(String protocollo) throws NoMoreCardsException, NoMoneyException, IllegalShireTypeException, NoSheepInShireException, IllegalShireException, CannotProcreateException, IllegalStreetException {
		if (protocollo.equals("rmi")) {
			ClientRMI client = new ClientRMI();
			client.start();
		} else {
			ClientSocket client = new ClientSocket("127.0.0.1",
					Costanti.PORTA_SOCKET);
			try {
				client.startClient();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public static String richiestaProtocolloComunicazione() {

		System.out
				.println("Inserire il protocollo di comunicazione con il Server (rmi / socket)");
		do {
			risposta = in.nextLine();
		} while (!risposta.equals("rmi") && !risposta.equals("socket"));
		return risposta;
	}

}
