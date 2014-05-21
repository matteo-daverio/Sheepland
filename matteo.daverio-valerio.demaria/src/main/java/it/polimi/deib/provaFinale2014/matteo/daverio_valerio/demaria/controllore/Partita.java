package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.Costanti;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.CannotProcreateException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.IllegalShireException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.InvalidMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMoneyException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.exception.NoMovementException;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Direzione;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Lupo;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.PecoraNera;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Partita {

	private int numeroGiocatori;
	private int turno;
	private int contatoreRecinti;
	private ArrayList<Pastore> pastori;
	private ArrayList<Strada> strade;

	private ArrayList<Pecora> pecore;
	private Lupo lupo;
	private PecoraNera pecoraNera;
	private static final Map<Integer, TipoTerreno> mappaRegioni = new HashMap<Integer, TipoTerreno>();

	// costruttori

	public Partita() // costruttore vuoto
	{
		this.turno = 1;
		this.contatoreRecinti = 0;
		this.strade = new ArrayList<Strada>();

		this.pastori = new ArrayList<Pastore>();
		this.pecore = new ArrayList<Pecora>();
		this.pecoraNera = new PecoraNera();
		this.lupo = new Lupo();

	}

	// getter e setter

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	public int getContatoreRecinti() {
		return contatoreRecinti;
	}

	public PecoraNera getPecoraNera() {
		return pecoraNera;
	}

	public Lupo getLupo() {
		return lupo;
	}

	public void setNumeroGiocatori(int numeroGiocatori) {
		this.numeroGiocatori = numeroGiocatori;
	}

	public int getNumeroGiocatori() {
		return numeroGiocatori;
	}

	public ArrayList<Strada> getStrade() {
		return strade;
	}

	public ArrayList<Pecora> getPecore() {
		return pecore;
	}

	public ArrayList<Pastore> getPastori() {
		return pastori;
	}

	/*
	 * 
	 * 
	 * 
	 * METODI DI PARTITA
	 */

	public void start() {
		inizializza();
	}

	// inizializzazione della classe
	private void inizializza() {
		creaMappa();
		creaPecore();
	}

	private void creaMappa() {
		// strade
		strade.add(new Strada(0, 9, 8, 1));
		strade.add(new Strada(1, 1, 8, 3));
		strade.add(new Strada(2, 7, 8, 2));
		strade.add(new Strada(3, 1, 7, 4));
		strade.add(new Strada(4, 6, 7, 6));
		strade.add(new Strada(5, 18, 7, 3));
		strade.add(new Strada(6, 18, 6, 2));
		strade.add(new Strada(7, 17, 18, 1));
		strade.add(new Strada(8, 17, 6, 3));
		strade.add(new Strada(9, 5, 6, 4));
		strade.add(new Strada(10, 5, 17, 5));
		strade.add(new Strada(11, 16, 17, 2));
		strade.add(new Strada(12, 16, 5, 3));
		strade.add(new Strada(13, 15, 16, 1));
		strade.add(new Strada(14, 15, 5, 6));
		strade.add(new Strada(15, 15, 4, 2));
		strade.add(new Strada(16, 15, 14, 5));
		strade.add(new Strada(17, 14, 4, 4));
		strade.add(new Strada(18, 14, 13, 1));
		strade.add(new Strada(19, 4, 13, 6));
		strade.add(new Strada(20, 13, 3, 2));
		strade.add(new Strada(21, 13, 12, 5));
		strade.add(new Strada(22, 12, 3, 1));
		strade.add(new Strada(23, 12, 11, 2));
		strade.add(new Strada(24, 3, 11, 3));
		strade.add(new Strada(25, 11, 2, 4));
		strade.add(new Strada(26, 11, 10, 1));
		strade.add(new Strada(27, 2, 10, 2));
		strade.add(new Strada(28, 10, 9, 4));
		strade.add(new Strada(29, 2, 9, 3));
		strade.add(new Strada(30, 1, 9, 2));
		strade.add(new Strada(31, 2, 1, 1));
		strade.add(new Strada(32, 0, 2, 5));
		strade.add(new Strada(33, 0, 1, 6));
		strade.add(new Strada(34, 6, 1, 5));
		strade.add(new Strada(35, 0, 6, 1));
		strade.add(new Strada(36, 5, 0, 2));
		strade.add(new Strada(37, 4, 5, 1));
		strade.add(new Strada(38, 4, 0, 3));
		strade.add(new Strada(39, 4, 3, 5));
		strade.add(new Strada(40, 3, 0, 4));
		strade.add(new Strada(41, 3, 2, 6));

		// direzioni
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

		// associazione regione-tipo terreno
		mappaRegioni.put(0, TipoTerreno.SHEEPSBURG);
		mappaRegioni.put(1, TipoTerreno.GRANO);
		mappaRegioni.put(2, TipoTerreno.PRATERIA);
		mappaRegioni.put(3, TipoTerreno.FORESTA);
		mappaRegioni.put(4, TipoTerreno.ACQUA);
		mappaRegioni.put(5, TipoTerreno.SABBIA);
		mappaRegioni.put(6, TipoTerreno.ROCCIA);
		mappaRegioni.put(7, TipoTerreno.GRANO);
		mappaRegioni.put(8, TipoTerreno.PRATERIA);
		mappaRegioni.put(9, TipoTerreno.PRATERIA);
		mappaRegioni.put(10, TipoTerreno.FORESTA);
		mappaRegioni.put(11, TipoTerreno.FORESTA);
		mappaRegioni.put(12, TipoTerreno.ACQUA);
		mappaRegioni.put(13, TipoTerreno.ACQUA);
		mappaRegioni.put(14, TipoTerreno.SABBIA);
		mappaRegioni.put(15, TipoTerreno.SABBIA);
		mappaRegioni.put(16, TipoTerreno.ROCCIA);
		mappaRegioni.put(17, TipoTerreno.ROCCIA);
		mappaRegioni.put(18, TipoTerreno.GRANO);

	}

	private void creaPecore() {
		for (int i = 0; i <= Costanti.NUMERO_PECORE - 1; i++)
			pecore.add(new Pecora());
	}

	// incremento del turno
	// @author Matteo Daverio
	public void incrementaTurno() {
		if (turno == numeroGiocatori)
			turno = 1;
		else
			turno++;
	}

	// aggiungi giocatore
	// @author Matteo Daverio
	public void aggiungiPastore(String nomeGiocatore, int turnoDiGioco) {
		pastori.add(new Pastore(nomeGiocatore, turnoDiGioco));
	}

	// controllo movimento pastore
	// @author Matteo Daverio
	public void muoviPastore(int posizione) throws NoMovementException,
			NoMoneyException, InvalidMovementException {
		Pastore pastore = pastori.get(turno - 1);
		if (pastore.getPosizione() == posizione) {
			// posizione arrivo uguale a posizione di partenza
			throw new NoMovementException();
		} else if (movimentoValido(posizione)
				&& mossaSenzaSpesa(posizione,
						strade.get(pastore.getPosizione()))) {
			// movimento valido e senza spese, quindi lo esegue
			spostamentoPastore(pastore, posizione);

		} else if (movimentoValido(posizione)) {
			// movimento valido ma con spesa di denaro
			if (pastore.getDenaro() >= 1) {
				// denaro sufficiente
				pastore.setDenaro(pastore.getDenaro() - 1);
				spostamentoPastore(pastore, posizione);
			} else {
				// denaro insufficiente
				throw new NoMoneyException();
			}
		} else {
			// movimento invalido
			throw new InvalidMovementException();
		}

	}

	// movimento pecora nera
	// @author Matteo Daverio
	public void muoviPecoraNera() {
		pecoraNera.fugaPecoraNera(lancioDado(), pastori, strade);
	}

	// movimento lupo
	public boolean muoviLupo() {
		lupo.muoviLupo(lancioDado(), strade);
		return lupo.mangiaPecora(pecore);
	}

	// trasforma agnello
	public void trasformaAgnelli() {
		for (Pecora pecora : pecore)
			if (pecora.getTipoPecora() == Costanti.TIPO_PECORA_AGNELLO)
				pecora.incrementaTurno();
	}

	// abbattimento
	public void abbatti() {
		if (denaroSufficente()) {
			// TODO terminare il metodo abbattimento
		}
	}

	// accoppiamento
	public void accoppia(int posizione) throws IllegalShireException,
			CannotProcreateException {
		if (regioneAdiacente(posizione)) {
			if (esisteMontone(posizione) && esistePecora(posizione))
				pecore.add(new Pecora(posizione, Costanti.TIPO_PECORA_AGNELLO));
			else {
				// nel terreno scelto non ci sono sia pecore che montoni
				throw new CannotProcreateException();
			}
		} else {
			// il terreno scelto non è adiacente al pastore
			throw new IllegalShireException();
		}
	}

	/*
	 * 
	 * 
	 * 
	 * METODI DI SERVIZIO
	 */
	// controllo che il pastore abbia denaro sufficente a comprare il silenzio
	private boolean denaroSufficente() {

		ArrayList<Direzione> stradeLimitrofe = strade.get(
				pastori.get(turno - 1).getPosizione()).getStrade();
		for (Direzione direzione : stradeLimitrofe) {
			// TODO vedere se ci sono pastori nelle vicinanze
			// utilizza il metodo gia scritto boolean stradaOccupata(int
			// posizioneStrada)
		}
		return true;
	}

	// controllo che la regione sia una delle due adiacenti al pastore che
	// attualmente sta giocando
	private boolean regioneAdiacente(int posizione) {
		if (strade.get(pastori.get(turno - 1).getPosizione())
				.getRegioneDestra() == posizione
				|| strade.get(pastori.get(turno - 1).getPosizione())
						.getRegioneSinistra() == posizione)
			return true;
		else
			return false;
	}

	private boolean esisteMontone(int posizione) {
		for (Pecora pecora : pecore) {
			if ((pecora.getPosizione() == posizione)
					&& (pecora.getTipoPecora() == Costanti.TIPO_PECORA_MONTONE))
				return true;
		}
		return false;
	}

	private boolean esistePecora(int posizione) {
		for (Pecora pecora : pecore) {
			if ((pecora.getPosizione() == posizione)
					&& (pecora.getTipoPecora() == Costanti.TIPO_PECORA_PECORA))
				return true;
		}
		return false;
	}

	// simula il lancio di un dado
	// @author Matteo Daverio
	private int lancioDado() {
		return (int) (Math.random() * 6);
	}

	// controlla se la mossa richiesta dal pastore richiede dispendio di denaro
	// @author Matteo Daverio
	private boolean mossaSenzaSpesa(int posizioneArrivo, Strada strada) {
		for (Direzione stradaAdiacente : strada.getStrade()) {
			if (stradaAdiacente.getPosizione() == posizioneArrivo)
				return true;
		}
		return false;
	}

	// controlla se la posizione di arrivo ha un pastore o un recinto che la
	// occupa
	// @author Matteo Daverio
	private boolean movimentoValido(int posizioneArrivo) {
		return !stradaRecintata(posizioneArrivo)
				&& !stradaOccupata(posizioneArrivo);
	}

	// controlla se la strada è recintata
	// @author Matteo Daverio
	private boolean stradaRecintata(int posizioneStrada) {
		return strade.get(posizioneStrada).recintata();
	}

	// controlla se sulla strada c'è un pastore
	// @author Matteo Daverio
	private boolean stradaOccupata(int posizioneStrada) {
		for (Pastore pastore : pastori) {
			if (pastore.getPosizione() == posizioneStrada)
				return true;
		}
		return false;
	}

	// movimento effettivo del pastore
	// @author Matteo Daverio
	private void spostamentoPastore(Pastore pastore, int posizione) {
		strade.get(pastore.getPosizione()).aggiungiRecinto();
		contatoreRecinti++;
		pastore.setPosizione(posizione);
	}
}