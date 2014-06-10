package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLine implements InterfacciaGrafica {

	private Scanner in = new Scanner(System.in);
	private String nome, password;
	private boolean autenticato = false;
	ControllorePartitaClient controllore;

	public CommandLine(ControllorePartitaClient controllore) {

		this.controllore = controllore;
	}

	public void start() {
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

		//TODO chiamo un metodo sul controllore passandogli la posizione del pastore

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
	public void settaPecore(List<Pecora> pecore) {
		System.out
				.println("Le pecore sono posizionate inizialmente nel modo seguente: ");
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

	public void nomiGiocatori(List<String> nomi) {
		for(int i=0;i<=nomi.size()-1;i++){
			System.out.println("Il giocatore del turno "+i+" è "+nomi.get(i));
		}
		
	}

	public void soldiPastori(List<Integer> soldi) {
		for(int i=0;i<=soldi.size()-1;i++){
			System.out.println("Il pastore "+i+ " ha "+soldi.get(i)+" denari");
		}
		
	}

	public void tessereInizialiPastori(List<Tessera> tessereIniziali) {
		for(int i=0;i<=tessereIniziali.size()-1;i++){
			System.out.println("Il pastore "+i+" ha tessera iniziale di tipo: "+tessereIniziali.get(i).getTipo()+" con costo: "+tessereIniziali.get(i).getCosto());
		}
		
	}

}
