package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

public class Pecora {

	private int tipoPecora;
	private int posizione;	
	
	// costruttori
	
	public Pecora(int posizione, int tipoPecora)  //  costruttore per pecora se si conosce posizione e il tipo di pecora desiderato
	{
		this.tipoPecora=tipoPecora;
		this.posizione=posizione;
	}
	
	public Pecora(int posizione)  //  costruttore per pecora che sceglie a random se sarà pecora, montone o agnello
	{
		this(posizione,(int)Math.random()*3);
	}
	
	public Pecora()  //  costruttore vuoto che crea la pecora a Sheepburg
	{
		this(0,1);
	}
	
	
	// getter e setter
	
	public int getPosizione()
	{
		return posizione;
	}
	
	public int getTipoPecora()
	{
		return tipoPecora;
	}
	
	public void setPosizione(int pos)
	{
		posizione=pos;
	}
	
	public void setTipoPecora(int tipo)
	{
		tipoPecora=tipo;
	}
	
}
