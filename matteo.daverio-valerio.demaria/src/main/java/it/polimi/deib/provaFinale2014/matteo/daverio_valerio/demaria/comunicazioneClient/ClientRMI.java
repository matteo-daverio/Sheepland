package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaGestioneRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.ServerApplication;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * applicazione del client
 * @author Valerio De Maria
 *
 */
public class ClientRMI implements InterfacciaClientRMI {
	
	//TODO da chiedere all'utente;
	static String nome ="Valerio";
	static String password="Ramponio";
	
	//InterfacciaPartia partita;
	
	
	public void iniziaPartita(String messaggio){
		
		//cerco il registry del server
		//Registry registry = LocateRegistry.getRegistry("localhost",
			//	ServerApplication.SERVER_PORT_RMI);
		
		
		//partita = (InterfacciaPartita) registry.lookup(messaggio);
		
		System.out.println("il server mi ha detto"+messaggio);
		
		
	}
	
	public static void main(String[] args) {
		
		//IL CLIENT SI CONNETTE AL SERVER
		try{
        	
			//cerco il registry del server
			Registry registry = LocateRegistry.getRegistry("localhost",
					ServerApplication.SERVER_PORT_RMI);
			
			//scarico l'oggetto remoto del server
			InterfacciaGestioneRMI comp = (InterfacciaGestioneRMI) registry
					.lookup("serverInAttesa");
			
			
			//eseguo il metodo registrazione dell'oggetto remoto del server
			String result = comp.registrazione(nome,password);
			
		} catch (RemoteException e) {
			System.err.println("Remote exception:");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("Name " + "istanza"
					+ " not bound.");
		}
	 
		//IL CLIENT CREA UN SUO OGGETTO REMOTO SUL REGISTRY TRAMITE CUI IL SERVER
		//PUò COMUNICARGLI
		 try{
				
			    //creo l'oggetto da caricare sul registry
				InterfacciaClientRMI client =new ClientRMI();
				
				//esporto l'oggetto sull registry
				InterfacciaClientRMI stub =(InterfacciaClientRMI)UnicastRemoteObject.exportObject(client,0);
			    
				//cerco il registry del server
				Registry registry = LocateRegistry.getRegistry("localhost",
						ServerApplication.SERVER_PORT_RMI);
				
				//associo all'oggetto esportato il nome formato da 
				//nomeGiocatore+password
				registry.rebind(nome+password, stub);
				
			}catch(RemoteException e){
				System.err.println("ComputeEngine exception:");
				e.printStackTrace();
			}
		
	}//FINE MAIN


}
