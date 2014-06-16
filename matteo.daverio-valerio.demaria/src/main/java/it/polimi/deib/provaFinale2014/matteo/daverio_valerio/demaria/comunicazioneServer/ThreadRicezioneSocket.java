package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.ComandiSocket;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient.ClientRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse.Mossa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadRicezioneSocket implements Runnable {

	private ObjectInputStream in;
	private ComandiSocket line;
	private ControllorePartita gameManager;
	private int turno;
	private boolean connesso=true;
	
	private static final Logger LOG=Logger.getLogger(ClientRMI.class.getName());

	public ThreadRicezioneSocket(ObjectInputStream in, ControllorePartita gameManager,int turno) {

		this.in = in;
		this.gameManager=gameManager;
		this.turno=turno;

	}
	
	public void aggiornaSocket(Socket socket){
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
			connesso=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void chiudiConnesione(){
		
		
	}

	public void run() {

		while (connesso) {

			try {
				line = (ComandiSocket) in.readObject();

				switch (line) {

				case INVIO_POSIZIONE_PASTORE:
					gameManager.riceviPosizionamentoPastore(in.readInt());
					
					break;
					
				case INVIO_MOSSA:
					Mossa mossa=(Mossa)in.readObject();
					int pastoreTurno=in.readInt();
					gameManager.riceviMossa(mossa,pastoreTurno);
					break;
					
				default:
					break;
				}

			} catch (ClassNotFoundException e) {
				
				connesso=false;
				gameManager.avvioTimerDisconnessione(turno);
				LOG.log(Level.SEVERE,"errore in ricezione socket", e);
				

			} catch (IOException e) {
				
				connesso=false;
				gameManager.avvioTimerDisconnessione(turno);
				LOG.log(Level.SEVERE,"errore in ricezione socket", e);
				

			}

		}

	}

}
