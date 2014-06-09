package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.LOGGER;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.MuoviPastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Pong;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientSocket implements InterfacciaComunicazioneClient {

	private String ip, nome, password;
	private int port;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Partita partita;
    private ControllorePartitaClient controllore;
	
	/**
	 * costruttore
	 * 
	 * @param ip
	 * @param port
	 * @author Valerio De Maria
	 */
	public ClientSocket(String ip, int port,ControllorePartitaClient controllore) {
		this.ip = ip;
		this.port = port;
		this.controllore=controllore;
	}

	/**
	 * metodo che attende di ricevere la partita
	 * 
	 * @throws IOException
	 * @author Valerio De Maria
	 */
	public void attendiPartita() throws IOException {
		boolean partitaRicevuta = false;
		ComandiSocket line;
		while (!partitaRicevuta) {

			try {

				// leggo dal buffer
				line = (ComandiSocket) in.readObject();

				if (line.equals(ComandiSocket.INVIO_PARTITA)) {
					partita = (Partita) in.readObject();
					partitaRicevuta = true;
					controllore.riceviPartita(partita);
				}

			} catch (ClassNotFoundException e) {
				LOGGER.log("errore ricezione da server", e);
			}
		}
	}

	/**
	 * metodo che attende di ricevere aggiornamenti da parte del server
	 * 
	 * @throws IOException
	 * @author Valerio De Maria
	 * @throws GameException 
	 */
	public void riceviAggiornamenti() throws IOException,
			GameException {

		boolean finePartita = false;
		List<MosseEnum> mosseDisponibili = new ArrayList<MosseEnum>();
		ComandiSocket line;

		while (!finePartita) {
			try {

				// leggo dal buffer
				line = (ComandiSocket) in.readObject();

				switch (line) {
				case MOVIMENTO_PECORA_NERA:
                    controllore.movimentoPecoraNera(in.readInt());
					break;

				case RICHIESTA_DI_MOSSA:
					mosseDisponibili = (List<MosseEnum>) in.readObject();
					if (mosseDisponibili.size() == 0) {
						out.reset();
						out.writeObject(new Pong());
						out.flush();
						System.out.println("ho mandato un pong");
					} else {
						// TODO chiedo all'utente la mossa che vuole fare
						out.reset();
						out.writeObject(new MuoviPastore(4));
						out.flush();
					}

					break;

				case MOVIMENTO_PASTORE:
					partita.getPastori().get(partita.getTurno() - 1)
							.setPosizione(in.readInt());
					System.out.println("ho mosso il pastore di turno");
					break;

				case ACQUISTO_TESSERA:
					partita.compraTessera((TipoTerreno) in.readObject());
					break;

				case ABBATTIMENTO:
					int regione = in.readInt();
					int pecora = in.readInt();
					partita.abbatti(regione, pecora);
					break;

				case ACCOPPIAMENTO:
					partita.accoppia(in.readInt());
					break;

				case MOVIMENTO_PECORA:
					int p = in.readInt();
					Strada strada = (Strada) in.readObject();
					partita.muoviPecora(p, strada);
					break;
				default:
					break;
				}// fine switch

			} catch (ClassNotFoundException e) {
				LOGGER.log("errore ricezione da server", e);
			}
		}
	}

	// NEW
	public boolean effettuaLogin(String nome, String password)
			throws IOException {

		ComandiSocket line;
		Socket socket;

		try {
			// chiedo una socket al server
			socket = new Socket(ip, port);

			// creo i buffer per ricevere/inviare dati con il server
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			try {

				// leggo dal buffer
				line = (ComandiSocket) in.readObject();

				// il server chiede il nome
				if (line.equals(ComandiSocket.RICHIESTA_NOME)) {
					out.reset();
					out.writeObject(nome);
					out.flush();
				}

				// il server chiede la password
				else if (line.equals(ComandiSocket.RICHIESTA_PASSWORD)) {
					out.reset();
					out.writeObject(password);
					out.flush();
				}

				// il server comunica che l'autenticazione è riuscita
				else if (line.equals(ComandiSocket.AUTENTICAZIONE_RIUSCITA)) {
					return true;
				}

				// il server comunica che l'autenticazione non è riuscita
				else if (line.equals(ComandiSocket.AUTENTICAZIONE_FALLITA)) {
					return false;
				}

			} catch (ClassNotFoundException e) {
				LOGGER.log("errore ricezione da server", e);
			}
		}

	}

}
