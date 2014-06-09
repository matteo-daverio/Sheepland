package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import java.io.IOException;
import java.net.UnknownHostException;

public interface InterfacciaComunicazioneClient {


	public boolean effettuaLogin(String nome,String password) throws IOException;
}
