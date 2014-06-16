package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocket implements InterfacciaComunicazioneToServer {

	private String ip;
	private int port;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ControllorePartitaClient controllore;
	private ComandiSocket line;
	private static final Logger LOG=Logger.getLogger(ClientRMI.class.getName());

	/**
	 * COSTRUTTORE
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
	 * metodo per effettuare il log-in
	 * 
	 * @author Valerio De Maria
	 */
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
			LOG.log(Level.SEVERE,"UnknowHostException", e);
		} catch (IOException e) {
			LOG.log(Level.SEVERE,"Errore creazione buffer", e);
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
				LOG.log(Level.SEVERE,"errore ricezione da server", e);
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
	public void riceviAggiornamenti() throws IOException, GameException {

		boolean finePartita = false;
		List<MosseEnum> mosseDisponibili = new ArrayList<MosseEnum>();

		int pastore, posizione;
		String giocatore;

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
						controllore.richiestaMossa(mosseDisponibili);
					

					break;

				case MOVIMENTO_PASTORE:

					int pos = in.readInt();
					String gio = (String) in.readObject();
					pastore = in.readInt();

					controllore.movimentoPastore(pos, gio, pastore);
					break;

				case ACQUISTO_TESSERA:

					TipoTerreno terr = (TipoTerreno) in.readObject();
					String gioc = (String) in.readObject();
					pastore = in.readInt();

					controllore.acquistoTessera(terr, gioc, pastore);

					break;

				case ABBATTIMENTO:

					int regione = in.readInt();
					int pecora = in.readInt();
					giocatore = (String) in.readObject();
					pastore = in.readInt();

					controllore.abbattimento(regione, pecora, giocatore,
							pastore);
					break;

				case ACCOPPIAMENTO:

					int r = in.readInt();
					giocatore = (String) in.readObject();
					pastore = in.readInt();

					controllore.accoppiamento(r, giocatore, pastore);
					break;

				case MOVIMENTO_PECORA:

					int pecoraDaMuovere = in.readInt();
					int stradaPassaggioPecora = in.readInt();
					giocatore = (String) in.readObject();
					pastore = in.readInt();

					controllore.movimentoPecora(pecoraDaMuovere,
							stradaPassaggioPecora, giocatore, pastore);
					break;

				case INIZIO_TURNO:
					List<Pecora> pecorelle = (List<Pecora>) in.readObject();
					int t = in.readInt();
					controllore.iniziaTurno(pecorelle, t);

					break;

				case DATI_GIOCATORI:

					List<String> nomi = (List<String>) in.readObject();
					controllore.riceviNomiGiocatori(nomi);

					List<Integer> soldi = (List<Integer>) in.readObject();
					controllore.riceviSoldiPastori(soldi);

					break;

				case INVIO_PECORE:
					List<Pecora> pecore = (List<Pecora>) in.readObject();
					controllore.settaPecore(pecore);
					break;

				case RICHIESTA_POSIZIONE_PASTORE:

					List<Integer> stradeDisponibili = (List<Integer>) in
							.readObject();
					controllore
							.richiestaPosizionamentoPastore(stradeDisponibili);
					break;

				case POSIZIONAMENTO_PASTORE:
					int turno = in.readInt();
					pastore = in.readInt();
					posizione = in.readInt();

					controllore.aggiornamentoPosizionePastore(turno, pastore,
							posizione);

					break;

				case CAMBIO_TURNO:

					giocatore = (String) in.readObject();

					List<Pecora> gregge = (List<Pecora>) in.readObject();

					controllore.cambioTurno(giocatore, gregge);

					break;

				case MOSSA_SBAGLIATA:

					controllore.mossaSbagliata();

					break;

				case MOSSA_CORRETTA:

					controllore.mossaCorretta();

					break;

				case TESSERA_INIZIALE:

					Tessera tesseraIniziale = (Tessera) in.readObject();

					controllore.riceviTesseraIniziale(tesseraIniziale);

					break;
				case PUNTEGGI:

					List<Integer> punteggi = (List<Integer>) in.readObject();
					List<String> nomiGioc = (List<String>) in.readObject();

					controllore.punteggiFinali(punteggi, nomiGioc);

					break;

				case NUMERO_RECINTI:

					int numRecinti = in.readInt();

					controllore.comunicaNumeroRecinti(numRecinti);

					break;

				case AGGIORNAMENTO_DENARO:

					List<Integer> denaroPastori = (List<Integer>) in
							.readObject();
					controllore.comunicaDenaro(denaroPastori);
					break;

				case MOVIMENTO_LUPO:

					posizione = in.readInt();

					controllore.movimentoLupo(posizione);
					break;

				case AGGIORNAMENTO:

					List<Pecora> sheep = (List<Pecora>) in.readObject();
					int posBlackSheep = in.readInt();
					int posWolf = in.read();
					List<Pastore> shepperds = (List<Pastore>) in.readObject();

					controllore.aggiornamentoPostDisconnessione(sheep,
							posBlackSheep, posWolf, shepperds);
					break;

				case INVIO_STRADE:

					List<Strada> street = (List<Strada>) in.readObject();
					controllore.riceviStrade(street);

					break;
					
				case SPOSTAMENTO_PECORA_NERA:
					int estrada=in.readInt();
				    giocatore=(String)in.readObject();
				    pastore=in.readInt();
				    
				    controllore.spostamentoPecoraNera(estrada,giocatore,pastore);
					
					break;
				
				case DISCONNESSIONE:
					String discN=(String)in.readObject();
					controllore.disconnessione(discN);
					break;
					
				case RICONNESSIONE:
					String ricN=(String)in.readObject();
					controllore.riconnessione(ricN);
					break;

				case ESCLUSIONE:
					String escN=(String)in.readObject();
					controllore.esclusione(escN);
					break;
					
				default:
					break;
				}// fine switch

			} catch (ClassNotFoundException e) {

				controllore.segnalaDisconnessione();

				LOG.log(Level.SEVERE,"errore ricezione da server", e);
			}catch (IOException e){
				controllore.segnalaDisconnessione();
				
				LOG.log(Level.SEVERE,"errore ricezione da server", e);
			}
			
		}
	}

	// //////// // DA CLIENT VERSO SERVER ////////////

	/**
	 * invio la posizione del mio pastore
	 * 
	 * @author Valerio De Maria
	 */
	public void inviaPosizionePastore(int posizione) {
		try {

			out.reset();
			out.writeObject(ComandiSocket.INVIO_POSIZIONE_PASTORE);
			out.flush();

			out.reset();
			out.writeInt(posizione);
			out.flush();

		} catch (IOException e) {

			controllore.segnalaDisconnessione();

			LOG.log(Level.SEVERE,"Errore in connessione", e);
		}

	}

	/**
	 * invio al server la mossa che voglio eseguire
	 * 
	 * @author Valerio De Maria
	 */
	public void inviaMossa(Mossa mossa, int pastoreTurno) {
		try {

			out.reset();
			out.writeObject(ComandiSocket.INVIO_MOSSA);
			out.flush();

			out.reset();
			out.writeObject(mossa);
			out.flush();

			out.reset();
			out.writeInt(pastoreTurno);
			out.flush();
		} catch (IOException e) {

			controllore.segnalaDisconnessione();

			LOG.log(Level.SEVERE,"Errore in connessione", e);
		}

	}

}
