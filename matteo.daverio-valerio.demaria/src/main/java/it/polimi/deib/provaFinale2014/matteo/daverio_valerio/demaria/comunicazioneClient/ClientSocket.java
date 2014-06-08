package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.LOGGER;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientSocket {

	private String ip, nome, password;
	private int port;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Partita partita;

	/**
	 * costruttore
	 * 
	 * @param ip
	 * @param port
	 * @author Valerio De Maria
	 */
	public ClientSocket(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	/**
	 * effettua il login
	 * 
	 * @throws IOException
	 * @author Valerio De Maria
	 */
	private void logIn() throws IOException {

		boolean autenticato = false;
		ComandiSocket line;
		Scanner inputUtente = new Scanner(System.in);
		while (!autenticato) {

			try {
				// leggo dal buffer
				line = (ComandiSocket) in.readObject();

				// il server chiede il nome
				if (line.equals(ComandiSocket.RICHIESTA_NOME)) {
					do {
						System.out.println("Nome:");
						nome = inputUtente.nextLine();
					} while (nome.equals(""));
					out.reset();
					out.writeObject(nome);
					out.flush();
				}

				// il server chiede la password
				if (line.equals(ComandiSocket.RICHIESTA_PASSWORD)) {
					do {
						System.out.println("Password:");
						password = inputUtente.nextLine();
					} while (password.equals(""));
					out.reset();
					out.writeObject(password);
					out.flush();
				}

				// il server comunica che l'autenticazione è riuscita
				if (line.equals(ComandiSocket.AUTENTICAZIONE_RIUSCITA)) {
					autenticato = true;
					System.out.println("mi sono autenticato");
				}

				// il server comunica che l'autenticazione non è riuscita
				if (line.equals(ComandiSocket.AUTENTICAZIONE_FALLITA)) {
					System.out
							.println("Hai una partita in corso e la tua password è sbagliata!");
				}

			} catch (ClassNotFoundException e) {
				LOGGER.log("errore ricezione da server", e);
			}

		}

	}

	public void riceviPartita() throws IOException {
		boolean partitaRicevuta = false;
		ComandiSocket line;
		while (!partitaRicevuta) {

			try {

				// leggo dal buffer
				line = (ComandiSocket) in.readObject();

				if (line.equals(ComandiSocket.INVIO_PARTITA)) {
					partita = (Partita) in.readObject();
					partitaRicevuta=true;
					System.out.println("ho ricevuto la partita");
				}

			} catch (ClassNotFoundException e) {
				LOGGER.log("errore ricezione da server", e);
			}
		}
	}

	private void riceviAggiornamenti() throws IOException, NoMoreCardsException, NoMoneyException, IllegalShireTypeException, NoSheepInShireException, IllegalShireException, CannotProcreateException, IllegalStreetException{
		
		boolean finePartita=false;
		List<MosseEnum> mosseDisponibili=new ArrayList<MosseEnum>();
		ComandiSocket line;
		
		while(!finePartita){
			try {

				// leggo dal buffer
				line = (ComandiSocket) in.readObject();

				switch(line){
				case MOVIMENTO_PECORA_NERA:
					partita.getPecoraNera().setPosizione(in.readInt());
					System.out.println("si è mossa la pecora nera");
					break;
					
				case RICHIESTA_DI_MOSSA:
					mosseDisponibili=(List<MosseEnum>)in.readObject();
					if(mosseDisponibili.size()==0){
						out.reset();
						out.writeObject(new Pong());
						out.flush();
						System.out.println("ho mandato un pong");
					}
					else{
					 //TODO chiedo all'utente la mossa che vuole fare
						out.reset();
						out.writeObject(new MuoviPastore(4));
						out.flush();
					}
					
					break;
					
				case MOVIMENTO_PASTORE:
					partita.getPastori().get(partita.getTurno() -1).setPosizione(in.readInt());
					System.out.println("ho mosso il pastore di turno");
					break;
				
				case ACQUISTO_TESSERA:
					partita.compraTessera((TipoTerreno)in.readObject());
					break;
					
				case ABBATTIMENTO:
					int regione =in.readInt();
					int pecora =in.readInt();
					partita.abbatti(regione, pecora);
					break;
					
				case ACCOPPIAMENTO:
					partita.accoppia(in.readInt());
					break;
				
				case MOVIMENTO_PECORA:
					int p =in.readInt();
					Strada strada =(Strada) in.readObject();
					partita.muoviPecora(p, strada);
				break;
				default:
					break;
				}//fine switch


			} catch (ClassNotFoundException e) {
				LOGGER.log("errore ricezione da server", e);
			}
		}
	}
	/**
	 * avvio il client socket
	 * 
	 * @throws IOException
	 * @author Valerio De Maria
	 * @throws IllegalShireTypeException 
	 * @throws NoMoneyException 
	 * @throws NoMoreCardsException 
	 * @throws IllegalShireException 
	 * @throws NoSheepInShireException 
	 * @throws IllegalStreetException 
	 * @throws CannotProcreateException 
	 */
	public void startClient() throws IOException, NoMoreCardsException, NoMoneyException, IllegalShireTypeException, NoSheepInShireException, IllegalShireException, CannotProcreateException, IllegalStreetException {

		// chiedo una socket al server
		Socket socket = new Socket(ip, port);
		System.out.println("Connessione al server riuscita");

		// creo i buffer per ricevere/inviare dati con il server
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());

		logIn();

		riceviPartita();
		
		riceviAggiornamenti();

		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			LOGGER.log("errore in chiusura socket", e);
		}
	}

}
