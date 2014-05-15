package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;

import java.util.ArrayList;


public class Partita {
	
	private int numeroGiocatori;
	private ArrayList<Pastore> pastori;
	private ArrayList<Strada> strade;
	
	// costruttori
	
	public Partita()  // costruttore vuoto
	{
		
	}
	
	// getter e setter
	
	public void setNumeroGiocatori(int numeroGiocatori)
	{
		this.numeroGiocatori=numeroGiocatori;
	}
	
	public int getNumeroGiocatori()
	{
		return numeroGiocatori;
	}
	
	// metodi di partita
	
	
	// inizializzazione della classe
	public void inizializza()
	{
		
		// creazione della mappa
		strade.add(new Strada(0,3,5,9));   // esempio creazione strada 
		strade.get(0).aggiungiStrade(1, "nord-est");  // esempio creazione direzione
		
		
		
		
	}
	
	// aggiungi giocatore
	public void aggiungiPastore(String nomeGiocatore, int turnoDiGioco)
	{
		pastori.add(new Pastore(nomeGiocatore,turnoDiGioco));
	}
	
	
	
	// movimento pastore
	public void muoviPastore()
	{
		
	}
	
}