package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import java.util.ArrayList;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;

public interface InterfacciaGrafica {
	
	//il controllore fa partire la grafica
	public void start(ControllorePartitaClient controllore);
	
	//visualizza la posizione iniziale delle pecore
	public void inizializzaPecore(ArrayList<Pecora> arrayList);
	
	//richiede il posizionamento di un pastore
	public void posizionaPastore ();
	
	//comunica l'inizio del turno
	public void iniziaTurno ();

	//comunica il movimento della pecora nera ad inizio turno
	public void muoviPecoraNera(int posizione);
	
}
