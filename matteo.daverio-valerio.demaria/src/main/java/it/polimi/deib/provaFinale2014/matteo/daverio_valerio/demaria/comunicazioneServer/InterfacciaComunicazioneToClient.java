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

public interface InterfacciaComunicazioneToClient {

	public void chiudiConnessione();

	public void comunicaMovimentoPecoraNera(int nuovaPosizione);

	public Mossa riceviMossa(List<MosseEnum> mosseDisponibili);
	
	
    //AGGIORNANO I CLIENTS CHE NON SONO DEL TURNO
	public void comunicaMovimentoPastore(int posizione, String giocatore, int pastore);

	public void comunicaAcquistaTessera(TipoTerreno terreno, String giocatore,int pastore);

	public void comunicaAbbattimento(int regione,int pecora,String giocatore,int pastore);

	public void comunicaAccoppiamento(int regione,String giocatore,int pastore);

	public void comunicaMovimentoPecora(int pecora, int strada,String giocatore, int pastore);
	
	
	
	public void comunicaPecore(List<Pecora> pecore);
	
	public int chiediPosizionamentoPastore(List<Integer> stradeDisponibili);
	
	public void comunicaPosizionamentoPastore(int turno, int pastore, int posizione);
	
	public void comunicaFaseFinale();
	
	public String getNome();
	
	public String getTipoConnessione();
	
	public void setSocket(Socket socket);
	
	public void inviaDatiGiocatori(List<String> nomi,List<Integer> soldi,List<Tessera> tessereIniziali);
	
	public void comunicaTurno();
	
	public void aggiornaTurno(String giocatore);
	
	public int selezionaPastore(int primo, int secondo);

}
