package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;

public class Pecora {

	private int tipoPecora;
	private int posizione;
	private int turno = 0;

	// costruttori

	/**
	 * costruttore per pecora se si conosce posizione e tipo di pecora
	 * desiderato
	 * 
	 * @param posizione
	 * @param tipoPecora
	 * @author Matteo Daverio
	 */
	public Pecora(int posizione, int tipoPecora) {
		if (tipoPecora == 3)
			this.tipoPecora = 2;
		else
			this.tipoPecora = tipoPecora;
		if (posizione == 19)
			this.posizione = 18;
		else
			this.posizione = posizione;
	}

	/**
	 * costruttore per pecora che sceglie a random se sarà pecora, montone o
	 * agnello
	 * 
	 * @param posizione
	 * @author Matteo Daverio
	 */
	public Pecora(int posizione) {
		this(posizione, (int) (Math.random() * Costanti.TIPI_DI_PECORE));
	}

	/**
	 * costruttore vuoto che crea l'animale random in posizione random
	 * 
	 * @author Matteo Daverio
	 */
	public Pecora() {
		this((int) ((Math.random() * Costanti.NUMERO_REGIONI) + 1), (int) (Math
				.random() * Costanti.TIPI_DI_PECORE));
	}

	// getter e setter

	/**
	 * 
	 * @return posizione della pecora
	 * @author Matteo Daverio
	 */
	public int getPosizione() {
		return posizione;
	}

	/**
	 * 
	 * @return tipo della pecora
	 * @author Matteo Daverio
	 */
	public int getTipoPecora() {
		return tipoPecora;
	}

	/**
	 * setta la posizione della pecora
	 * 
	 * @param pos
	 * @author Matteo Daverio
	 */
	public void setPosizione(int pos) {
		posizione = pos;
	}

	/**
	 * setta il tipo di pecora
	 * 
	 * @param tipo
	 * @author Matteo Daverio
	 */
	public void setTipoPecora(int tipo) {
		tipoPecora = tipo;
	}

	/**
	 * incrementa il contatore di turno dell'agnello e, se >=3, si trasforma in
	 * pecora o montone
	 * 
	 * @author Matteo Daverio
	 */
	public void incrementaTurno() {
		turno++;
		if (turno >= 3) {
			tipoPecora = (int) ((Math.random() * 2) + 1);
			if (tipoPecora >= 3)
				tipoPecora = 2;

		}
	}

	/**
	 * 
	 * @return da quanti turni esiste l'agnello
	 * @author Matteo Daverio
	 */
	public int getTurno() {
		return turno;
	}

	/**
	 * movimento della pecora
	 * 
	 * @param strada
	 * @author Matteo Daverio
	 */
	public void muoviPecora(Strada strada) {
		if (posizione == strada.getRegioneDestra())
			posizione = strada.getRegioneSinistra();
		else
			posizione = strada.getRegioneDestra();
	}
}
