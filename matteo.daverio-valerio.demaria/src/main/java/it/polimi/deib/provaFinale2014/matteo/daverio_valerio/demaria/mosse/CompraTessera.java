package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaComunicazioneClient;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;

import java.io.Serializable;
import java.util.List;

/**
 * passo tipo di terreno di cui voglio comprare la tessera
 * 
 * @author Valerio De Maria
 * 
 */
public class CompraTessera implements Mossa,Serializable {

	private TipoTerreno terreno;

	// costruttore
	public CompraTessera(TipoTerreno terreno) {

		this.terreno = terreno;

	}

	public void eseguiMossa(Partita partita) throws NoMoreCardsException,
			NoMoneyException, IllegalShireTypeException {

		partita.compraTessera(terreno);
	}

	public void aggiornaClients(
			List<InterfacciaComunicazioneClient> giocatori,int turno) {
		for (int i=0;i<=giocatori.size();i++) {
	           if(i!=(turno-1)){
				giocatori.get(turno-1).comunicaAcquistaTessera(terreno);;
	           }
			}		

	}

	public List<MosseEnum> aggiornaMosseFatte(List<MosseEnum> mosseFatte) {
		mosseFatte.add(MosseEnum.COMPRA_TESSERA);
		return mosseFatte;

	}

}
