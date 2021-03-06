package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * classe che gestisce le conessioni socket
 * 
 * @author Valerio De Maria
 * 
 */
public class GestioneSocket implements Gestione {

	private Socket socket;
	private String nome, password, tipo;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Partita partita;
	private static final Logger LOG=Logger.getLogger(ClientRMI.class.getName());

	/**
	 * COSTRUTTORE
	 * 
	 * @param socket
	 * @author Valerio De Maria
	 */
	public GestioneSocket(Socket socket) {

		this.tipo = "socket";
		this.socket = socket;
		// al momento del login il client non ha nessuna partita in corso
		this.partita = null;

	}

	/**
	 * ritorna il nome
	 * 
	 * @author Valerio De Maria
	 */
	public String getNome() {
		return nome;
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
				LOG.log(Level.SEVERE,"errore connessione Socket", e);
			}

		} else {
			try {
				out.reset();
				out.writeObject(ComandiSocket.AUTENTICAZIONE_FALLITA);
				out.flush();
			} catch (IOException e) {
				LOG.log(Level.SEVERE,"errore connessione Socket", e);
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

	/**
	 * chiedo nome e password
	 * @author Valerio De Maria
	 */
	public void chiediDati() {
		try {

			// dichiaro PRIMA quello d'uscita e DOPO quello d'ingresso perchè il
			// costruttore di quello d'ingresso ha bisogno di fare subito un
			// flush per caricare il suo header!!!!!

			// The ObjectInputStream constructor blocks until it completes
			// reading the serialization stream header. Code which waits for an
			// ObjectInputStream to be constructed before creating the
			// corresponding ObjectOutputStream for that stream will deadlock,
			// since the ObjectInputStream constructor will block until a header
			// is written to the stream, and the header will not be written to
			// the stream until the ObjectOutputStream constructor executes.

			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());

			// chiedo il nomeGiocatore
			out.reset();
			out.writeObject(ComandiSocket.RICHIESTA_NOME);
			out.flush();
			String nome = new String();
			try {
			
					nome = (String) in.readObject();
			
			} catch (ClassNotFoundException e) {
				LOG.log(Level.SEVERE,"errore in lettura nome", e);
			}
			this.nome = nome;
			
			// chiedo la password
			out.reset();
			out.writeObject(ComandiSocket.RICHIESTA_PASSWORD);
			out.flush();
			String password = new String();
			try {
			
					password = (String) in.readObject();
				
			} catch (ClassNotFoundException e) {
				LOG.log(Level.SEVERE,"errore in lettura password", e);
			}

			this.password = password;
			

		} catch (IOException e) {
			LOG.log(Level.SEVERE,"non comunica il socket", e);
			
		}
	}
}
