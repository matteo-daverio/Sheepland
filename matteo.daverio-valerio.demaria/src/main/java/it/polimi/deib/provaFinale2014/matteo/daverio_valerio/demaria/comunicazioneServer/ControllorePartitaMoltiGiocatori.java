package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;

import java.util.List;

public class ControllorePartitaMoltiGiocatori extends ControllorePartita {

	public ControllorePartitaMoltiGiocatori(List<Gestione> connessioni,
			Partita partita) {
		super(connessioni, partita);
		super.run();
	}

	/**
	 * creo un pastore per ogni giocatore la sequenza di turni dei giocatori è
	 * data da chi si è connesso per primo
	 * 
	 * @author Valerio De Maria
	 */
	@Override
	void aggiungiPastori(List<Gestione> connessioni, Partita partita) {

		for (int i = 0; i <= connessioni.size()-1; i++) {

			partita.aggiungiPastore(connessioni.get(i).getNome(), i + 1);

		}

	}
/*
	@Override
	void posizionaPastori(List<InterfacciaComunicazioneClient> giocatori, Partita partita) {
		for (int i = 0; i <= giocatori.size() - 1; i++) {
			partita.getPastori()
					.get(i)
					.setPosizione(
							giocatori.get(i).chiediPosizionamentoPastore());
		}
		
	}
*/

	@Override
	void posizionaPastori(List<InterfacciaComunicazioneClient> giocatori,
			Partita partita) {
		// TODO Auto-generated method stub
		
	}
}
