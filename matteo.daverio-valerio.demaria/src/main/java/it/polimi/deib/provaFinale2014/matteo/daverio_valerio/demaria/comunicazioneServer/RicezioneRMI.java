package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.rmi.RemoteException;

public class RicezioneRMI implements InterfacciaServerRMI{
	
	private ControllorePartita gameManager;

	public RicezioneRMI(ControllorePartita gameManager){
		
		this.gameManager=gameManager;
	}

	public void riceviPosizionePastore(int posizione) throws RemoteException {
		gameManager.riceviPosizionamentoPastore(posizione);
		
	}

	public void riceviMossa(Mossa mossa,int pastoreTurno) throws RemoteException {
		gameManager.riceviMossa(mossa,pastoreTurno);
		
	}


	
	
}
