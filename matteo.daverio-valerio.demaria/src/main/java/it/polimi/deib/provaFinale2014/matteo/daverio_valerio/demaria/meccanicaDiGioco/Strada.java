package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import java.util.ArrayList;

public class Strada {
	
	private final int posizione;
	private final int numeroDado; // numero da fare con il dado per passare
	private final int regioneDestra;
	private final int regioneSinistra;
	private boolean recinto;
	private ArrayList<Direzione> strade;
	
	// costruttori
	
	public Strada(int posizione, int regioneDestra, int regioneSinistra, int numeroDado)  // da modificare quando creeremo il metodo inizializzazione
	{
		recinto=false;
		this.posizione=posizione;
		this.regioneDestra=regioneDestra;
		this.regioneSinistra=regioneSinistra;
		this.numeroDado=numeroDado;
	}

	
	// getter e setter 
	
	public boolean isRecinto() {
		return recinto;
	}

	public void aggiungiRecinto() {
		recinto=true;
	}

	public ArrayList<Direzione> getStrade() {
		return strade;
	}

	public void aggiungiStrade(int posizioneStrada, String verso) {
		Direzione direzione=new Direzione(posizioneStrada,verso);
		strade.add(direzione);
	}

	public int getPosizione() {
		return posizione;
	}

	public int getRegioneDestra() {
		return regioneDestra;
	}

	public int getRegioneSinistra() {
		return regioneSinistra;
	}
	
	public int getNumeroDado() {
		return numeroDado;
	}
	
}
