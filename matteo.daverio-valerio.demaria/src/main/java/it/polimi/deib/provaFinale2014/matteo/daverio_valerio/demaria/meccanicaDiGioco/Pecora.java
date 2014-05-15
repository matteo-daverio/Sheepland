package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;

public class Pecora {

	private int tipoPecora;
	private int posizione;
	private int turno=0;

	// costruttori

	public Pecora(int posizione, int tipoPecora) // costruttore per pecora se si
													// conosce posizione e il
													// tipo di pecora desiderato
	{
		this.tipoPecora = tipoPecora;
		this.posizione = posizione;
	}

	public Pecora(int posizione) // costruttore per pecora che sceglie a random
									// se sarà pecora, montone o agnello
	{
		this(posizione, (int) (Math.random() * Costanti.NUMERO_PECORE));
	}

	public Pecora() // costruttore vuoto che crea l'animale random in posizione random
	{
		this((int) (Math.random() * Costanti.NUMERO_REGIONI), (int) (Math.random() * Costanti.NUMERO_PECORE));
	}

	// getter e setter

	public int getPosizione() {
		return posizione;
	}

	public int getTipoPecora() {
		return tipoPecora;
	}

	public void setPosizione(int pos) {
		posizione = pos;
	}

	public void setTipoPecora(int tipo) {
		tipoPecora = tipo;
	}

	public void incrementaTurno()
	{
		turno++;
	}
	
	public int getTurno()
	{
		return turno;
	}
}
