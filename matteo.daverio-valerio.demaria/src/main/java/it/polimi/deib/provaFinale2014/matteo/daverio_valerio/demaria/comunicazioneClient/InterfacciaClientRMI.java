package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaServerRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * interfaccia del client RMI dove ho i metodi che il server può eseguire
 * 
 * @author Valerio De Maria
 * 
 */
public interface InterfacciaClientRMI extends Remote {

	/**
	 * 
	 * @param posizione
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void movimentoPecoraNera(int posizione) throws RemoteException;

	/**
	 * 
	 * @param posizione
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void movimentoLupo(int posizione) throws RemoteException;

	/**
	 * avvisa il client che si è entrati in fase finale
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void faseFinale() throws RemoteException;

	/**
	 * 
	 * @param pecore
	 * @param turno
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void inizioTurno(List<Pecora> pecore, int turno)
			throws RemoteException;

	/**
	 * 
	 * @param giocatore
	 * @param pecore
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void cambioTurno(String giocatore, List<Pecora> pecore)
			throws RemoteException;

	/**
	 * 
	 * @param nomi
	 * @param soldi
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void riceviDatiGiocatori(List<String> nomi, List<Integer> soldi)
			throws RemoteException;

	/**
	 * 
	 * @param pecore
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void riceviPecore(List<Pecora> pecore) throws RemoteException;

	/**
	 * 
	 * @param strade
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void riceviStrade(List<Strada> strade) throws RemoteException;

	/**
	 * 
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void mossaSbagliata() throws RemoteException;

	/**
	 * 
	 * @param tesseraIniziale
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void tesseraIniziale(Tessera tesseraIniziale) throws RemoteException;

	/**
	 * 
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void mossaCorretta() throws RemoteException;

	/**
	 * 
	 * @param punteggiFinali
	 * @param nomi
	 * @throws RemoteException
	 * @author Valeri De Maria
	 */
	public void punteggiFinali(List<Integer> punteggiFinali, List<String> nomi)
			throws RemoteException;

	/**
	 * 
	 * @param denaroPastori
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void comunicaDenaro(List<Integer> denaroPastori)
			throws RemoteException;

	/**
	 * 
	 * @param recinti
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void comunicaNumeroRecinti(int recinti) throws RemoteException;

	/**
	 * ricevo l'oggetto del server su cui posso chiamare i metodi per inviare dati
	 * @param serverRMI
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void riceviStubServer(InterfacciaServerRMI serverRMI)
			throws RemoteException;

	// /// METODI DI RICHIESTE

	/**
	 * 
	 * @param mosseDisponibili
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void richiestaMossa(List<MosseEnum> mosseDisponibili)
			throws RemoteException;

	/**
	 * 
	 * @param stradeDisponibili
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void richiestaPosizionamento(List<Integer> stradeDisponibili)
			throws RemoteException;

	// /// METODI RELATIVI ALLE DISCONNESSIONI

	/**
	 * aggiorna il client quando rientra in seguit ad una riconnessione
	 * 
	 * @param pecore
	 * @param posPecoraNera
	 * @param PosLupo
	 * @param pastori
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void aggiornamento(List<Pecora> pecore, int posPecoraNera,
			int PosLupo, List<Pastore> pastori) throws RemoteException;

	// /// METODI DI AGGIORNAMENTO SU MOSSE DI AVVERSARI

	/**
	 *un avversario ha mosso la pecora nera
	 * @param strada
	 * @param giocatore
	 * @param pastore
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void spostamentoPecoraNera(int strada,String giocatore,int pastore)throws RemoteException;
	/**
	 * invia l'aggiornamento di una posizione di pastore da parte dell'aversario
	 * 
	 * @param turno
	 * @param pastore
	 * @param posizione
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void riceviAggiornamentoPosizionamentoPastore(int turno,
			int pastore, int posizione) throws RemoteException;

	/**
	 * 
	 * @param posizione
	 * @param giocatore
	 * @param pastore
	 * @throws RemoteException
	 * @throws GameException
	 * @author Valerio De Maria
	 */
	public void movimentoPastore(int posizione, String giocatore, int pastore)
			throws RemoteException, GameException;

	/**
	 * 
	 * @param terreno
	 * @param giocatore
	 * @param pastore
	 * @throws RemoteException
	 * @throws GameException
	 * @author Valerio De Maria
	 */
	public void acquistoTessera(TipoTerreno terreno, String giocatore,
			int pastore) throws RemoteException, GameException;

	/**
	 * 
	 * @param pecora
	 * @param strada
	 * @param giocatore
	 * @param pastore
	 * @throws RemoteException
	 * @throws GameException
	 * @author Valerio De Maria
	 */
	public void movimentoPecora(int pecora, int strada, String giocatore,
			int pastore) throws RemoteException, GameException;

	/**
	 * 
	 * @param regione
	 * @param pecora
	 * @param giocatore
	 * @param pastore
	 * @throws RemoteException
	 * @throws GameException
	 * @author Valerio De Maria
	 */
	public void abbattimento(int regione, int pecora, String giocatore,
			int pastore) throws RemoteException, GameException;

	/**
	 * 
	 * @param regione
	 * @param giocatore
	 * @param pastore
	 * @throws RemoteException
	 * @throws GameException
	 * @author Valerio De Maria
	 */
	public void accoppiamento(int regione, String giocatore, int pastore)
			throws RemoteException, GameException;
	
	/**
	 * comunico una disconnessione di un giocatore
	 * @param nome
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void disconnessione(String nome)throws RemoteException;
	
	/**
	 * comunico una riconnessione di un giocatore
	 * @param nome
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void risconnessione(String nome)throws RemoteException;
	
	/**
	 * comunico una esclusione di un giocatore
	 * @param nome
	 * @throws RemoteException
	 * @author Valerio De Maria
	 */
	public void esclusione(String nome)throws RemoteException;
	
	/**
	 * risponde ad una richiesta di pong
	 * @author Valerio De Maria
	 */
	public void pong()throws RemoteException;

}
