package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import java.util.ArrayList;

public class Pastore {
	
	private int posizione;
	private int denaro;
	private ArrayList<Tessera> tessere;
	
	// costruttore
	
	public Pastore()
	{
		posizione=(int)Math.random()*Costanti.NUMERO_STRADE;
		denaro=20;
	}
	
	// getter e setter
	
	public int getPosizione() {
		return posizione;
	}
	
	public void setPosizione(int posizione) {
		this.posizione = posizione;
	}
	
	public int getDenaro() {
		return denaro;
	}
	
	public void setDenaro(int denaro) {
		this.denaro = denaro;
	}
	
	public ArrayList<Tessera> getTessere() {
		return tessere;
	}
	
	public void aggiungiTessera(Tessera tessera) {
		tessere.add(tessera);
	}
	
	
	
}
