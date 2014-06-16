package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * classe che invia messaggi ai client socket
 * 
 * @author Valerio De Maria
 * 
 */
public class ComunicazioneSocket implements InterfacciaComunicazioneToClient {

	private Socket socket;
	private String nome;
	private ObjectOutputStream out;
	private static final Logger LOG=Logger.getLogger(ClientRMI.class.getName());

	/**
	 * COSTRUTTORE
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
	
//GETTERS AND SETTERS	
	
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
	 * setta la socket e riaggiorna il buffer di uscita
	 * 
	 * @author Valerio De Maria
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	// METODI /////////////////////////////
	

	/**
	 * chiudo la connessione
	 * 
	 * @author Valerio De Maria
	 */
	public void chiudiConnessione() {

		try {
			out.close();
			socket.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE,"errore in chiusura socket", e);
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
			LOG.log(Level.SEVERE,"errore in invio mossa pecora", e);
		}

	}

	public void inviaRichiestaMossa(List<MosseEnum> mosseDisponibili){
		try {
			// richiedo una mossa
			out.reset();
			out.writeObject(ComandiSocket.RICHIESTA_DI_MOSSA);
			out.flush();
			// invio la lista di mosse disponibili
			out.reset();
			out.writeObject(mosseDisponibili);
			out.flush();

		} catch (IOException e) {
			LOG.log(Level.SEVERE,"errore in comunicazione richiesta mossa", e);
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

	public void comunicaTurno(List<Pecora> pecore,int turno) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.INIZIO_TURNO);
			out.flush();
			
			out.reset();
			out.writeObject(pecore);
			out.flush();
			
			out.reset();
			out.writeInt(turno);
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void inviaRichiestaPosizionamento(List<Integer> stradeDisponibili) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.RICHIESTA_POSIZIONE_PASTORE);
			out.flush();
			out.reset();
			out.writeObject(stradeDisponibili);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		try {
			
			out.reset();
			out.writeObject(ComandiSocket.MOVIMENTO_PASTORE);
			out.flush();
			
			out.reset();
			out.writeInt(posizione);
			out.flush();
			
			out.reset();
			out.writeObject(giocatore);
			out.flush();
			
			out.reset();
			out.writeInt(pastore);
			out.flush();
		} catch (IOException e) {
			LOG.log(Level.SEVERE,"errore in comunicazione movimento pastore", e);
		}
		
	}

	public void comunicaAcquistaTessera(TipoTerreno terreno, String giocatore,
			int pastore) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.ACQUISTO_TESSERA);
			out.flush();
			
			out.reset();
			out.writeObject(terreno);
			out.flush();
			
			out.reset();
			out.writeObject(giocatore);
			out.flush();
			
			out.reset();
			out.writeInt(pastore);
			out.flush();
		} catch (IOException e) {
			LOG.log(Level.SEVERE,"errore in comunicazione acquisto tessera", e);
		}
		
	}

	public void comunicaAbbattimento(int regione, int pecora, String giocatore,
			int pastore) {
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
			
			out.reset();
			out.writeObject(giocatore);
			out.flush();
			
			out.reset();
			out.writeInt(pastore);
			out.reset();

		} catch (IOException e) {
			LOG.log(Level.SEVERE,"errore in comunicazione abbattimento", e);
		}

		
	}

	public void comunicaAccoppiamento(int regione, String giocatore, int pastore) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.ACCOPPIAMENTO);
			out.flush();
			
			out.reset();
			out.writeInt(regione);
			out.flush();
			
			out.reset();
			out.writeObject(giocatore);
			out.flush();
			
			out.reset();
			out.writeInt(pastore);
			out.flush();
		} catch (IOException e) {
			LOG.log(Level.SEVERE,"errore in comunicazione accoppiamento", e);
		}

		
	}

	public void comunicaMovimentoPecora(int pecora, int strada,
			String giocatore, int pastore) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.MOVIMENTO_PECORA);
			out.flush();
			
			out.reset();
			out.writeInt(pecora);
			out.flush();
			
			out.reset();
			out.writeInt(strada);
			out.flush();
			
			out.reset();
			out.writeObject(giocatore);
			out.flush();
			
			out.reset();
			out.writeInt(pastore);
			out.flush();
		} catch (IOException e) {
			LOG.log(Level.SEVERE,"errore in comunicazione movimento pecora", e);
		}
		
	}


	public void comunicaPosizionamentoPastore(int turno, int pastore,
			int posizione) {
		try {
			
			out.reset();
			out.writeObject(ComandiSocket.POSIZIONAMENTO_PASTORE);
			out.flush();
			
			out.reset();
			out.writeInt(turno);
			out.flush();
			
			out.reset();
			out.writeInt(pastore);
			out.flush();
			
			out.reset();
			out.writeInt(posizione);
			out.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void aggiornaTurno(String giocatore,List<Pecora> pecore) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.CAMBIO_TURNO);
			out.flush();
			
		    out.reset();
		    out.writeObject(giocatore);
		    out.flush();
		    
		    out.reset();
		    out.writeObject(pecore);
		    out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void comunicaMossaSbagliata() {
		try {
			out.reset();
			out.writeObject(ComandiSocket.MOSSA_SBAGLIATA);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void mossaCorretta() {
		try {
			out.reset();
			out.writeObject(ComandiSocket.MOSSA_CORRETTA);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inviaTesseraIniziale(Tessera tesseraIniziale) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.TESSERA_INIZIALE);
			out.flush();
			
			out.reset();
			out.writeObject(tesseraIniziale);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void inviaPunteggi(List<Integer> punteggiFinali, List<String> nomi) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.PUNTEGGI);
			out.flush();
			
			out.reset();
			out.writeObject(punteggiFinali);
			out.flush();
			
			out.reset();
			out.writeObject(nomi);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void comunicaNumeroRecinti(int recinti) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.NUMERO_RECINTI);
			out.flush();
			
			out.reset();
			out.writeInt(recinti);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void comunicaDenaro(List<Integer> denaroPastori) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.AGGIORNAMENTO_DENARO);
			out.flush();
			
			out.reset();
			out.writeObject(denaroPastori);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void comunicaMovimentoLupo(int nuovaPosizione) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.MOVIMENTO_LUPO);
			out.flush();
			
			out.reset();
			out.writeInt(nuovaPosizione);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void aggiornamento(List<Pecora> pecore, int posPecoraNera,
			int posLupo, List<Pastore> pastori) {

try {
	out.reset();
	out.writeObject(ComandiSocket.AGGIORNAMENTO);
	out.flush();
	
	out.reset();
	out.writeObject(pecore);
	out.flush();
	
	out.reset();
	out.writeInt(posPecoraNera);
	out.flush();
	
	out.reset();
	out.writeInt(posLupo);
	out.flush();
	
	out.reset();
	out.writeObject(pastori);
	out.flush();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		
	}

	public void comunicaStrade(List<Strada> strade) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.INVIO_STRADE);
			out.flush();
			
			out.reset();
			out.writeObject(strade);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void comunicaSpostamentoPecoraNera(int strada, String giocatore,
			int pastore) {
		try {
			out.reset();
			out.writeObject(ComandiSocket.SPOSTAMENTO_PECORA_NERA);
			out.flush();
			
			out.reset();
			out.writeInt(strada);
			out.flush();
			
			out.reset();
			out.writeObject(giocatore);
			out.flush();
			
			out.reset();
			out.writeInt(pastore);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
