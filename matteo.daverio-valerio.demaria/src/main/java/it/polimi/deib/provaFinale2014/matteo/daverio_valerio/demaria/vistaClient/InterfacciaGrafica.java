package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import java.util.ArrayList;
import java.util.List;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
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
	public void posizionaPastore ();
	
	//comunica l'inizio del turno
	public void iniziaTurno ();

	//comunica il movimento della pecora nera ad inizio turno
	public void muoviPecoraNera(int posizione);
	
}
