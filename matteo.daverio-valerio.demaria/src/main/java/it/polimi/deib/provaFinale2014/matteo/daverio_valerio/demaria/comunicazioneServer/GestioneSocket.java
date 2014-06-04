package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * classe che gestisce le conessioni socket
 * 
 * @author Valerio De Maria
 * 
 */
public class GestioneSocket implements Gestione {

	private Socket socket;
	private String nomeClient, password;
	private Scanner in;
	private PrintWriter out;
	private String line;
	private String tipo;

	// costruttore
	public GestioneSocket(Socket socket) {

		this.tipo = "socket";
		this.socket = socket;

		try {

			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream());

			// chiedo il nomeGiocatore
			out.println("nome?");
			out.flush();
			line = in.nextLine();

			this.nomeClient = line;

			// chiedo la password
			out.println("password?");
			out.flush();
			line = in.nextLine();

			this.password = line;

			// chiudo la connessione
			in.close();
			out.close();
			socket.close();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

	public String getNome() {
		return nomeClient;
	}

	public String getTipoConnessione() {
		return tipo;
	}

	public Socket getSocket() {
		return socket;
	}

	public String getPassword() {
		return password;
	}

	public InterfacciaClientRMI getInterfacciaClient() {
		return null;
	}
}
