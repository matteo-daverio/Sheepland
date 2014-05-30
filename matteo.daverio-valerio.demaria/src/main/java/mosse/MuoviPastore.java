package mosse;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;

public class MuoviPastore implements Mossa {
	
	private int posizione;
	
	public MuoviPastore(int posizione){
		
		this.posizione=posizione;
		
	}
	public void eseguiMossa(Partita partita) throws NoMovementException, NoMoneyException, InvalidMovementException{
		
		
		partita.muoviPastore(posizione);
	}

}
