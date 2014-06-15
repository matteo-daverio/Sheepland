package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

import java.io.Serializable;

public enum ComandiSocket implements Serializable{
	
	//DA SERVER A CLIENT
	
	MOVIMENTO_PECORA_NERA,MOVIMENTO_LUPO,
	
	//gestione turno
	CAMBIO_TURNO,INIZIO_TURNO,
	
	NUMERO_RECINTI,
	
	AGGIORNAMENTO_DENARO,
	
	FASE_FINALE, PUNTEGGI,
	
	//in caso di disconnessione
	AGGIORNAMENTO,
	
	//aggiornamento clients
	MOVIMENTO_PASTORE, ABBATTIMENTO,ACCOPPIAMENTO, MOVIMENTO_PECORA, ACQUISTO_TESSERA,
	
	RICHIESTA_DI_MOSSA,MOSSA_SBAGLIATA,MOSSA_CORRETTA,
	
	//login
	RICHIESTA_NOME,RICHIESTA_PASSWORD,AUTENTICAZIONE_RIUSCITA,AUTENTICAZIONE_FALLITA,
	LOG_IN,
	
	//posizionamento pastori
	RICHIESTA_POSIZIONE_PASTORE,POSIZIONAMENTO_PASTORE,
	
	//invio liste iniziali
	DATI_GIOCATORI,INVIO_PECORE,TESSERA_INIZIALE,INVIO_STRADE,
	
	//DA CLIENT A SERVER
	
	INVIO_POSIZIONE_PASTORE,
	INVIO_MOSSA
}
