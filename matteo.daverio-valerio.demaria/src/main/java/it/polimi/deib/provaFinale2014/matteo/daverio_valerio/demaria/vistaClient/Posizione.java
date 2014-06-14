package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.vistaClient;


/**
 * 
 * @author Matteo Daverio
 *
 */
public class Posizione {
	
	private String tipo;
	private int posizione;
	
	/**
	 * costruisce un oggetto posizione, che indica se è strada o regione e la sua posizione
	 * 
	 * @param tipo
	 * @param posizione
	 * @author Matteo Daverio
	 */
	public Posizione(String tipo, int posizione) {
		this.tipo=tipo;
		this.posizione=posizione;
	}
	
	/**
	 * 
	 * @return tipo di luogo cliccato
	 * @author Matteo Daverio
	 */
	public String getTipo() {
		return tipo;
	}
	
	/**
	 * 
	 * @return posizione del luogo cliccato
	 * @author Matteo Daverio
	 */
	public int getPosizione() {
		return posizione;
	}
	
	/**
	 * setta se la posizione è una strada o una regione
	 * 
	 * @param tipo
	 * @author Matteo Daverio
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * 
	 * @param pos
	 * @author Matteo Daverio
	 */
	public void setPosizione(int pos) {
		this.posizione = pos;
	}

	@Override
	/**
	 * override del metodo equals per le hashmap
	 * 
	 * @author Matteo Daverio
	 */
	public boolean equals(Object o) {
		if(o instanceof Posizione && ((Posizione) o).getPosizione()==this.posizione && ((Posizione) o).getTipo().equals(this.tipo)) {
			return true;
		}
		return false;
	}

	@Override
	/**
	 * override del metodo di hashing per le hashmap
	 * 
	 * @author Matteo Daverio
	 */
	public int hashCode() {
		int hash = 5;
        hash = 89 * hash + (this.tipo != null ? this.tipo.hashCode() : 0);
        hash = 89 * hash + (int) (this.posizione ^ (this.posizione >>> 32));
        hash = 89 * hash + this.posizione;
        return hash;
	}
}
