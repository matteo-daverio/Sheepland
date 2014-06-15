package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.LOGGER;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Pong;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ClientSocket implements InterfacciaComunicazioneToServer {

	private String ip, nome, password;
	private int port;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ControllorePartitaClient controllore;
	private ComandiSocket line;

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
	 * metodo che attende di ricevere aggiornamenti da parte del server
	 * 
	 * @throws IOException
	 * @author Valerio De Maria
	 * @throws GameException
	 */
	public void riceviAggiornamenti() throws IOException, GameException {

		boolean finePartita = false;
		List<MosseEnum> mosseDisponibili = new ArrayList<MosseEnum>();
		
		int pastore,posizione;
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
					if (mosseDisponibili.size() == 0) {
						out.reset();
						out.writeObject(new Pong());
						out.flush();
						System.out.println("ho mandato un pong");
					} else {

						controllore.richiestaMossa(mosseDisponibili);
					}

					break;

				case MOVIMENTO_PASTORE:
					
					int pos=in.readInt();
					String gio=(String)in.readObject();
					pastore=in.readInt();
					
					controllore.movimentoPastore(pos, gio, pastore);
					break;

				case ACQUISTO_TESSERA:
					
					TipoTerreno terr=(TipoTerreno)in.readObject();
					String gioc=(String)in.readObject();
					pastore=in.readInt();
					
					controllore.acquistoTessera(terr, gioc, pastore);
					
					break;

				case ABBATTIMENTO:
					
					int regione = in.readInt();
					int pecora = in.readInt();
					giocatore=(String)in.readObject();
					pastore=in.readInt();
					
					controllore.abbattimento(regione, pecora, giocatore, pastore);
					break;

				case ACCOPPIAMENTO:
					
					int r=in.readInt();
					giocatore=(String)in.readObject();
					pastore=in.readInt();
					
					controllore.accoppiamento(r, giocatore, pastore);
					break;

				case MOVIMENTO_PECORA:
					
					int pecoraDaMuovere = in.readInt();
					int stradaPassaggioPecora=in.readInt();
					giocatore=(String) in.readObject();
					pastore=in.readInt();
	
					controllore.movimentoPecora(pecoraDaMuovere,stradaPassaggioPecora,giocatore,pastore);
					break;
					
				case INIZIO_TURNO:
					List<Pecora> pecorelle=(List<Pecora>)in.readObject();
					controllore.iniziaTurno(pecorelle);
					
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
					
				case RICHIESTA_POSIZIONE_PASTORE:
					
					List<Integer> stradeDisponibili=(List<Integer>)in.readObject();
					controllore.richiestaPosizionamentoPastore(stradeDisponibili);
					break;
				
				case POSIZIONAMENTO_PASTORE:
					int turno=in.readInt();
					pastore=in.readInt();
			        posizione=in.readInt();
			        
			        controllore.aggiornamentoPosizionePastore(turno, pastore, posizione);
			        
			        break;
			        
				case CAMBIO_TURNO:
					
					giocatore=(String)in.readObject();
					
					List<Pecora> gregge=(List<Pecora>)in.readObject();
					
					controllore.cambioTurno(giocatore,gregge);
					
					break;
					
				case MOSSA_SBAGLIATA:
					
					controllore.mossaSbagliata();
					
					break;
					
				case MOSSA_CORRETTA:
					
					controllore.mossaCorretta();
					
					break;
				
				case TESSERA_INIZIALE:
					
					Tessera tesseraIniziale =(Tessera)in.readObject();
					
					controllore.riceviTesseraIniziale(tesseraIniziale);
					
					break;
				case PUNTEGGI:
					
					List<Integer> punteggi=(List<Integer>)in.readObject();
					List<String> nomiGioc=(List<String>)in.readObject();
					
					controllore.punteggiFinali(punteggi, nomiGioc);
					
					break;
				
				case NUMERO_RECINTI:
					
					int numRecinti=in.readInt();
					
					controllore.comunicaNumeroRecinti(numRecinti);
					
					break;
				
				case AGGIORNAMENTO_DENARO:
					
					List<Integer> denaroPastori =(List<Integer>)in.readObject();
					controllore.comunicaDenaro(denaroPastori);
					break;
					
				case MOVIMENTO_LUPO:
					
					posizione=in.readInt();
					
					controllore.movimentoLupo(posizione);
					break;
					
				default:
					break;
				}// fine switch

			} catch (ClassNotFoundException e) {
				LOGGER.log("errore ricezione da server", e);
			}
		}
	}


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

	// DA CLIENT VERSO SERVER
	
	public void inviaPosizionePastore(int posizione) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.INVIO_POSIZIONE_PASTORE);
			out.flush();
			
			out.reset();
			out.writeInt(posizione);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void inviaMossa(Mossa mossa) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.INVIO_MOSSA);
			out.flush();
			
			out.reset();
			out.writeObject(mossa);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
