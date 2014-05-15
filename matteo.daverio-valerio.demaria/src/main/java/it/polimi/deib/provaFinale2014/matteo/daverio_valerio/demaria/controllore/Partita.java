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
		
		//strade
		strade.add(new Strada(0,9,8,1));   
		strade.add(new Strada(1,1,8,3));
		strade.add(new Strada(2,7,8,2));
		strade.add(new Strada(3,1,7,4));
		strade.add(new Strada(4,6,7,6));
		strade.add(new Strada(5,18,7,3));
		strade.add(new Strada(6,18,6,2));
		strade.add(new Strada(7,17,18,1));
		strade.add(new Strada(8,17,6,3));
		strade.add(new Strada(9,5,6,4));
		strade.add(new Strada(10,5,17,5));
		strade.add(new Strada(11,16,17,2));
		strade.add(new Strada(12,16,5,3));
		strade.add(new Strada(13,15,16,1));
		strade.add(new Strada(14,15,5,6));
		strade.add(new Strada(15,15,4,2));
		strade.add(new Strada(16,15,14,5));
		strade.add(new Strada(17,14,4,4));
		strade.add(new Strada(18,14,13,1));
		strade.add(new Strada(19,4,13,6));
		strade.add(new Strada(20,13,3,2));
		strade.add(new Strada(21,13,12,5));
		strade.add(new Strada(22,12,3,1));
		strade.add(new Strada(23,12,11,2));
		strade.add(new Strada(24,3,11,3));
		strade.add(new Strada(25,11,2,4));
		strade.add(new Strada(26,11,10,1));
		strade.add(new Strada(27,2,10,2));
		strade.add(new Strada(28,10,9,4));
		strade.add(new Strada(29,2,9,3));
		strade.add(new Strada(30,1,9,2));
		strade.add(new Strada(31,2,1,1));
		strade.add(new Strada(32,0,2,5));
		strade.add(new Strada(33,0,1,6));
		strade.add(new Strada(34,6,1,5));
		strade.add(new Strada(35,0,6,1));
		strade.add(new Strada(36,5,0,2));
		strade.add(new Strada(37,4,5,1));
		strade.add(new Strada(38,4,0,3));
		strade.add(new Strada(39,4,3,5));
		strade.add(new Strada(40,3,0,4));
		strade.add(new Strada(41,3,2,6));
		
		//direzioni
		strade.get(0).aggiungiStrade(1, "nord-est");  // esempio creazione direzione
		strade.get(0).aggiungiStrade(30, "sud-est");
		
		strade.get(1).aggiungiStrade(2, "nord-ovest");
		strade.get(1).aggiungiStrade(3, "nord-est");
		strade.get(1).aggiungiStrade(0, "sud-ovest");
		strade.get(1).aggiungiStrade(30, "sud-est");
		
		strade.get(2).aggiungiStrade(1, "sud-ovest");
		strade.get(2).aggiungiStrade(3, "sud-est");
		
		strade.get(3).aggiungiStrade(4, "nord-ovest");
		strade.get(3).aggiungiStrade(34, "nord-est");
		strade.get(3).aggiungiStrade(1, "sud-est");
		strade.get(3).aggiungiStrade(2, "sud-ovest");
		
		strade.get(4).aggiungiStrade(5, "nord-ovest");
		strade.get(4).aggiungiStrade(6, "nord-est");
		strade.get(4).aggiungiStrade(34, "sud-est");
		strade.get(4).aggiungiStrade(3, "sud-ovest");
		
		strade.get(5).aggiungiStrade(6, "sud-est");
		strade.get(5).aggiungiStrade(4, "sud-ovest");
		
         ///da continuare... => venerdi 16 maggio
		
		
		
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