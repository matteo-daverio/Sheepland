package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.controllore.Partita;
/**
 * classe che comunica con client RMI
 * @author Valerio De Maria
 *
 */
public class ComunicazioneRMI implements InterfacciaComunicazioneClient {

	private String nome,password;
	
	//costruttore
	public ComunicazioneRMI(String nome,String password){
		
		this.nome=nome;
		this.password=password;
		
	}
	
	public void inviaPartita(Partita partita){
		
	}
	
	public void chiudiConnessione(){
		
	}

	public void comunicaMovimentoPecoraNera(int nuovaPosizione) {

		
	}
}
