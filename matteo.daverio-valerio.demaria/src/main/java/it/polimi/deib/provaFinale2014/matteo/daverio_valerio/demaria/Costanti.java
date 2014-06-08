package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria;

public class Costanti {

	// costanti per il server
	public static final int PORTA_SOCKET = 3000;
	public static final int PORTA_RMI = 12189;
	public static final long PERIODO_AVVIO_PARTITE = 40*1000;
	public final static String SERVER_NAME = "compute";

	// costanti per le pecore

	public static final int TIPO_PECORA_AGNELLO = 0;
	public static final int TIPO_PECORA_PECORA = 1;
	public static final int TIPO_PECORA_MONTONE = 2;
	public static final int TIPO_PECORA_PECORANERA = 4;
	public static final int NUMERO_PECORE = 18;
	public static final int TIPI_DI_PECORE = 3;

	// costanti per le strade
	public static final int NUMERO_STRADE = 42;

	// costanti per i terreni
	public static final int POSIZIONE_SHEEPBURG = 0;
	public static final int NUMERO_REGIONI = 18;

	// costanti per la partita
	public static final int NUMERO_MOSSE_GIOCATORE = 3;
	public static final int NUMERO_RECINTI_NORMALI = 20;
	public static final int NUMERO_MASSIMO_GIOCATORI=6;
	
	//per il debug
	public static boolean toDebug=true;

}
