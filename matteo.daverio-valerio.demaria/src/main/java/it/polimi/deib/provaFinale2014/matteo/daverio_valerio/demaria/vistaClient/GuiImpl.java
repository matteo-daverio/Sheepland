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
	List<Pecora> pecore = new ArrayList<Pecora>();
	List<Strada> strade = new ArrayList<Strada>();
	int posizionePecoraNera = 0;
	int numeroDiGiocatori;

	/**
	 * costruttore
	 * 
	 * @param controllorePartita
	 * @author Matteo Daverio
	 */
	public GuiImpl(ControllorePartitaClient controllorePartita) {
		this.controllorePartita = controllorePartita;
	}

	/**
	 * avvio grafica
	 * 
	 * @author Matteo Daverio
	 */
	public void start() {
		loginScreen = new LoginScreen(controllorePartita, this);
		loginScreen.createAndShowGui();
	}

	/**
	 * passo i nomi dei giocatori
	 * 
	 * @author Matteo Daverio
	 */
	public void nomiGiocatori(List<String> nomi) {
		mappa.numeroGiocatori(nomi.size());
		for (int i = 0; i < nomi.size(); i++) {
			mappa.aggiornaNome(nomi.get(i), i);
		}
	}

	/**
	 * passo i soldi dei giocatori
	 * 
	 * @author Matteo Daverio
	 */
	public void soldiPastori(List<Integer> soldi) {
		for (int i = 0; i < soldi.size(); i++) {
			mappa.impostaDenaro(soldi.get(i), i);
		}
	}

	/**
	 * passo la tessera iniziale
	 * 
	 * @author Matteo
	 */
	public void tesseraIniziale(Tessera tesseraIniziale) {
		mappa.tesseraIniziale(tesseraIniziale.getTipo());
	}

	/**
	 * setto le pecore iniziali
	 * 
	 * @author Matteo Daverio
	 */
	public void settaPecore(List<Pecora> pecore) {
		this.pecore = pecore;
		mappa.inizializzaPecore(pecore);
	}

	/**
	 * richiedo il posizionamento del pastore
	 * 
	 * @author Matteo Daverio
	 */
	public void richiestaPosizionamentoPastore() {
		mappa.richiestaPosizionamentoPastore();
	}

	/**
	 * passo il movimento della pecora nera
	 * 
	 * @author Matteo Daverio
	 */
	public void muoviPecoraNera(int posizione) {
		posizionePecoraNera = posizione;
		mappa.muoviPecoraNera(posizione);
	}

	/**
	 * avvio la fase finale
	 * 
	 * @author Matteo Daverio
	 */
	public void faseFinale() {
		mappa.faseFinale();
	}

	/**
	 * passo i punteggi finali
	 * 
	 * @author Matteo Daverio
	 */
	public void punteggiFinali(List<Integer> punteggiFinali, List<String> nomi) {
		mappa.punteggiFinali(punteggiFinali, nomi);
	}

	/**
	 * comunico il numero dei recinti
	 * 
	 * @author Matteo Daverio
	 */
	public void comunicaNumeroRecinti(int recinti) {
		mappa.aggiornaRecinti(recinti);
	}

	/**
	 * creo la mappa di gioco
	 * 
	 * @param mappa
	 * @author Matteo Daverio
	 */
	public void creaMappa(Map mappa) {
		this.mappa = mappa;
		mappa.impostaGuiImpl(this);
	}

	/**
	 * richiedo una mossa
	 * 
	 * @author Matteo Daverio
	 */
	public void richiestaMossa(List<MosseEnum> mosseDisponibili) {
		mappa.richiestaMosse(mosseDisponibili);
	}

	/**
	 * comunico il posizionamento corretto
	 * 
	 * @author Matteo Daverio
	 */
	public void posizionamentoPastoreCorretto() {
		mappa.posizionamentoCorretto();
	}

	/**
	 * comunico il posizionamento errato
	 * 
	 * @author Matteo Daverio
	 */
	public void posizionamentoPastoreErrato() {
		mappa.posizionamentoSbagliato();
	}

	/**
	 * aggiorna posizione pastore
	 * 
	 * @author Matteo Daverio
	 */
	public void aggiornamentoPosizionePastore(int turno, int pastore,
			int posizione) {
		mappa.muoviPastoreAvversario(pastore, posizione);
	}

	/**
	 * movimento del pastore
	 * 
	 * @author Matteo Daverio
	 */
	public void movimentoPastore(int posizione, String giocatore, int pastore) {
		mappa.muoviPastoreAvversario(pastore, posizione, giocatore);
	}

	/**
	 * notifica un acquisto tessera
	 * 
	 * @author Matteo Daverio
	 */
	public void acquistoTessera(TipoTerreno terreno, String giocatore,
			int pastore) {
		mappa.notificaAcquistoTessera(giocatore, terreno);
	}

	/**
	 * notifica un movimento di una pecora
	 * 
	 * @author Matteo Daverio
	 */
	public void movimentoPecora(int pecora, int strada, String giocatore,
			int pastore) {
		Pecora ovino = pecore.get(pecora);
		int tipoPecora = ovino.getTipoPecora();
		int regionePartenza = ovino.getPosizione();
		Strada posizionePastore = strade.get(strada);
		int regioneArrivo;
		if (posizionePastore.getRegioneDestra() == regionePartenza) {
			regioneArrivo = posizionePastore.getRegioneSinistra();
		} else {
			regioneArrivo = posizionePastore.getRegioneDestra();
		}

		mappa.muoviPecora(tipoPecora, regionePartenza, regioneArrivo);
	}

	/**
	 * notifica un abbattimento
	 * 
	 * @author Matteo Daverio
	 */
	public void abbattimento(int regione, int pecora, String giocatore,
			int pastore) {
		mappa.abbattiPecora(regione, giocatore, pecore.get(pecora)
				.getTipoPecora());
	}

	/**
	 * notifica un accoppiamento
	 * 
	 * @author Matteo daverio
	 */
	public void accoppiamento(int regione, String giocatore, int pastore) {
		mappa.aggiungiPecora(regione);
	}

	/**
	 * notifica il movimento del lupo
	 * 
	 * @author Matteo Daverio
	 */
	public void muoviLupo(int posizione) {
		mappa.muoviLupo(posizione);
	}

	/**
	 * notifica mossa sbagliata
	 * 
	 * @author Matteo Daverio
	 */
	public void mossaSbagliata() {
		mappa.mossaSbagliata();
	}

	/**
	 * notifica mossa corretta
	 * 
	 * @author Matteo Daverio
	 */
	public void mossaCorretta() {
		mappa.mossaCorretta();
	}

	/**
	 * comunica denaro
	 * 
	 * @author Matteo Daverio
	 */
	public void comunicaDenaro(List<Integer> denaroPastori) {
		for (int i = 0; i < denaroPastori.size(); i++) {
			mappa.impostaDenaro(denaroPastori.get(i), i);
		}
	}

	/**
	 * comunica l'inizio del turno
	 * 
	 * @author Matteo Daverio
	 */
	public void iniziaTurno(List<Pecora> pecore, int turno) {
		this.pecore = pecore;
		mappa.azzeraPecore();
		mappa.inizializzaPecore(pecore);
		mappa.iniziaTurno(turno);
	}

	/**
	 * comunica il cambio di turno
	 * 
	 * @author Matteo Daverio
	 */
	public void cambioTurno(String giocatore, List<Pecora> pecore) {
		this.pecore = pecore;
		mappa.azzeraPecore();
		mappa.inizializzaPecore(pecore);
		mappa.cambioTurno(giocatore);
	}

	/**
	 * riceve le strade
	 * 
	 * @author Matteo Daverio
	 */
	public void riceviStrade(List<Strada> strade) {
		this.strade = strade;
	}

	/**
	 * aggiornamento quando si tenta la riconnessione
	 * 
	 * @author Matteo Daverio
	 */
	public void aggiornamentoPostDisconnessione(List<Pecora> pecore,
			int posPecoraNera, int posLupo, List<Pastore> pastori) {
		mappa.inizializzaPecore(pecore);
		mappa.muoviPecoraNera(posPecoraNera);
		mappa.muoviLupo(posLupo);
		for (int i = 0; i < pastori.size(); i++) {
			mappa.collocaPastoreAvversario(pastori.get(i).getPosizione(), i);
		}
	}

	/**
	 * segnala la disconnessione
	 * 
	 * @author Matteo Daverio
	 */
	public void segnalaDisconnessione() {
		mappa.segnalaDisconnessione();
	}

	/**
	 * invia il movimento della pecora
	 * 
	 * @param turno
	 * @param posizionePartenza
	 * @param posizioneArrivo
	 * @param tipoPecora
	 */
	public void muoviPecora(int turno, int posizionePartenza,
			int posizioneArrivo, int tipoPecora) {
		int indicePecora = -1;
		int indiceStrada = -1;
		for (int i = 0; i < pecore.size(); i++) {
			if (pecore.get(i).getPosizione() == posizionePartenza
					&& pecore.get(i).getTipoPecora() == tipoPecora) {
				indicePecora = i;
				break;
			}
		}
		for (int i = 0; i < strade.size(); i++) {
			if ((strade.get(i).getRegioneDestra() == posizionePartenza && strade
					.get(i).getRegioneSinistra() == posizioneArrivo)
					|| (strade.get(i).getRegioneDestra() == posizioneArrivo && strade
							.get(i).getRegioneSinistra() == posizionePartenza)) {
				indiceStrada = i;
				break;
			}
		}
		controllorePartita.muoviPecora(indiceStrada, indicePecora, turno);
	}

	/**
	 * notifica spostamento della pecora nera
	 * 
	 * @author Matteo Daverio
	 */
	public void spostamentoPecoraNera(int strada, String giocatore, int pastore) {
		Strada posizionePastore = strade.get(strada);
		int posizione;
		if (posizionePastore.getRegioneDestra() == posizionePecoraNera) {
			posizione = posizionePastore.getRegioneSinistra();
		} else {
			posizione = posizionePastore.getRegioneDestra();
		}
		posizionePecoraNera = posizione;
		mappa.spostamentoPecoraNera(giocatore, posizione);
	}

	/**
	 * invia movimento pecora nera
	 * 
	 * @param turno
	 * @param posizionePartenza
	 * @param posizioneArrivo
	 * @author Matteo Daverio
	 */
	public void mandaMossaPecoraNera(int turno, int posizionePartenza,
			int posizioneArrivo) {
		int indiceStrada = -1;
		for (int i = 0; i < strade.size(); i++) {
			if ((strade.get(i).getRegioneDestra() == posizionePartenza && strade
					.get(i).getRegioneSinistra() == posizioneArrivo)
					|| (strade.get(i).getRegioneDestra() == posizioneArrivo && strade
							.get(i).getRegioneSinistra() == posizionePartenza)) {
				indiceStrada = i;
				break;
			}
		}
		posizionePecoraNera = posizioneArrivo;
		controllorePartita.spostaPecoraNera(indiceStrada, turno);
	}

	/**
	 * invia abbattimento di una pecora
	 * 
	 * @param regione
	 * @param tipoPecora
	 * @param mioTurno
	 * @author Matteo Daverio
	 */
	public void mandaAbbattimento(int regione, int tipoPecora, int mioTurno) {
		int indicePecora = -1;
		for (int i = 0; i < pecore.size(); i++) {
			if (pecore.get(i).getPosizione() == regione
					&& pecore.get(i).getTipoPecora() == tipoPecora) {
				indicePecora = i;
				break;
			}
		}
		controllorePartita.abbatti(regione, indicePecora, mioTurno);

	}

	/**
	 * informa della disconnessione di un altro client
	 * 
	 * @author Matteo Daverio
	 */
	public void disconnessione(String nome) {
		mappa.disconnessione(nome);
	}

	/**
	 * informa della riconnessione di un altro client
	 * 
	 * @author Matteo Daverio
	 */
	public void riconnessione(String nome) {
		mappa.riconnessione(nome);
	}

	/**
	 * informa dell'esclusione di un altro client
	 * 
	 * @author Matteo Daverio
	 */
	public void esclusione(String nome) {
		mappa.esclusione(nome);
	}
}
