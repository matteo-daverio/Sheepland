package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * classe che parla con client socket
 * @author Valerio De Maria
 *
 */
public class ComunicazioneSocket implements InterfacciaComunicazioneClient{
	
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private String line;
	
	//costruttore
	public ComunicazioneSocket(Socket socket){
		
		this.socket=socket;
		 
		try{ 
			 
			 in = new Scanner(socket.getInputStream()); 
			 out = new PrintWriter(socket.getOutputStream());
		  
		  }catch(IOException e){ System.err.println(e.getMessage()); }
		
	}
	
	public void inviaPartita(Partita partita){
		
	}
	
	public void chiudiConnessione(){
		
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void comunicaMovimentoPecoraNera(int nuovaPosizione) {
		
	}

	public Mossa riceviMossa() {
		//TODO
		return null;
	}

	public void comunicaMovimentoPastore(int posizione) {
		// TODO 
		
	}

	public void comunicaAcquistaTessera(TipoTerreno terreno) {
		// TODO 
		
	}

	public void comunicaAbbattimento(int regione) {
		// TODO Auto-generated method stub
		
	}

	public void comunicaAccoppiamento(int regione) {
		// TODO 
		
	}

	public void comunicaMovimentoPecora(int pecora, Strada strada) {
		// TODO
		
	}
	

}
