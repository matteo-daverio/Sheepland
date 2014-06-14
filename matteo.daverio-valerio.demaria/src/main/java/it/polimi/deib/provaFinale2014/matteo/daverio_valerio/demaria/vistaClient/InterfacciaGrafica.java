package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;

import java.util.List;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;

/**
 * interfaccia che raccoglie i metodi comuni a gui e commandLine
 * 
 * @author Valerio De Maria
 *
 */
public interface InterfacciaGrafica {
	
	/**
	 * viene fatta partire la grafica con la schermata di log-in
	 * @author Valerio De Maria
	 */
	public void start();
	
	/**
	 * invio alla grafica la lista dei nomi dei giocatori della partita
	 * @param nomi
	 * @author Valerio De Maria
	 */
	public void nomiGiocatori(List<String> nomi);
	
	/**
	 * invio alla grafica i soldi iniziali relativi ad ogni pastore
	 * @param soldi
	 * @author Valerio De Maria
	 */
	public void soldiPastori(List<Integer>soldi);
	
	/**
	 * invio alla grafica la tessera iniziale del pastore
	 * @param tesseraIniziale
	 * @author Valerio De Maria
	 */
	public void tesseraIniziale(Tessera tesseraIniziale);
	
	/**
	 * invio alla grafica l'array di pecore posizionate inizialmente
	 * @param pecore
	 * @author Valerio De Maria
	 */
    public void settaPecore(List<Pecora> pecore);
	
    /**
     * viene richiesto il posizionamento del pastore
     * @author Valerio De Maria
     */
    public void richiestaPosizionamentoPastore();
    
    /**
     * viene richiesta l'esecuzione di una mossa,
     * si passa la lista di mosse disponibili da mostrare all'utente
     * @param mosseDisponibili
     * @author Valerio De Maria
     */
    public void richiestaMossa(List<MosseEnum> mosseDisponibili);
    
    
	/**
	 * dico alla grafica che il posizionamento è corretto
	 * @author Valerio De Maria
	 */
	public void posizionamentoPastoreCorretto();
	
	/**
	 * dico alla grafica che il posizionamento è sbagliato
	 * @author Valerio De Maria
	 */
	public void posizionamentoPastoreErrato();
	

	
	//AGGIORNAMENTI MOSSE AVVERSARI
	
	/**
	 * dico alla grafica che il giocatore di un certo turno ha mosso un certo pastore in una 
	 * certa posizione
	 * @param turno
	 * @param pastore
	 * @param posizione
	 * @author Valerio De Maria
	 */
	public void aggiornamentoPosizionePastore(int turno,int pastore,int posizione);
	
	/**
	 * dico alla grafica che un certo giocatore ha mosso un certo pastore in una certa
	 * direzione
	 * @param posizione
	 * @param giocatore
	 * @param pastore
	 * @author Valerio De Maria
	 */
	public void movimentoPastore(int posizione,String giocatore,int pastore);
	
	/**
	 * dico alla grafica che un certo giocatore, tramite un certo pastore, ha acquistato 
	 * una tessera di un determinato tipo 
	 * @param terreno
	 * @param giocatore
	 * @param pastore
	 * @author Valerio De Maria
	 */
	public void acquistoTessera(TipoTerreno terreno,String giocatore,int pastore);
	
	/**
	 * dico alla grafica che un giocatore, tramite un certo pastore, ha spostato una certa pecora
	 * lungo una certa strada
	 * @param pecora
	 * @param strada
	 * @param giocatore
	 * @param pastore
	 * @author Valerio De Maria
	 */
	public void movimentoPecora(int pecora, int strada,String giocatore, int pastore);
	
	/**
	 * dico alla grafica che un certo giocatore, tramite un certo pastore, ha abbattuto una
	 * certa pecora in una certa regione
	 * @param regione
	 * @param pecora
	 * @param giocatore
	 * @param pastore
	 * @author Valerio De Maria
	 */
	public void abbattimento(int regione, int pecora,String giocatore, int pastore);
	
	/**
	 * dico alla grafica che un certo giocatore, tramite un certo pastore, ha fatto un 
	 * accoppiamento in una certa regione
	 * @param regione
	 * @param giocatore
	 * @param pastore
	 * @author Valerio De Maria
	 */
	public void accoppiamento(int regione,String giocatore, int pastore);
	
	/**
	 * comunico che è il turno dell'utente
	 * @author Valerio De Maria
	 */
	public void iniziaTurno ();
	
	/**
	 * dico alla grafica di chi è il turno attuale
	 * @param giocatore
	 * @author Valerio De Maria
	 */
	public void cambioTurno(String giocatore);

	/**
	 * comunico alla grafica il movimento della pecora nera
	 * @param posizione
	 * @author Valerio De Maria
	 */
	public void muoviPecoraNera(int posizione);
	
	//per selezionare il pastore in partite a due giocatori
	//public int selezionaPastore(int primo,int secondo);
	
	/**
	 * dico alla grafica che la mossa fatta è sbagliata
	 * @author Valerio De Maria
	 */
	public void mossaSbagliata();
	
	/**
	 * dico alla grafica che la mossa fatta è sbagliata
	 * @author Valerio De Maria
	 */
	public void mossaCorretta();
	
	/**
	 * dico alla grafica che si è entrati in fase finale
	 * @author Valerio De Maria
	 */
	public void faseFinale();
	
	/**
	 * invio alla grafica la lista dei punteggi finali
	 * @param punteggiFinali
	 * @param nomi
	 * @author Valerio De Maria
	 */
	public void punteggiFinali(List<Integer> punteggiFinali, List<String> nomi);
	
	/**
	 * comunico all'utente qual'è il denaro residuo del pastore che ha usato per fare
	 * una mossa
	 * @param denaro
	 * @author Valerio De Maria
	 */
	public void comunicaDenaro(int denaro);
	
	/**
	 * dico alla grafica qual'è il numero di recinti USATI fino ad ora
	 * @param recinti
	 * @author Valerio De Maria
	 */
	public void comunicaNumeroRecinti(int recinti);
	
	//PER ESECUZIONE MOSSA
	
	//metodi che vengo eseguiti a seguito di una richiesta di una determinata mossa da parte
	//dell'utente, richiedono un valore di ritorno, bisogna verificare se si riescono ad 
	//implementare nella gui
	
	public int scegliRegione();
	public int scegliStrada();
	public int scegliPecora();
	//1-ACQUA,2-FORESTA,3-GRANO,4-PRATERIA,5-ROCCIA,6-SABBIA;
	public int scegliTipoTerreno();
	
}
