package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Mosse;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ServerApplication;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.InterfacciaClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

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

	public Mossa riceviMossa(ArrayList<Mosse> mosseDisponibili) {
		try {
			return client.inviaMossa(mosseDisponibili);
		} catch (RemoteException e) {
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
		} catch (NoMovementException e) {

			e.printStackTrace();
		} catch (NoMoneyException e) {

			e.printStackTrace();
		} catch (InvalidMovementException e) {

			e.printStackTrace();
		}

	}

	public void comunicaAcquistaTessera(TipoTerreno terreno) {
		try {
			client.acquistoTessera(terreno);
		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (NoMoreCardsException e) {

			e.printStackTrace();
		} catch (NoMoneyException e) {

			e.printStackTrace();
		} catch (IllegalShireTypeException e) {

			e.printStackTrace();
		}

	}

	public void comunicaAbbattimento(int regione, int pecora) {
		try {
			client.abbattimento(regione, pecora);
		} catch (NoSheepInShireException e) {

			e.printStackTrace();
		} catch (NoMoneyException e) {

			e.printStackTrace();
		} catch (IllegalShireException e) {

			e.printStackTrace();
		}

	}

	public void comunicaAccoppiamento(int regione) {
		try {
			client.accoppiamento(regione);
		} catch (IllegalShireException e) {
			e.printStackTrace();
		} catch (CannotProcreateException e) {
			e.printStackTrace();
		}

	}

	public void comunicaMovimentoPecora(int pecora, Strada strada) {
		try {
			client.movimentoPecora(pecora, strada);
		} catch (IllegalStreetException e) {
			e.printStackTrace();
		} catch (IllegalShireException e) {
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

	public boolean ping() {
		return client.pong();
	}
}
