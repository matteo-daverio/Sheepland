package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.LOGGER;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * classe che gestisce le conessioni socket
 * 
 * @author Valerio De Maria
 * 
 */
public class GestioneSocket implements Gestione {

	private Socket socket;
	private String nomeClient, password, tipo;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Partita partita;

	// costruttore
	public GestioneSocket(Socket socket) {

		this.tipo = "socket";
		this.socket = socket;
		//al momento del login il client non ha nessuna partita in corso
		this.partita=null;

		try {

			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());

			// chiedo il nomeGiocatore
			out.reset();
			out.writeObject(ComandiSocket.RICHIESTA_NOME);
			out.flush();
			String nome = new String();
			try {
				nome = (String) in.readObject();
			} catch (ClassNotFoundException e) {
				LOGGER.log("errore in lettura nome", e);
			}
			this.nomeClient = nome;

			// chiedo la password
			out.reset();
			out.writeObject(ComandiSocket.RICHIESTA_PASSWORD);
			out.flush();
			String password = new String();
			try {
				password = (String) in.readObject();
			} catch (ClassNotFoundException e) {
				LOGGER.log("errore in lettura password", e);
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

	/**
	 * ritorna il nome
	 * 
	 * @author Valerio De Maria
	 */
	public String getNome() {
		return nomeClient;
	}

	/**
	 * ritorna il tipo di connessione
	 * 
	 * @author Valerio De Maria
	 */
	public String getTipoConnessione() {
		return tipo;
	}

	/**
	 * ritorna la socket
	 * 
	 * @author Valerio De Maria
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * ritorna la password
	 * 
	 * @author Valerio De Maria
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * ritorna il buffer di lettura
	 * 
	 * @author Valeri De Maria
	 */
	public ObjectInputStream getBufferIn() {
		return in;
	}

	/**
	 * ritorna il buffer di scrittura
	 * 
	 * @author Valerio De Maria
	 */
	public ObjectOutputStream getBufferOut() {
		return out;
	}

	/**
	 * ritorna null perchè non è una GestioneRMI
	 * 
	 * @author Valerio De Maria
	 */
	public InterfacciaClientRMI getInterfacciaClient() {
		return null;
	}

	/**
	 * se il nome e password inseriti sono relativi ad un giocatore che sta
	 * giocando e la password è sbagliata l'autenticazione fallisce
	 * 
	 * @param riuscita
	 * @author Valerio De Maria
	 */
	public void autenticazione(boolean riuscita) {
		if (riuscita) {
			try {
				out.reset();
				out.writeObject(ComandiSocket.AUTENTICAZIONE_RIUSCITA);
				out.flush();
			} catch (IOException e) {
				LOGGER.log("errore connessione Socket", e);
			}

		} else {
			try {
				out.reset();
				out.writeObject(ComandiSocket.AUTENTICAZIONE_FALLITA);
				out.flush();
			} catch (IOException e) {
				LOGGER.log("errore connessione Socket", e);
			}

		}
	}

	/**
	 * ritorna la partita in corso del client
	 * 
	 * @author Valerio De Maria
	 */
	public Partita getPartita() {
		return partita;
	}
}
