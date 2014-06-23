package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import java.net.Socket;
import java.util.List;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.MosseEnum;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.TipoTerreno;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pastore;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Pecora;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Strada;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.meccanicaDiGioco.Tessera;

/**
 * metodi per comunicare al client
 * 
 * @author Valerio De Maria
 * 
 */
public interface InterfacciaComunicazioneToClient {

	public void chiudiConnessione();

	public void comunicaMovimentoPecoraNera(int nuovaPosizione);

	public void comunicaMovimentoLupo(int nuovaPosizione);

	// AGGIORNANO I CLIENTS CHE NON SONO DEL TURNO
	public void comunicaMovimentoPastore(int posizione, String giocatore,
			int pastore);

	public void comunicaAcquistaTessera(TipoTerreno terreno, String giocatore,
			int pastore);

	public void comunicaAbbattimento(int regione, int pecora, String giocatore,
			int pastore);

	public void comunicaAccoppiamento(int regione, String giocatore, int pastore);

	public void comunicaMovimentoPecora(int pecora, int strada,
			String giocatore, int pastore);

	public void comunicaSpostamentoPecoraNera(int strada, String giocatore,
			int pastore);

	public void comunicaPecore(List<Pecora> pecore);

	public void comunicaStrade(List<Strada> strade);

	public void comunicaPosizionamentoPastore(int turno, int pastore,
			int posizione);

	public void comunicaFaseFinale();

	public String getNome();

	public String getTipoConnessione();

	public void setSocket(Socket socket);

	public void inviaDatiGiocatori(List<String> nomi, List<Integer> soldi);

	public void comunicaTurno(List<Pecora> pecore, int turno);

	public void aggiornaTurno(String giocatore, List<Pecora> pecore);

	public void inviaTesseraIniziale(Tessera tesseraIniziale);

	public void comunicaMossaSbagliata();

	public void mossaCorretta();

	public void inviaPunteggi(List<Integer> punteggiFinali, List<String> nomi);

	public void comunicaDenaro(List<Integer> denaroPastori);

	public void comunicaNumeroRecinti(int recinti);

	public void aggiornamento(List<Pecora> pecore, int posPecoraNera,
			int posLupo, List<Pastore> pastori);

	// ///METODI NUOVI

	public void inviaRichiestaMossa(List<MosseEnum> mosseDisponibili);

	public void inviaRichiestaPosizionamento(List<Integer> stradeDisponibili);

	public void comunicaDisconnessione(String nome);

	public void comunicaRiconnessione(String nome);

	public void comunicaEsclusione(String nome);

	public void ping();
}
