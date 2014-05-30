package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
		// TODO Auto-generated method stub
		
	}
	

}
