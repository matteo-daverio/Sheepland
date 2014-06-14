package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.LOGGER;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * classe che parla con client socket
 * 
 * @author Valerio De Maria
 * 
 */
public class ComunicazioneSocket implements InterfacciaComunicazioneToClient {

	private Socket socket;
	private String nome;
	private ObjectOutputStream out;

	/**
	 * costruttore
	 * 
	 * @param socket
	 * @param in
	 * @param out
	 * @param nome
	 * @author Valerio De Maria
	 */
	public ComunicazioneSocket(Socket socket,
			ObjectOutputStream out, String nome) {

		this.socket = socket;
		this.out = out;
		this.nome = nome;
	}

	/**
	 * invia l'oggetto partita
	 * 
	 * @author Valerio De Maria
	 */
	public void inviaPartita(Partita partita) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.INVIO_PARTITA);
			out.flush();
			out.reset();
			out.writeObject(partita);
			out.flush();
		} catch (IOException e) {
			LOGGER.log("errore in invio partita", e);
		}

	}

	/**
	 * chiudo la connessione
	 * 
	 * @author Valerio De Maria
	 */
	public void chiudiConnessione() {

		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			LOGGER.log("errore in chiusura socket", e);
		}
	}

	/**
	 * comunica il movimento della pecora nera
	 * 
	 * @author Valerio De Maria
	 */
	public void comunicaMovimentoPecoraNera(int nuovaPosizione) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.MOVIMENTO_PECORA_NERA);
			out.flush();
			out.reset();
			out.writeInt(nuovaPosizione);
			out.flush();
		} catch (IOException e) {
			LOGGER.log("errore in invio mossa pecora", e);
		}

	}

	public Mossa riceviMossa(List<MosseEnum> mosseDisponibili) {
		Mossa mossa = null;
		try {
			// richiedo una mossa
			out.reset();
			out.writeObject(ComandiSocket.RICHIESTA_DI_MOSSA);
			out.flush();
			// invio la lista di mosse disponibili
			out.reset();
			out.writeObject(mosseDisponibili);
			out.flush();

			try {
				mossa = (Mossa) in.readObject();
			} catch (ClassNotFoundException e) {
				LOGGER.log("errore in ricezione mossa", e);
			}

		} catch (IOException e) {
			LOGGER.log("errore in comunicazione richiesta mossa", e);
		}

		return mossa;
	}

	/**
	 * comunica il movimento di un pastore
	 * 
	 * @author Valerio De Maria
	 */
	public void comunicaMovimentoPastore(int posizione) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.MOVIMENTO_PASTORE);
			out.flush();
			out.reset();
			out.writeInt(posizione);
			out.flush();
		} catch (IOException e) {
			LOGGER.log("errore in comunicazione movimento pastore", e);
		}
	}

	/**
	 * comunico l'acquisto di una tessera
	 * 
	 * @author Valerio De Maria
	 */
	public void comunicaAcquistaTessera(TipoTerreno terreno) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.ACQUISTO_TESSERA);
			out.flush();
			out.reset();
			out.writeObject(terreno);
			out.flush();
		} catch (IOException e) {
			LOGGER.log("errore in comunicazione acquisto tessera", e);
		}

	}

	/**
	 * comunico un abbattimento
	 * 
	 * @author Valerio De Maria
	 */
	public void comunicaAbbattimento(int regione, int pecora) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.ABBATTIMENTO);
			out.flush();
			out.reset();
			out.writeInt(regione);
			out.flush();
			out.reset();
			out.writeInt(pecora);
			out.flush();

		} catch (IOException e) {
			LOGGER.log("errore in comunicazione abbattimento", e);
		}

	}

	/**
	 * comunico un accoppiamento
	 * 
	 * @author Valerio De Maria
	 */
	public void comunicaAccoppiamento(int regione) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.ACCOPPIAMENTO);
			out.flush();
			out.reset();
			out.writeInt(regione);
			out.flush();
		} catch (IOException e) {
			LOGGER.log("errore in comunicazione accoppiamento", e);
		}

	}

	/**
	 * comunico il movimento di una pecora
	 * 
	 * @author Valerio De Maria
	 */
	public void comunicaMovimentoPecora(int pecora, Strada strada) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.MOVIMENTO_PECORA);
			out.flush();
			out.reset();
			out.writeInt(pecora);
			out.flush();
			out.reset();
			out.writeObject(strada);
			out.flush();
		} catch (IOException e) {
			LOGGER.log("errore in comunicazione movimento pecora", e);
		}

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
		return "socket";
	}

	/**
	 * setta la socket
	 * 
	 * @author Valerio De Maria
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * al client che ora è il turno del giocatore successivo
	 * 
	 * @author Valerio De Maria
	 */
	public void comunicaCambioTurno() {
		try {
			out.reset();
			out.writeObject(ComandiSocket.CAMBIO_TURNO);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void comunicaFaseFinale() {
		try {
			out.reset();
			out.writeObject(ComandiSocket.FASE_FINALE);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void comunicaInizioTurno() {

		try {
			out.reset();
			out.writeObject(ComandiSocket.INIZIO_TURNO);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int chiediPosizionamentoPastore() {

		try {
			out.reset();
			out.writeObject(ComandiSocket.POSIZIONA_PASTORE);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return in.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return-1;
	}

	public void inviaDatiGiocatori(List<String> nomi,List<Integer>soldi) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.DATI_GIOCATORI);
			out.flush();
			out.reset();
			out.writeObject(nomi);
			out.flush();
			out.reset();
			out.writeObject(soldi);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void comunicaPecore(List<Pecora> pecore) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.INVIO_PECORE);
			out.flush();
			out.reset();
			out.writeObject(pecore);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void comunicaMovimentoPastore(int posizione, String giocatore,
			int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void comunicaAcquistaTessera(TipoTerreno terreno, String giocatore,
			int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void comunicaAbbattimento(int regione, int pecora, String giocatore,
			int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void comunicaAccoppiamento(int regione, String giocatore, int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void comunicaMovimentoPecora(int pecora, int strada,
			String giocatore, int pastore) {
		// TODO Auto-generated method stub
		
	}

	public int chiediPosizionamentoPastore(List<Integer> stradeDisponibili) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void comunicaPosizionamentoPastore(int turno, int pastore,
			int posizione) {
		// TODO Auto-generated method stub
		
	}

	public void comunicaTurno() {
		// TODO Auto-generated method stub
		
	}

	public void aggiornaTurno(String giocatore) {
		// TODO Auto-generated method stub
		
	}

	public int selezionaPastore(int primo, int secondo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void comunicaMossaSbagliata() {
		// TODO Auto-generated method stub
		
	}

	public void inviaTesseraIniziale(Tessera tesseraIniziale) {
		// TODO Auto-generated method stub
		
	}

	public void mossaCorretta() {
		// TODO Auto-generated method stub
		
	}

	public void inviaPunteggi(List<Integer> punteggiFinali, List<String> nomi) {
		// TODO Auto-generated method stub
		
	}

	public void comunicaDenaro(int denaro) {
		// TODO Auto-generated method stub
		
	}

	public void comunicaNumeroRecinti(int recinti) {
		// TODO Auto-generated method stub
		
	}

	//NUOVI METODI
	
	public void posizionamentoPastoreCorretto() {
		// TODO Auto-generated method stub
		
	}

	public void inviaRichiestaMossa(List<MosseEnum> mosseDisponibili) {
		// TODO Auto-generated method stub
		
	}

	public void inviaRichiestaPosizionamento(List<Integer> stradeDisponibili) {
		// TODO Auto-generated method stub
		
	}

}
