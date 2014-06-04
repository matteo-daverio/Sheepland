package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String tipo;
	private Partita partita;

	// costruttore
	public GestioneSocket(Socket socket) {

		this.tipo = "socket";
		this.socket = socket;

		try {

			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());

			// chiedo il nomeGiocatore
			out.reset();
			out.writeObject(ComandiSocket.RICHIESTA_NOME);
			out.flush();
			String nome=new String();
			try {
				nome = (String)in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			this.nomeClient = nome;

			// chiedo la password
			out.reset();
			out.writeObject(ComandiSocket.RICHIESTA_PASSWORD);
			out.flush();
			String password=new String();
			try {
				password = (String)in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			this.password = password;

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
	
	public ObjectInputStream getBufferIn(){
		return in;
	}

	public ObjectOutputStream getBufferOut(){
		return out;
	}
	
	public InterfacciaClientRMI getInterfacciaClient() {
		return null;
	}
	/**
	 * se il nome e password inseriti sono relativi ad un giocatore che sta
	 * giocando, l'autenticazione fallisce 
	 * @param riuscita
	 */
	public void autenticazione(boolean riuscita){
		if(riuscita){
			try {
				out.reset();
				out.writeObject(ComandiSocket.AUTENTICAZIONE_RIUSCITA);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		else{
			try {
				out.reset();
				out.writeObject(ComandiSocket.AUTENTICAZIONE_FALLITA);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public Partita getPartita() {
		return partita;
	}
}
