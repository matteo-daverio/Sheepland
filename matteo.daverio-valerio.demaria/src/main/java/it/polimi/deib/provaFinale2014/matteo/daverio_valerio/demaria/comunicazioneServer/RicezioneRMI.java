package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.rmi.RemoteException;

/**
 * classe che implementa i metodi di server RMI
 * 
 * @author Valerio De Maria
 * 
 */
public class RicezioneRMI implements InterfacciaServerRMI {

	private ControllorePartita gameManager;

	/**
	 * COSTRUTTORE
	 * 
	 * @param gameManager
	 * @author Valerio De Maria
	 */
	public RicezioneRMI(ControllorePartita gameManager) {

		this.gameManager = gameManager;
	}

	/**
	 * riceve la posizione iniziale del pastore
	 * 
	 * @author Valerio De Maria
	 */
	public void riceviPosizionePastore(int posizione) throws RemoteException {
		gameManager.riceviPosizionamentoPastore(posizione);

	}

	/**
	 * riceve la mossa che vuole eseguire l'utente
	 * 
	 * @author Valerio De Maria
	 */
	public void riceviMossa(Mossa mossa, int pastoreTurno)
			throws RemoteException {
		gameManager.riceviMossa(mossa, pastoreTurno);

	}

}
