package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;


public interface InterfacciaComunicazioneClient {
	
	public void inviaPartita(Partita partita);

	public void chiudiConnessione();
}
