package mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireTypeException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoreCardsException;
/**
 * passo tipo di terreno di cui voglio comprare la tessera
 * 
 * @author Valerio De Maria
 *
 */
public class CompraTessera implements Mossa {
	
	private TipoTerreno terreno;
	
	//costruttore
	public CompraTessera(TipoTerreno terreno){
		
		this.terreno=terreno;
		
	}
	
	public void eseguiMossa(Partita partita) throws NoMoreCardsException, NoMoneyException, IllegalShireTypeException{
		
		partita.compraTessera(terreno);
	}

}
