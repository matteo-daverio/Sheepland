package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ThreadRicezioneSocket implements Runnable {

	private ObjectInputStream in;
	private ComandiSocket line;
	private ControllorePartita gameManager;

	public ThreadRicezioneSocket(ObjectInputStream in, ControllorePartita gameManager) {

		this.in = in;
		this.gameManager=gameManager;

	}

	public void run() {

		while (true) {

			try {
				line = (ComandiSocket) in.readObject();

				switch (line) {

				case POSIZIONE_PASTORE:
					gameManager.riceviPosizionamentoPastore(in.readInt());
					
					break;
					
				case MOSSA:
					gameManager.riceviMossa((Mossa)in.readObject());
					break;
					
				default:
					break;
				}

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
