package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import java.util.Scanner;

public class ClientApplication {

	public static void main(String[] args) {

		// TODO richiesta se utilizzare Socket o RMI
		String protocollo;
		protocollo = richiestaProtocolloComunicazione();

		// TODO scelta grafica

		// TODO avvio istanza di grafica
	}

	public static String richiestaProtocolloComunicazione() {
		String risposta = null;
		Scanner in = new Scanner(System.in);

		System.out
				.println("Inserire il protocollo di comunicazione con il Server (rmi / socket)");
		do {
			risposta = in.nextLine();
		} while (!risposta.equals("rmi") && !risposta.equals("socket"));
		return risposta;
	}

}
