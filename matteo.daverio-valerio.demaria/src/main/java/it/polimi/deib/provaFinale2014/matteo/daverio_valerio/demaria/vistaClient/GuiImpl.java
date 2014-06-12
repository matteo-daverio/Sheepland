package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;

import java.util.List;

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

	public void creaMappa(Map mappa) {
		this.mappa=mappa;
	}

	public void nomiGiocatori(List<String> nomi) {
		mappa.numeroGiocatori(nomi.size());
		for(int i=0;i<nomi.size();i++) {
			mappa.aggiornaNome(nomi.get(i), i);
		}
	}

	public void soldiPastori(List<Integer> soldi) {
		for(int i=0;i<soldi.size();i++) {
			mappa.impostaDenaro(soldi.get(i), i);
		}
	}

	public void tessereInizialiPastori(List<Tessera> tessereIniziali) {
		// TODO Auto-generated method stub
		
	}

	public void settaPecore(List<Pecora> pecore) {
		// TODO Auto-generated method stub
		
	}

	public int posizionaPastore() {
		return mappa.posizionaPastore();
	}

	public void posizionamentoPastoreCorretto() {
		mappa.posizionePastoreCorretta();
	}

	public void posizionamentoPastoreErrato() {
		mappa.posizionePastoreErrata();
	}

	public void aggiornamentoPosizionePastore(int turno, int pastore,
			int posizione) {
		// TODO Auto-generated method stub
		
	}

	public void visualizzaMosseDisponibili(List<MosseEnum> mosseDisponibili) {
		// TODO Auto-generated method stub
		
	}

	public void movimentoPastore(int posizione, String giocatore, int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void acquistoTessera(TipoTerreno terreno, String giocatore,
			int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void movimentoPecora(int pecora, int strada, String giocatore,
			int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void abbattimento(int regione, int pecora, String giocatore,
			int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void accoppiamento(int regione, String giocatore, int pastore) {
		// TODO Auto-generated method stub
		
	}

	public void iniziaTurno() {
		mappa.iniziaTurno();
	}

	public void cambioTurno(String giocatore) {
		// TODO Auto-generated method stub
		
	}

	public void muoviPecoraNera(int posizione) {
		// TODO Auto-generated method stub
		
	}

	public int selezionaPastore(int primo, int secondo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int chiediMossa(int max) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int scegliRegione() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int scegliStrada() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int scegliPecora() {
		// TODO Auto-generated method stub
		return 0;
	}


}
