package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.GameException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

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

	public void movimentoPecoraNera(int posizione) throws RemoteException;

	public Mossa inviaMossa(List<MosseEnum> mosseDisponibili) throws RemoteException;

	public void movimentoPastore(int posizione, String giocatore, int pastore) throws RemoteException,
			 GameException;

	public void acquistoTessera(TipoTerreno terreno,String giocatore,int pastore) throws RemoteException,GameException;

	public void movimentoPecora(int pecora, int strada, String giocatore, int pastore)
			throws RemoteException,GameException;

	public void abbattimento(int regione,int pecora,String giocatore, int pastore) throws RemoteException,GameException;

	public void accoppiamento(int regione,String giocatore, int pastore) throws RemoteException,GameException;
	
	public void faseFinale() throws RemoteException;
	
	public void inizioTurno()throws RemoteException;
	
	public void cambioTurno(String giocatore)throws RemoteException;
	
	public void riceviDatiGiocatori(List<String> nomi, List<Integer> soldi) throws RemoteException;
	
	public void riceviPecore(List<Pecora> pecore) throws RemoteException;
	
	public int posizionaPastore(List<Integer> stradeDisponibili)throws RemoteException;
	
	public void riceviAggiornamentoPosizionamentoPastore(int turno,int pastore, int posizione) throws RemoteException;
	
	public int selezionaPastore(int primo, int secondo)throws RemoteException;
	
	public void mossaSbagliata()throws RemoteException;
	
	public void tesseraIniziale(Tessera tesseraIniziale)throws RemoteException;
	
	public void mossaCorretta() throws RemoteException;
	
	public void punteggiFinali(List<Integer> punteggiFinali,List<String> nomi)throws RemoteException;

	public void comunicaDenaro(int denaro)throws RemoteException;
	
	public void comunicaNumeroRecinti(int recinti)throws RemoteException;
}
