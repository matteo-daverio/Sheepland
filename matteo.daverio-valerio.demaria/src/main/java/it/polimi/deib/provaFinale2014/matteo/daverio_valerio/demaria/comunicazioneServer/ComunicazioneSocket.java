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
import java.net.Socket;
import java.util.List;

/**
 * classe che parla con client socket
 * 
 * @author Valerio De Maria
 * 
 */
public class ComunicazioneSocket implements InterfacciaComunicazioneClient {

	private Socket socket;
	private String nome;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	// costruttore
	public ComunicazioneSocket(Socket socket,ObjectInputStream in,ObjectOutputStream out,String nome) {

		this.socket = socket;
		this.in=in;
		this.out=out;
		this.nome=nome;
	}

	public void inviaPartita(Partita partita) {

		try {
			out.reset();
			out.writeObject(ComandiSocket.INVIO_PARTITA);
			out.flush();
			out.reset();
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
			out.reset();
			out.writeObject(ComandiSocket.MOVIMENTO_PECORA_NERA);
			out.flush();
			out.reset();
			out.writeInt(nuovaPosizione);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Mossa riceviMossa(List<Mosse> mosseDisponibili) {
		return null;
	}

	public void comunicaMovimentoPastore(int posizione) {
		
		try {
			out.reset();
			out.writeObject(ComandiSocket.MOVIMENTO_PASTORE);
			out.flush();
			out.writeInt(posizione);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void comunicaAcquistaTessera(TipoTerreno terreno) {
		
		try {
			out.reset();
			out.writeObject(ComandiSocket.ACQUISTO_TESSERA);
			out.flush();
			out.reset();
			out.writeObject(terreno);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void comunicaAbbattimento(int regione,int pecora) {
		
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
			e.printStackTrace();
		}

	}

	public void comunicaAccoppiamento(int regione) {
		
		try {
			out.reset();
			out.writeObject(ComandiSocket.ACCOPPIAMENTO);
			out.flush();
			out.reset();
			out.writeInt(regione);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

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
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getNome() {
		return nome;
	}

	public String getTipoConnessione() {
		return "socket";
	}

	public void setSocket(Socket socket) {
		this.socket=socket;
	}

	public boolean ping() {
		try {
			out.reset();
			out.writeObject(ComandiSocket.PING);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (in.equals(ComandiSocket.PONG)){
			return true;
		}
		else{
			return false;
		}
	}

}
