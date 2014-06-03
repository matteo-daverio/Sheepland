package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.util.ArrayList;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Mosse;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

public interface InterfacciaComunicazioneClient {

	public void inviaPartita(Partita partita);

	public void chiudiConnessione();

	public void comunicaMovimentoPecoraNera(int nuovaPosizione);

	public Mossa riceviMossa(ArrayList<Mosse> mosseDisponibili);

	public void comunicaMovimentoPastore(int posizione);

	public void comunicaAcquistaTessera(TipoTerreno terreno);

	public void comunicaAbbattimento(int regione);

	public void comunicaAccoppiamento(int regione);

	public void comunicaMovimentoPecora(int pecora, Strada strada);

}
