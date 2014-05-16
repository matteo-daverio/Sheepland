package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Regione;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;

import java.util.ArrayList;


public class Partita {
	
	private int numeroGiocatori;
	private int turno;
	private ArrayList<Pastore> pastori;
	private static ArrayList<Strada> strade;
	private ArrayList<Regione> regioni;
	
	
	// costruttori
	
	public Partita()  // costruttore vuoto
	{
		turno=1;
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
	
	public static ArrayList<Strada> getStrade()
	{
		return strade;
	}
	
	/*
	 * 
	 *  
	 *  
	 *  METODI DI PARTITA
	 *  
	 *  
	 *  
	 *  
	 *  
	 */
	
	
	// inizializzazione della classe
	private void inizializza()
	{
		creaMappa();	
	}
	
	private void creaMappa()
	{
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
		strade.get(0).aggiungiStrade(1, "nord-est");  
		strade.get(0).aggiungiStrade(30, "sud-est");
		
		strade.get(1).aggiungiStrade(2, "nord-ovest");
		strade.get(1).aggiungiStrade(3, "nord-est");
		strade.get(1).aggiungiStrade(0, "ovest");
		strade.get(1).aggiungiStrade(30, "sud-est");
		
		strade.get(2).aggiungiStrade(1, "sud-ovest");
		strade.get(2).aggiungiStrade(3, "sud-est");
		
		strade.get(3).aggiungiStrade(2, "nord-ovest");
		strade.get(3).aggiungiStrade(4, "nord-est");
		strade.get(3).aggiungiStrade(34, "sud-est");
		strade.get(3).aggiungiStrade(1, "sud-ovest");
		
		strade.get(4).aggiungiStrade(5, "nord");
		strade.get(4).aggiungiStrade(6, "est");
		strade.get(4).aggiungiStrade(34, "sud");
		strade.get(4).aggiungiStrade(3, "ovest");
		
		strade.get(5).aggiungiStrade(6, "sud-est");
		strade.get(5).aggiungiStrade(4, "sud-ovest");
		
		strade.get(6).aggiungiStrade(5, "nord-ovest");
		strade.get(6).aggiungiStrade(4, "sud-ovest");
		strade.get(6).aggiungiStrade(7, "nord-est");
		strade.get(6).aggiungiStrade(8, "sud-est");
		
		strade.get(7).aggiungiStrade(6, "ovest");
		strade.get(7).aggiungiStrade(8, "sud");
		
		strade.get(8).aggiungiStrade(6, "nord-ovest");
		strade.get(8).aggiungiStrade(7, "nord-est");
		strade.get(8).aggiungiStrade(9, "sud");
		strade.get(8).aggiungiStrade(10, "est");
		
		strade.get(9).aggiungiStrade(8, "nord");
		strade.get(9).aggiungiStrade(10, "est");
		strade.get(9).aggiungiStrade(35, "ovest");
		strade.get(9).aggiungiStrade(36, "sud");
		
		strade.get(10).aggiungiStrade(11, "nord-est");
		strade.get(10).aggiungiStrade(8, "nord-ovest");
		strade.get(10).aggiungiStrade(12, "sud-est");
		strade.get(10).aggiungiStrade(9, "sud-ovest");
		
		strade.get(11).aggiungiStrade(12, "sud");
		strade.get(11).aggiungiStrade(10, "ovest");
		
		strade.get(12).aggiungiStrade(11, "nord");
		strade.get(12).aggiungiStrade(14, "sud");
		strade.get(12).aggiungiStrade(13, "est");
		strade.get(12).aggiungiStrade(10, "ovest");
		
		strade.get(13).aggiungiStrade(12, "nord-ovest");
		strade.get(13).aggiungiStrade(14, "sud-ovest");
		
		strade.get(14).aggiungiStrade(12, "nord");
		strade.get(14).aggiungiStrade(13, "est");
		strade.get(14).aggiungiStrade(37, "ovest");
		strade.get(14).aggiungiStrade(15, "sud");
		
		strade.get(15).aggiungiStrade(14, "nord");
		strade.get(15).aggiungiStrade(37, "ovest");
		strade.get(15).aggiungiStrade(16, "est");
		strade.get(15).aggiungiStrade(17, "sud");
		
		strade.get(16).aggiungiStrade(15, "nord");
		strade.get(16).aggiungiStrade(17, "sud");
		
		strade.get(17).aggiungiStrade(15, "nord");
		strade.get(17).aggiungiStrade(19, "ovest");
		strade.get(17).aggiungiStrade(16, "est");
		strade.get(17).aggiungiStrade(18, "sud");
		
		strade.get(18).aggiungiStrade(17, "nord");
		strade.get(18).aggiungiStrade(19, "ovest");
		
		strade.get(19).aggiungiStrade(17, "nord-est");
		strade.get(19).aggiungiStrade(39, "nord-ovest");
		strade.get(19).aggiungiStrade(18, "sud-est");
		strade.get(19).aggiungiStrade(20, "sud-ovest");
		
		strade.get(20).aggiungiStrade(39, "nord");
		strade.get(20).aggiungiStrade(19, "est");
		strade.get(20).aggiungiStrade(22, "ovest");
		strade.get(20).aggiungiStrade(21, "sud");
		
		strade.get(21).aggiungiStrade(20, "nord");
		strade.get(21).aggiungiStrade(22, "ovest");
		
		strade.get(22).aggiungiStrade(20, "nord-est");
		strade.get(22).aggiungiStrade(24, "nord-ovest");
		strade.get(22).aggiungiStrade(21, "sud-est");
		strade.get(22).aggiungiStrade(23, "sud-ovest");
		
		strade.get(23).aggiungiStrade(24, "nord");
		strade.get(23).aggiungiStrade(22, "est");
		
		strade.get(24).aggiungiStrade(41, "nord");
		strade.get(24).aggiungiStrade(25, "ovest");
		strade.get(24).aggiungiStrade(22, "est");
		strade.get(24).aggiungiStrade(23, "sud");
		
		strade.get(25).aggiungiStrade(41, "nord-est");
		strade.get(25).aggiungiStrade(27, "nord-ovest");
		strade.get(25).aggiungiStrade(24, "sud-est");
		strade.get(25).aggiungiStrade(26, "sud-ovest");
		
		strade.get(26).aggiungiStrade(27, "nord");
		strade.get(26).aggiungiStrade(25, "est");
		
		strade.get(27).aggiungiStrade(29, "nord");
		strade.get(27).aggiungiStrade(28, "ovest");
		strade.get(27).aggiungiStrade(25, "est");
		strade.get(27).aggiungiStrade(26, "sud");
		
		strade.get(28).aggiungiStrade(29, "nord");
		strade.get(28).aggiungiStrade(27, "sud");
		
		strade.get(29).aggiungiStrade(30, "nord");
		strade.get(29).aggiungiStrade(31, "est");
		strade.get(29).aggiungiStrade(28, "ovest");
		strade.get(29).aggiungiStrade(27, "sud");
		
		strade.get(30).aggiungiStrade(1, "nord");
		strade.get(30).aggiungiStrade(0, "ovest");
		strade.get(30).aggiungiStrade(31, "est");
		strade.get(30).aggiungiStrade(29, "sud");
		
		strade.get(31).aggiungiStrade(33, "nord-est");
		strade.get(31).aggiungiStrade(30, "nord-ovest");
		strade.get(31).aggiungiStrade(32, "sud-est");
		strade.get(31).aggiungiStrade(29, "sud-ovest");
		
		strade.get(32).aggiungiStrade(33, "nord");
		strade.get(32).aggiungiStrade(31, "ovest");
		strade.get(32).aggiungiStrade(40, "est");
		strade.get(32).aggiungiStrade(41, "sud");
		
		strade.get(33).aggiungiStrade(34, "nord");
		strade.get(33).aggiungiStrade(35, "est");
		strade.get(33).aggiungiStrade(31, "ovest");
		strade.get(33).aggiungiStrade(32, "sud");
		
		strade.get(34).aggiungiStrade(4, "nord");
		strade.get(34).aggiungiStrade(3, "ovest");
		strade.get(34).aggiungiStrade(35, "est");
		strade.get(34).aggiungiStrade(33, "sud");
		
		strade.get(35).aggiungiStrade(9, "nord-est");
		strade.get(35).aggiungiStrade(34, "nord-ovest");
		strade.get(35).aggiungiStrade(36, "sud-est");
		strade.get(35).aggiungiStrade(33, "sud-ovest");
		
		strade.get(36).aggiungiStrade(9, "nord");
		strade.get(36).aggiungiStrade(35, "ovest");
		strade.get(36).aggiungiStrade(37, "est");
		strade.get(36).aggiungiStrade(38, "sud");
		
		strade.get(37).aggiungiStrade(14, "nord-est");
		strade.get(37).aggiungiStrade(36, "nord-ovest");
		strade.get(37).aggiungiStrade(15, "sud-est");
		strade.get(37).aggiungiStrade(38, "sud-ovest");
		
		strade.get(38).aggiungiStrade(36, "nord");
		strade.get(38).aggiungiStrade(37, "est");
		strade.get(38).aggiungiStrade(40, "ovest");
		strade.get(38).aggiungiStrade(39, "sud");
		
		strade.get(39).aggiungiStrade(38, "nord");
		strade.get(39).aggiungiStrade(40, "ovest");
		strade.get(39).aggiungiStrade(19, "est");
		strade.get(39).aggiungiStrade(20, "sud");
		
		strade.get(40).aggiungiStrade(38, "nord-est");
		strade.get(40).aggiungiStrade(32, "nord-ovest");
		strade.get(40).aggiungiStrade(39, "sud-est");
		strade.get(40).aggiungiStrade(41, "sud-ovest");
		
		strade.get(41).aggiungiStrade(32, "nord-ovest");
		strade.get(41).aggiungiStrade(40, "nord-est");
		strade.get(41).aggiungiStrade(25, "sud-ovest");
		strade.get(41).aggiungiStrade(24, "sud-est");
		
		//regioni
		regioni.add(new Regione(0,TipoTerreno.SHEEPSBURG));
		regioni.add(new Regione(1,TipoTerreno.GRANO));
		regioni.add(new Regione(2,TipoTerreno.PRATERIA));
		regioni.add(new Regione(3,TipoTerreno.FORESTA));
		regioni.add(new Regione(4,TipoTerreno.ACQUA));
		regioni.add(new Regione(5,TipoTerreno.SABBIA));
		regioni.add(new Regione(6,TipoTerreno.ROCCIA));
		regioni.add(new Regione(7,TipoTerreno.GRANO));
		regioni.add(new Regione(8,TipoTerreno.PRATERIA));
		regioni.add(new Regione(9,TipoTerreno.PRATERIA));
		regioni.add(new Regione(10,TipoTerreno.FORESTA));
		regioni.add(new Regione(11,TipoTerreno.FORESTA));
		regioni.add(new Regione(12,TipoTerreno.ACQUA));
		regioni.add(new Regione(13,TipoTerreno.ACQUA));
		regioni.add(new Regione(14,TipoTerreno.SABBIA));
		regioni.add(new Regione(15,TipoTerreno.SABBIA));
		regioni.add(new Regione(16,TipoTerreno.ROCCIA));
		regioni.add(new Regione(17,TipoTerreno.ROCCIA));
		regioni.add(new Regione(18,TipoTerreno.GRANO));
	}

	// aggiungi giocatore
	public void aggiungiPastore(String nomeGiocatore, int turnoDiGioco)
	{
		pastori.add(new Pastore(nomeGiocatore,turnoDiGioco));
	}
	
	
	// movimento pastore
	private void muoviPastore()
	{
		
	}
	
	
	/*
	 * 
	 *
	 * 
	 *  METODI DI SERVIZIO
	 * 
	 * 
	 * 
	 * 
	 */
	 
	
	private int lancioDado()
	{
		return (int)(Math.random()*6);
	}
	
}