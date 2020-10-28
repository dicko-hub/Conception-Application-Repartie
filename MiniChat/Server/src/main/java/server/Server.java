package server;


import Chatroom.ChatRoom;
import interfaces.ChatInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class Server
{	
	private ChatInterface ChatRoom;
	
	public Server() throws AlreadyBoundException, IOException {
		ChatRoom = new ChatRoom();
		LocateRegistry .createRegistry(1099);
		Naming.rebind("rmi://localhost:1099/chatroom", ChatRoom);
		System.out.println("Enregistrement de l'objet avec l'url : chatroom");

	    System.out.println("Serveur lancé");
	}
	
	
	public static void main(String[] args)  {
		try {
			Server server = new Server();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}