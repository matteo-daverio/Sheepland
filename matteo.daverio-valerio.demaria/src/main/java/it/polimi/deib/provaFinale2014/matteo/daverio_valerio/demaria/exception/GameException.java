package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.EccezioniDiGioco;

public class GameException extends Exception{
	
	private EccezioniDiGioco tipo;
	
	private static final long serialVersionUID = 1L;

	public GameException(EccezioniDiGioco tipo) {
		
		super();
		this.tipo=tipo;
		
	}

	public GameException(String s,EccezioniDiGioco tipo) {
		super(s);
		this.tipo=tipo;
	}
	
	public EccezioniDiGioco getTipo(){
		return tipo;
	}

}
