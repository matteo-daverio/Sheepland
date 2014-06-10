package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.net.Socket;
import java.util.List;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
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
	
	public void comunicaInizioTurno();
	
	public void comunicaPecore(List<Pecora> pecore);
	
	//public int chiediPosizionamentoPastore();
	
	public void comunicaFaseFinale();
	
	public String getNome();
	
	public String getTipoConnessione();
	
	public void setSocket(Socket socket);
	
	public void inviaDatiGiocatori(List<String> nomi,List<Integer> soldi,List<Tessera> tessereIniziali);
	

}
