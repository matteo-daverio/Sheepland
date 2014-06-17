package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * thread che rimane in ascolto su uno specifico buffer socket d'ingersso
 * 
 * @author Valerio De Maria
 * 
 */
public class ThreadRicezioneSocket implements Runnable {

	private ObjectInputStream in;
	private ComandiSocket line;
	private ControllorePartita gameManager;
	private int turno;
	private boolean connesso = true;

	private static final Logger LOG = Logger.getLogger(ClientRMI.class
			.getName());

	/**
	 * COSTRUTTORE
	 * 
	 * @param in
	 * @param gameManager
	 * @param turno
	 * @author Valerio De Maria
	 */
	public ThreadRicezioneSocket(ObjectInputStream in,
			ControllorePartita gameManager, int turno) {

		this.in = in;
		this.gameManager = gameManager;
		this.turno = turno;
		System.out.println("turno thread socket: "+turno);

	}

	/**
	 * aggiorna la socket con la quella nuova del client che si è riconnesso
	 * 
	 * @param socket
	 * @author Valerio De Maria
	 */
	public void aggiornaSocket(Socket socket) {

		try {
			in = new ObjectInputStream(socket.getInputStream());
			connesso = true;
		} catch (IOException e) {

			LOG.log(Level.SEVERE, "errore in connessione", e);

		}

	}

	/**
	 * chiude il buffer d'ingresso
	 * 
	 * @author Valerio De Maria
	 */
	public void chiudiConnesione() {

		try {
			in.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Errore in chiusura", e);
		}
	}

	/**
	 * il thread continua a leggere dal buffer socket in ingresso finche il
	 * clien è connesso
	 * 
	 * @author Valerio De Maria
	 */
	public void run() {

		while (connesso) {

			try {
				
				line = (ComandiSocket) in.readObject();

				switch (line) {

				case INVIO_POSIZIONE_PASTORE:
					gameManager.riceviPosizionamentoPastore(in.readInt());

					break;

				case INVIO_MOSSA:
					Mossa mossa = (Mossa) in.readObject();
					int pastoreTurno = in.readInt();
					gameManager.riceviMossa(mossa, pastoreTurno);
					break;

				default:
					break;
				}

			} catch (ClassNotFoundException e) {

				connesso = false;
				gameManager.avvioTimerDisconnessione(turno);
				LOG.log(Level.SEVERE, "errore in ricezione socket", e);

			} catch (IOException e) {

				connesso = false;
				gameManager.avvioTimerDisconnessione(turno);
				LOG.log(Level.SEVERE, "errore in ricezione socket", e);

			}

		}

	}

}
