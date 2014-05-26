package it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneClient;

import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.InterfacciaThreadRMI;
import it.polimi.deib.provaFinale2014.matteo.daverio_valerio.demaria.comunicazioneServer.ThreadRMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientRMI implements InterfacciaClientRMI {
	
	//TODO da chiedere all'utente;
	static String nome ="Valerio";
	static String password="Ramponio";
	
	//TODO CAPIRE PERCHè NON VUOLE OVERRIDE
	//@Override
	public String comunicaClient(String messaggio){
		System.out.println("il server mi ha detto"+messaggio);
		return "Ricevuto";
	}
	
	public static void main(String[] args) {
		
		//IL CLIENT SI CONNETTE AL SERVER
		try{
        	
			//cerco il registry del server
			Registry registry = LocateRegistry.getRegistry("localhost",
					ThreadRMI.SERVER_PORT);
			
			//scarico l'oggetto remoto del server
			InterfacciaThreadRMI comp = (InterfacciaThreadRMI) registry
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
						ThreadRMI.SERVER_PORT);
				
				//associo all'oggetto esportato il nome formato da 
				//nomeGiocatore+password
				registry.rebind(nome+password, stub);
				
			}catch(RemoteException e){
				System.err.println("ComputeEngine exception:");
				e.printStackTrace();
			}
		
	}//FINE MAIN


}
