package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.net.Socket;
import java.util.List;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

public interface InterfacciaComunicazioneClient {

	public void inviaPartita(Partita partita);

	public void chiudiConnessione();

	public void comunicaMovimentoPecoraNera(int nuovaPosizione);

	public Mossa riceviMossa(List<MosseEnum> mosseDisponibili);

	public void comunicaMovimentoPastore(int posizione);

	public void comunicaAcquistaTessera(TipoTerreno terreno);

	public void comunicaAbbattimento(int regione,int pecora);

	public void comunicaAccoppiamento(int regione);

	public void comunicaMovimentoPecora(int pecora, Strada strada);
	
	public void comunicaCambioTurno();
	
	public void comunicaFaseFinale();
	
	public String getNome();
	
	public String getTipoConnessione();
	
	public void setSocket(Socket socket);
	

}
