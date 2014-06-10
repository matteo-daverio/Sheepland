package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;

import java.util.ArrayList;

public class GuiImpl implements InterfacciaGrafica {

	ControllorePartitaClient controllorePartita;
	Map mappa;
	
	public GuiImpl(ControllorePartitaClient controllorePartita) {
		this.controllorePartita= controllorePartita;
	}
	
	public void start() {
		LoginScreen loginScreen=new LoginScreen(controllorePartita,this);
		loginScreen.createAndShowGui();
	}

	public void inizializzaPecore(ArrayList<Pecora> arrayList) {
		// TODO Auto-generated method stub
		
	}

	public void posizionaPastore() {
		// TODO Auto-generated method stub
		
	}

	public void iniziaTurno() {
		// TODO Auto-generated method stub
		
	}

	public void muoviPecoraNera(int posizione) {
		// TODO Auto-generated method stub
		
	}
	
	public void creaMappa(Map mappa) {
		this.mappa=mappa;
	}

}
