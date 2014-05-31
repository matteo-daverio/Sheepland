package mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
/**
 * passo la pecora che voglio muovere e la strada su cui si trova il pastore
 * 
 * @author Valerio De Maria
 *
 */
public class MuoviPecora implements Mossa {
	
	private Strada strada;
	private int pecora;
	
	//costruttore
	public MuoviPecora(Strada strada, int pecora){
		
		this.strada=strada;
	    this.pecora=pecora;
	    
	}
	
	public void eseguiMossa(Partita partita) {
		
		partita.getPecore().get(pecora).muoviPecora(strada);
	}

}
