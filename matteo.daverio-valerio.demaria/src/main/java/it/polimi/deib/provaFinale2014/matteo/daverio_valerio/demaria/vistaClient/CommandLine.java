package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandLine implements InterfacciaGrafica {

	private Scanner in = new Scanner(System.in);
	private String nome, password;
	private boolean autenticato = false;

	public void start(ControllorePartitaClient controllore) {
		System.out.println("Benvenuto in Sheepland!");

		while (!autenticato) {
			System.out.println("Inserire nome:");
			do {
				nome = in.nextLine();
			} while (nome.equals(""));
			System.out.println("Inserire password:");
			do {
				password = in.nextLine();
			} while (password.equals(""));

			try {
				autenticato = controllore.logIn(nome, password);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Autenticazione riuscita");

	}

	public void posizionaPastore() {
		System.out
				.println("Dove vuoi posizionare inizialmente il tuo pastore?");
		int posPastore;
		do {
			posPastore = in.nextInt();
		} while (posPastore < 0 && posPastore > 41);

	}

	public void iniziaTurno() {
		System.out.println("Ora è il tuo turno");

	}

	/**
	 * comunico il movimento spontaneo della pecora nera
	 * 
	 * @author Valerio De Maria
	 */
	public void muoviPecoraNera(int posizione) {
		System.out.println("La pecora nera è scappata nella regione: "
				+ posizione);

	}

	/**
	 * mostra la posizione delle pecore sulla mappa
	 * 
	 * @param pecore
	 * @author Valerio De Maria
	 */
	public void inizializzaPecore(ArrayList<Pecora> pecore) {
		for (Pecora x : pecore) {
			if (x.getTipoPecora() == (Costanti.TIPO_PECORA_PECORA)) {
				System.out.println("C'è una pecora nella regione: "
						+ x.getPosizione());
			} else if (x.getTipoPecora() == (Costanti.TIPO_PECORA_MONTONE)) {
				System.out.println("C'è un montone nella regione: "
						+ x.getPosizione());
			} else
				System.out.println("C'è un agnello nella regione: "
						+ x.getPosizione());
		}

	}

}
