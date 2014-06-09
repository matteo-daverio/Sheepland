package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalStreetException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoSheepInShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
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

	public void riceviPartita(Partita partita) throws RemoteException;

	public void movimentoPecoraNera(int posizione) throws RemoteException;

	public Mossa inviaMossa(List<MosseEnum> mosseDisponibili) throws RemoteException;

	public void movimentoPastore(int posizione) throws RemoteException,
			NoMovementException, NoMoneyException, InvalidMovementException;

	public void cambioTurno(int turno) throws RemoteException;

	public void acquistoTessera(TipoTerreno terreno) throws RemoteException,
			NoMoreCardsException, NoMoneyException, IllegalShireTypeException;

	public void movimentoPecora(int pecora, Strada strada)
			throws RemoteException,IllegalStreetException, IllegalShireException;

	public void abbattimento(int regione,int pecora) throws RemoteException,NoSheepInShireException,
			NoMoneyException, IllegalShireException;

	public void accoppiamento(int regione) throws RemoteException,IllegalShireException,
			CannotProcreateException;
	
	public void faseFinale() throws RemoteException;
	public void cambioTurno() throws RemoteException;
	
}
