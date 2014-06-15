package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;


import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
	private ControllorePartita gameManager;

	// costruttore
	public ComunicazioneRMI(String nome, InterfacciaClientRMI client,
			ControllorePartita gameManager) {

		this.nome = nome;
		this.client = client;
		this.gameManager = gameManager;

		try {
			// cerco il registry del server
			Registry registry = LocateRegistry.getRegistry("localhost",
					Costanti.PORTA_RMI);

			// creo la classe su cui il client può chiamare i metodi
			InterfacciaServerRMI s = new RicezioneRMI(gameManager);

			// esporto l'interfaccia server
			InterfacciaServerRMI stubServerRMI = (InterfacciaServerRMI) UnicastRemoteObject
					.exportObject(s, 0);

			client.riceviStubServer(stubServerRMI);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
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

	public void comunicaMovimentoPastore(int posizione, String giocatore,
			int pastore) {
		try {
			client.movimentoPastore(posizione, giocatore, pastore);
		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (GameException e) {

			e.printStackTrace();
		}

	}

	public void comunicaAcquistaTessera(TipoTerreno terreno, String giocatore,
			int pastore) {
		try {
			client.acquistoTessera(terreno, giocatore, pastore);
		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (GameException e) {

			e.printStackTrace();
		}

	}

	public void comunicaAbbattimento(int regione, int pecora, String giocatore,
			int pastore) {
		try {
			client.abbattimento(regione, pecora, giocatore, pastore);
		} catch (GameException e) {

			e.printStackTrace();
		} catch (RemoteException e) {

			e.printStackTrace();
		}

	}

	public void comunicaAccoppiamento(int regione, String giocatore, int pastore) {
		try {
			client.accoppiamento(regione, giocatore, pastore);
		} catch (GameException e) {
			e.printStackTrace();
		} catch (RemoteException e) {

			e.printStackTrace();
		}

	}

	public void comunicaMovimentoPecora(int pecora, int strada,
			String giocatore, int pastore) {
		try {
			client.movimentoPecora(pecora, strada, giocatore, pastore);
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

	public void inviaDatiGiocatori(List<String> nomi, List<Integer> soldi) {
		try {
			client.riceviDatiGiocatori(nomi, soldi);
			;
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

	public void comunicaPosizionamentoPastore(int turno, int pastore,
			int posizione) {
		try {
			client.riceviAggiornamentoPosizionamentoPastore(turno, pastore,
					posizione);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void comunicaTurno(List<Pecora> pecore,int turno) {
		try {
			client.inizioTurno(pecore,turno);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void aggiornaTurno(String giocatore,List<Pecora> pecore) {
		try {
			client.cambioTurno(giocatore,pecore);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	public void inviaPunteggi(List<Integer> punteggiFinali, List<String> nomi) {
		try {
			client.punteggiFinali(punteggiFinali, nomi);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void comunicaDenaro(List<Integer> denaroPastori) {
		try {
			client.comunicaDenaro(denaroPastori);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void comunicaNumeroRecinti(int recinti) {
		// client.comunicaNumeroRecinti(recinti);

	}

/////////// NUOVI METODI ////////////////
	
	public void inviaRichiestaMossa(List<MosseEnum> mosseDisponibili) {
		try {
			client.richiestaMossa(mosseDisponibili);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void inviaRichiestaPosizionamento(List<Integer> stradeDisponibili) {
		try {
			client.richiestaPosizionamento(stradeDisponibili);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void comunicaMovimentoLupo(int nuovaPosizione) {
		try {
			client.movimentoLupo(nuovaPosizione);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
