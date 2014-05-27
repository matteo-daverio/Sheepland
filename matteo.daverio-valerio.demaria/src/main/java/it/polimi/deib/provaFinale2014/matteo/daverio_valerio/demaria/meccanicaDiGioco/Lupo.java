package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco;

import java.util.ArrayList;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;

public class Lupo {

	private int posizione;

	// costruttori

	public Lupo() {
		this.posizione = Costanti.POSIZIONE_SHEEPBURG;
	}

	// getter e setter

	public int getPosizione() {
		return posizione;
	}

	public void setPosizione(int pos) {
		posizione = pos;
	}

	/**
	 * metodo che esegue il movimento del lupo
	 * 
	 * @param lancioDado
	 * @param strade
	 * @return nuova posizione del lupo
	 * @author Matteo Daverio
	 */
	public int muoviLupo(int lancioDado, ArrayList<Strada> strade) {
		// strada su cui il lupo DOVREBBE passare
		Strada strada = esisteStrada(lancioDado, strade);
		// controlla se la strada esiste
		if (strada != null) {
			// se non è recintata, oppure tutte le strade intorno sono recintate
			if (!strada.recintata() || tutteRecintate(strade))
				// il lupo si muove
				effettuaMovimento(strada);
		}
		return posizione;
	}

	/**
	 * se esiste una pecora nel terreno, il lupo la mangia
	 * 
	 * @param pecore
	 * @return true se il lupo ha mangiato una pecora
	 * @author Matteo Daverio
	 */
	public boolean mangiaPecora(ArrayList<Pecora> pecore) {
		for (Pecora pecora : pecore) {
			if (posizione == pecora.getPosizione()) {
				pecore.remove(pecora);
				return true;
			}
		}
		return false;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * METODI DI SUPPORTO
	 */

	// restituisce, se esiste, la strada adiacente al terreno del lupo con il
	// numero del dado uscito dal lancio
	private Strada esisteStrada(int lancioDado, ArrayList<Strada> strade) {
		for (Strada strada : strade) {
			if (stradaAdiacente(strada) && strada.getNumeroDado() == lancioDado)
				return strada;
		}
		return null;
	}

	// controlla se il lupo si trova su un terreno adiacente alla strada passata
	private boolean stradaAdiacente(Strada strada) {
		return strada.getRegioneDestra() == posizione
				|| strada.getRegioneSinistra() == posizione;
	}

	// il lupo attraversa la strada
	private void effettuaMovimento(Strada strada) {
		if (posizione == strada.getRegioneDestra())
			posizione = strada.getRegioneSinistra();
		else
			posizione = strada.getRegioneDestra();
	}

	// controlla se tutte le strade intorno al lupo sono recintate
	private boolean tutteRecintate(ArrayList<Strada> strade) {
		for (Strada strada : strade) {
			// se la strada è adiacente al lupo e non è recintata
			if (stradaAdiacente(strada) && !strada.recintata()) {
				return false;
			}
		}
		return true;
	}
}
