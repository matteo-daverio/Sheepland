package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;

public class Pecora {

	private int tipoPecora;
	private int posizione;
	private int turno = 0;

	// costruttori

	public Pecora(int posizione, int tipoPecora) // costruttore per pecora se si
													// conosce posizione e il
													// tipo di pecora desiderato
	{
		if (tipoPecora == 3)
			this.tipoPecora = 2;
		else
			this.tipoPecora = tipoPecora;
		if(posizione==19)
			this.posizione=18;
		else
		 this.posizione = posizione;
	}

	public Pecora(int posizione) // costruttore per pecora che sceglie a random
									// se sarà pecora, montone o agnello
	{
		this(posizione, (int) (Math.random() * Costanti.TIPI_DI_PECORE));
	}

	public Pecora() // costruttore vuoto che crea l'animale random in posizione
					// random
	{
		this((int) ((Math.random() * Costanti.NUMERO_REGIONI)+1), (int) (Math
				.random() * Costanti.TIPI_DI_PECORE));
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
		if (turno>=3){
			tipoPecora=(int) ((Math.random()*2)+1);
			if (tipoPecora>=3)
				tipoPecora=2;
            
		}
	}

	public int getTurno() {
		return turno;
	}

	// movimento pecora

	public void muoviPecora(Strada strada) {
		if (posizione == strada.getRegioneDestra())
			posizione = strada.getRegioneSinistra();
		else
			posizione = strada.getRegioneDestra();
	}
}
