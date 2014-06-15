package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;


import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.io.IOException;


public interface InterfacciaComunicazioneToServer {


	public boolean effettuaLogin(String nome,String password) throws IOException;
	
	public void riceviAggiornamenti()throws IOException,GameException;
	
	public void inviaPosizionePastore(int posizione);
	
	public void inviaMossa(Mossa mossa);
	
}
