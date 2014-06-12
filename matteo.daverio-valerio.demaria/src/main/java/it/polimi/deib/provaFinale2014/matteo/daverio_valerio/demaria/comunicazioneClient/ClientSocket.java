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
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
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

public class ClientSocket implements InterfacciaComunicazioneToServer {

	private String ip, nome, password;
	private int port;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ControllorePartitaClient controllore;
	private ComandiSocket line;

	/**
	 * costruttore
	 * 
	 * @param ip
	 * @param port
	 * @author Valerio De Maria
	 */
	public ClientSocket(String ip, int port,
			ControllorePartitaClient controllore) {
		this.ip = ip;
		this.port = port;
		this.controllore = controllore;
	}

	/**
	 * metodo che attende di ricevere aggiornamenti da parte del server
	 * 
	 * @throws IOException
	 * @author Valerio De Maria
	 * @throws GameException
	 */
	public void riceviAggiornamenti() throws IOException, GameException {

		boolean finePartita = false;
		List<MosseEnum> mosseDisponibili = new ArrayList<MosseEnum>();
		

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
					
					int pos=in.readInt();
					String gio=(String)in.readObject();
					int pas=in.readInt();
					
					controllore.movimentoPastore(pos, gio, pas);
					break;

				case ACQUISTO_TESSERA:
					
					break;

				case ABBATTIMENTO:
					
					int regione = in.readInt();
					int pecora = in.readInt();
					String giocatore=(String)in.readObject();
					int pastore=in.readInt();
					
					controllore.abbattimento(regione, pecora, giocatore, pastore);
					break;

				case ACCOPPIAMENTO:
					
					int r=in.readInt();
					String g=(String)in.readObject();
					int p=in.readInt();
					
					controllore.accoppiamento(r, g, p);
					break;

				case MOVIMENTO_PECORA:
					
					int pecoraDaMuovere = in.readInt();
					int stradaPassaggioPecora=in.readInt();
					String giocatoreSpostaPecora=(String) in.readObject();
					int pastoreSpostaPecora=in.readInt();
	
					controllore.movimentoPecora(pecoraDaMuovere,stradaPassaggioPecora,giocatoreSpostaPecora,pastoreSpostaPecora);
					break;
					
				case INIZIO_TURNO:
					controllore.iniziaTurno();
					break;
					
				case DATI_GIOCATORI:
					List<String> nomi = (List<String>) in.readObject();
					controllore.riceviNomiGiocatori(nomi);
					List<Integer> soldi = (List<Integer>) in.readObject();
					controllore.riceviSoldiPastori(soldi);

					break;
					
				case INVIO_PECORE:
					List<Pecora> pecore =(List<Pecora>)in.readObject();
					controllore.settaPecore(pecore);
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
