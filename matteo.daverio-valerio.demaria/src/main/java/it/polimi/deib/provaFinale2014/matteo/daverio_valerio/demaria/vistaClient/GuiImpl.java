package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.ControllorePartitaClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;

import java.util.ArrayList;
import java.util.List;

public class GuiImpl implements InterfacciaGrafica {

	ControllorePartitaClient controllorePartita;
	Map mappa;
	LoginScreen loginScreen;
	List<Pecora> pecore=new ArrayList<Pecora>();
	List<Strada> strade=new ArrayList<Strada>();

	public GuiImpl(ControllorePartitaClient controllorePartita) {
		this.controllorePartita = controllorePartita;
	}

	public void start() {
		loginScreen = new LoginScreen(controllorePartita, this);
		loginScreen.createAndShowGui();
	}

	public void nomiGiocatori(List<String> nomi) {
		mappa.numeroGiocatori(nomi.size());
		for (int i = 0; i < nomi.size(); i++) {
			mappa.aggiornaNome(nomi.get(i), i);
		}
	}

	public void soldiPastori(List<Integer> soldi) {
		for (int i = 0; i < soldi.size(); i++) {
			mappa.impostaDenaro(soldi.get(i), i);
		}
	}

	public void tesseraIniziale(Tessera tesseraIniziale) {
		mappa.tesseraIniziale(tesseraIniziale.getTipo());
	}

	public void settaPecore(List<Pecora> pecore) {
		this.pecore=pecore;
		mappa.inizializzaPecore(pecore);
	}

	public void richiestaPosizionamentoPastore() {
		mappa.richiestaPosizionamentoPastore();
	}

	public void muoviPecoraNera(int posizione) {
		mappa.muoviPecoraNera(posizione);
	}

	public void faseFinale() {
		mappa.faseFinale();
	}

	public void punteggiFinali(List<Integer> punteggiFinali, List<String> nomi) {
		mappa.punteggiFinali(punteggiFinali, nomi);
	}

	public void comunicaNumeroRecinti(int recinti) {
		mappa.aggiornaRecinti(recinti);
	}

	public void creaMappa(Map mappa) {
		this.mappa = mappa;
		mappa.impostaGuiImpl(this);
	}

	public void richiestaMossa(List<MosseEnum> mosseDisponibili) {
		mappa.richiestaMossa();
	}

	public void posizionamentoPastoreCorretto() {
		mappa.posizionamentoCorretto();
	}

	public void posizionamentoPastoreErrato() {
		mappa.posizionamentoSbagliato();
	}

	public void aggiornamentoPosizionePastore(int turno, int pastore,
			int posizione) {
		mappa.muoviPastoreAvversario(pastore, posizione);
	}

	public void movimentoPastore(int posizione, String giocatore, int pastore) {
		mappa.muoviPastoreAvversario(pastore, posizione,giocatore);
	}

	public void acquistoTessera(TipoTerreno terreno, String giocatore,
			int pastore) {
		mappa.notificaAcquistoTessera(giocatore, terreno);
	}

	public void movimentoPecora(int pecora, int strada, String giocatore,
			int pastore) {
		// TODO pecora è l'indice dell'array di pecore
		Pecora ovino=pecore.get(pecora);
		int tipoPecora=ovino.getTipoPecora();
		int regionePartenza=ovino.getPosizione();
		Strada posizionePastore=strade.get(strada);
		int regioneArrivo;
		if(posizionePastore.getRegioneDestra()==regionePartenza){
			regioneArrivo=posizionePastore.getRegioneSinistra();
		} else {
			regioneArrivo=posizionePastore.getRegioneDestra();
		}
		
		mappa.muoviPecora(tipoPecora, regionePartenza, regioneArrivo);
	}

	public void abbattimento(int regione, int pecora, String giocatore,
			int pastore) {
		mappa.abbattiPecora(regione,giocatore,pecore.get(pecora).getTipoPecora());
	}

	public void accoppiamento(int regione, String giocatore, int pastore) {
		mappa.aggiungiPecora(regione);
	}

	public void muoviLupo(int posizione) {
		mappa.muoviLupo(posizione);
	}

	public void mossaSbagliata() {
		mappa.mossaSbagliata();
	}

	public void mossaCorretta() {
		mappa.mossaCorretta();
	}

	public void comunicaDenaro(List<Integer> denaroPastori) {
		for (int i = 0; i < denaroPastori.size(); i++) {
			mappa.impostaDenaro(denaroPastori.get(i), i);
		}
	}

	public void iniziaTurno(List<Pecora> pecore, int turno) {
		this.pecore=pecore;
		mappa.inizializzaPecore(pecore);
		mappa.iniziaTurno(turno);
	}

	public void cambioTurno(String giocatore, List<Pecora> pecore) {
		this.pecore=pecore;
		mappa.inizializzaPecore(pecore);
		mappa.cambioTurno(giocatore);
	}
	

	public void riceviStrade(List<Strada> strade) {
		this.strade=strade;
	}

	public void aggiornamentoPostDisconnessione(List<Pecora> pecore,
			int posPecoraNera, int posLupo, List<Pastore> pastori) {
		mappa.inizializzaPecore(pecore);
		mappa.muoviPecoraNera(posPecoraNera);
		mappa.muoviLupo(posLupo);
		for(int i=0;i<pastori.size();i++){
			mappa.collocaPastoreAvversario(pastori.get(i).getPosizione(), i);
		}
	}

	public void segnalaDisconnessione() {
		mappa.segnalaDisconnessione();
	}

	public void muoviPecora(int turno,int posizionePartenza, int posizioneArrivo, int tipoPecora) {
		int indicePecora=-1;
		int indiceStrada=-1;
		for(int i=0;i<pecore.size();i++) {
			if(pecore.get(i).getPosizione()==posizionePartenza && pecore.get(i).getTipoPecora()==tipoPecora){
				indicePecora=i;
				break;
			}
		}
		for(int i=0;i<strade.size();i++) {
			if((strade.get(i).getRegioneDestra()==posizionePartenza && strade.get(i).getRegioneSinistra()==posizioneArrivo) || (strade.get(i).getRegioneDestra()==posizioneArrivo && strade.get(i).getRegioneSinistra()==posizionePartenza)){
				indiceStrada=i;
				break;
			}
		}
		controllorePartita.muoviPecora(indiceStrada, indicePecora, turno);
	}
	
}
