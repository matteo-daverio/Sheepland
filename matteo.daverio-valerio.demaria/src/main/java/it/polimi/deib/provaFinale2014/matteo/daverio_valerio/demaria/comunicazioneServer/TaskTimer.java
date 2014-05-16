package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;


import java.util.TimerTask;

class TaskTimer extends TimerTask {

	private int time = 0;

	@Override
	public void run() {

		time++;

		if (time > 60) {
			// fermo il timer

			SocketServer.setTimeout();
			this.cancel();

		}

	}

}
