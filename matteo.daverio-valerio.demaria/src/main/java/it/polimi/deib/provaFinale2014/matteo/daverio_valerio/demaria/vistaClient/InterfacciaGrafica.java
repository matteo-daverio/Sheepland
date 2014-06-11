package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import java.util.ArrayList;
import java.util.List;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;

public interface InterfacciaGrafica {
	
	//il controllore fa partire la grafica
	public void start();
	
	//setta i nomi dei giocatori
	public void nomiGiocatori(List<String> nomi);
	
	//setta i soldi iniziali dei pastori
	public void soldiPastori(List<Integer>soldi);
	
	//setta le tessere iniziali dei pastori
	public void tessereInizialiPastori(List<Tessera> tessereIniziali);
	
	//visualizza la posizione iniziale delle pecore
    public void settaPecore(List<Pecora> pecore);
	
	//richiede il posizionamento di un pastore
	public int posizionaPastore ();
	
	public void posizionamentoPastoreCorretto();
	
	public void posizionamentoPastoreErrato();
	
	public void aggiornamentoPosizionePastore(int turno,int pastore,int posizione);
	
	public void visualizzaMosseDisponibili(List<MosseEnum> mosseDisponibili);
	
	//AGGIORNAMENTI MOSSE AVVERSARI
	
	public void movimentoPastore(int posizione,String giocatore,int pastore);
	public void acquistoTessera(TipoTerreno terreno,String giocatore,int pastore);
	public void movimentoPecora(int pecora, int strada,String giocatore, int pastore);
	public void abbattimento(int regione, int pecora,String giocatore, int pastore);
	public void accoppiamento(int regione,String giocatore, int pastore);
	
	//comunica l'inizio del turno
	public void iniziaTurno ();
	
	//comunica di chi è il turno attuale
	public void cambioTurno(String giocatore);

	//comunica il movimento della pecora nera ad inizio turno
	public void muoviPecoraNera(int posizione);
	
	public int selezionaPastore(int primo,int secondo);
	
	public int chiediMossa(int max);
	
	//PER ESECUZIONE MOSSA
	
	public int scegliRegione();
	public int scegliStrada();
	public int scegliPecora();
}
