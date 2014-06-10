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
public class ComunicazioneRMI implements InterfacciaComunicazioneClient {

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

	public void inviaPartita(Partita partita) {

		try {
			client.riceviPartita(partita);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

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

	public void comunicaMovimentoPastore(int posizione) {
		try {
			client.movimentoPastore(posizione);
		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (GameException e) {

			e.printStackTrace();
		} 

	}

	public void comunicaAcquistaTessera(TipoTerreno terreno) {
		try {
			client.acquistoTessera(terreno);
		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (GameException e) {

			e.printStackTrace();
		} 

	}

	public void comunicaAbbattimento(int regione, int pecora) {
		try {
			client.abbattimento(regione, pecora);
		} catch (GameException e) {

			e.printStackTrace();
		} catch (RemoteException e) {

			e.printStackTrace();
		}

	}

	public void comunicaAccoppiamento(int regione) {
		try {
			client.accoppiamento(regione);
		} catch (GameException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}

	}

	public void comunicaMovimentoPecora(int pecora, Strada strada) {
		try {
			client.movimentoPecora(pecora, strada);
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

	public void comunicaCambioTurno() {
		try {
			client.cambioTurno();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void comunicaFaseFinale() {
		
		
	}

	public void comunicaInizioTurno() {
		try {
			client.inizioTurno();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
/*
	public int chiediPosizionamentoPastore() {
		try {
			return client.posizionaPastore();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	*/

	public void inviaDatiGiocatori(List<String> nomi,List<Integer> soldi, List<Tessera> tessereIniziali) {
		try {
			client.riceviDatiGiocatori(nomi, soldi, tessereIniziali);;
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


}
