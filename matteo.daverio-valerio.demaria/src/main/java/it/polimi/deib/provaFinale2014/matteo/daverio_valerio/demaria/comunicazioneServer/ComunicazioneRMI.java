package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

/**
 * classe che comunica con client RMI
 * 
 * @author Valerio De Maria
 * 
 */
public class ComunicazioneRMI implements InterfacciaComunicazioneToClient {

	private String nome;
	private InterfacciaClientRMI client;

	// costruttore
	public ComunicazioneRMI(String nome,
			InterfacciaClientRMI client) {

		this.nome = nome;
		this.client = client;
	
	}

	public Mossa riceviMossa(List<MosseEnum> mosseDisponibili) {
		try {
			return client.inviaMossa(mosseDisponibili);
		} catch (RemoteException e) {
			//TODO devo gestire il fatto che il giocatore si è disconnesso
			e.printStackTrace();
		}
		return null;
	}

	public void chiudiConnessione() {
		// TODO
	}

	public void comunicaMovimentoPecoraNera(int nuovaPosizione) {

		try {
			client.movimentoPecoraNera(nuovaPosizione);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void comunicaMovimentoPastore(int posizione,String giocatore,int pastore) {
		try {
			client.movimentoPastore(posizione,giocatore,pastore);
		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (GameException e) {

			e.printStackTrace();
		} 

	}

	public void comunicaAcquistaTessera(TipoTerreno terreno,String giocatore,int pastore) {
		try {
			client.acquistoTessera(terreno,giocatore,pastore);
		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (GameException e) {

			e.printStackTrace();
		} 

	}

	public void comunicaAbbattimento(int regione, int pecora, String giocatore, int pastore) {
		try {
			client.abbattimento(regione, pecora,giocatore,pastore);
		} catch (GameException e) {

			e.printStackTrace();
		} catch (RemoteException e) {

			e.printStackTrace();
		}

	}

	public void comunicaAccoppiamento(int regione,String giocatore, int pastore) {
		try {
			client.accoppiamento(regione,giocatore,pastore);
		} catch (GameException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}

	}

	public void comunicaMovimentoPecora(int pecora, int strada,String giocatore,int pastore) {
		try {
			client.movimentoPecora(pecora, strada,giocatore,pastore);
		} catch (GameException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public String getNome() {
		return nome;
	}

	public String getTipoConnessione() {
		return "rmi";
	}

	public void setSocket(Socket socket) {
		
	}

	public void comunicaFaseFinale() {
		try {
			client.faseFinale();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void inviaDatiGiocatori(List<String> nomi,List<Integer> soldi) {
		try {
			client.riceviDatiGiocatori(nomi, soldi);;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void comunicaPecore(List<Pecora> pecore) {
		try {
			client.riceviPecore(pecore);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public int chiediPosizionamentoPastore(List<Integer> stradeDisponibili) {

		try {
			return client.posizionaPastore(stradeDisponibili);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public void comunicaPosizionamentoPastore(int turno, int pastore,
			int posizione) {
		try {
			client.riceviAggiornamentoPosizionamentoPastore(turno, pastore, posizione);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void comunicaTurno() {
		try {
			client.inizioTurno();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void aggiornaTurno(String giocatore) {
		try {
			client.cambioTurno(giocatore);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public int selezionaPastore(int primo, int secondo) {
		
		try {
			return client.selezionaPastore(primo,secondo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public void comunicaMossaSbagliata() {
		try {
			client.mossaSbagliata();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public void inviaTesseraIniziale(Tessera tesseraIniziale) {
		try {
			client.tesseraIniziale(tesseraIniziale);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void mossaCorretta() {
		try {
			client.mossaCorretta();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void inviaPunteggi(List<Integer> punteggiFinali,List<String> nomi) {
		try {
			client.punteggiFinali(punteggiFinali,nomi);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void comunicaDenaro(int denaro) {
		//client.comunicaDenaro(denaro);
		
	}

	public void comunicaNumeroRecinti(int recinti) {
		//client.comunicaNumeroRecinti(recinti);
		
	}


}
