package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;

import java.util.ArrayList;

public class Pastore {
	
	private int posizione;
	private int denaro;
	private ArrayList<Tessera> tessere=null;
	private String nomeGiocatore;
	private int turnoDiGioco;
	
	// costruttore
	
	public Pastore(String nomeGiocatore, int turnoDiGioco)
	{
		this.nomeGiocatore=nomeGiocatore;
		this.turnoDiGioco=turnoDiGioco;
		posizione=(int)(Math.random()*Costanti.NUMERO_STRADE);
		denaro=20;
	}
	
	// getter e setter
	
	public int getPosizione() {
		return posizione;
	}
	
	public int getTurnoDiGioco()
	{
		return turnoDiGioco;
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
	
	public String getNomeGiocatore()
	{
		return nomeGiocatore;
	}
	
}
