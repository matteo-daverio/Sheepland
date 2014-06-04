package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Mosse;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * classe che parla con client socket
 * 
 * @author Valerio De Maria
 * 
 */
public class ComunicazioneSocket implements InterfacciaComunicazioneClient {

	private Socket socket;
	private String line;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	// costruttore
	public ComunicazioneSocket(Socket socket) {

		this.socket = socket;

		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void inviaPartita(Partita partita) {

		try {
			out.writeObject(ComandiSocket.INVIO_PARTITA);
			out.writeObject(partita);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void chiudiConnessione() {

		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void comunicaMovimentoPecoraNera(int nuovaPosizione) {
		
		try {
			out.writeObject(ComandiSocket.MOVIMENTO_PECORA_NERA);
			out.writeInt(nuovaPosizione);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Mossa riceviMossa(ArrayList<Mosse> mosseDisponibili) {
		return null;
	}

	public void comunicaMovimentoPastore(int posizione) {
		
		try {
			out.reset();
			out.writeObject(ComandiSocket.MOVIMENTO_PASTORE);
			out.writeInt(posizione);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void comunicaAcquistaTessera(TipoTerreno terreno) {
		
		try {
			out.writeObject(ComandiSocket.ACQUISTO_TESSERA);
			out.writeObject(terreno);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void comunicaAbbattimento(int regione,int pecora) {
		
		try {
			out.writeObject(ComandiSocket.ABBATTIMENTO);
			out.writeInt(regione);
			out.flush();
			out.writeInt(pecora);
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void comunicaAccoppiamento(int regione) {
		
		try {
			out.writeObject(ComandiSocket.ACCOPPIAMENTO);
			out.writeInt(regione);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void comunicaMovimentoPecora(int pecora, Strada strada) {
		
		try {
			out.writeObject(ComandiSocket.MOVIMENTO_PECORA);
			out.writeInt(pecora);
			out.flush();
			out.writeObject(strada);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
