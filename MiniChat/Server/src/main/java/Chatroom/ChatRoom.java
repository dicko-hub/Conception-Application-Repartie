package Chatroom;

import interfaces.ChatInterface;
import interfaces.ClientInterface;
import interfaces.MessageInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ChatRoom extends UnicastRemoteObject implements ChatInterface {
	
	private static Set<ClientInterface> Clients;
	private static List<MessageInterface> MessagesHistorique;
	private Map<String, String> ClientPasswords;
	private int index=0;

	/*
	 * Initialise la liste des clients connectée,l'historique des messages
	 * Puis charge en memoire les identifiants de connections disponibles 
	 */
	public ChatRoom() throws IOException {
		Clients = new HashSet<>();
		MessagesHistorique = new LinkedList<>();
		ClientPasswords = new HashMap<>();
		FillClientPasswords();
	}

	/*
	 * Recuperer un client, verifie que ses idendtifiantq sont corrects puis l'ajoute a la liste des 
	 * clients connectées avant d'envoyer un message a tout les clients qu'un nouveau vient
	 * d'arriver
	 */
	@Override
	public synchronized boolean Connect(ClientInterface client) throws RemoteException {
		return checkUser(client.getLogin(), client.getPwd()) && Clients.add(client);
	}

	/*
	 * fonction qui envoit un message a un client en particulier a partir 
	 */
	@Override
	public synchronized void Send(String client, MessageInterface msg) throws RemoteException {
		for (ClientInterface c : Clients)
			if((c.getNom()).equals(client))
			 	c.Receive(msg);
	}

	/*
	 * Supprime le client de la liste des connectées avant d'informer les autres de son depart
	 */
	@Override
	public synchronized boolean Disconnect(ClientInterface client) {
		return Clients.remove(client);
	}

	/*
	 * Envoit un message a tout les clients connectées
	 */
	public synchronized void SendMsgToAllClients(MessageInterface msg) throws RemoteException{
		MessagesHistorique.add(index, msg);
		index++;
		for (ClientInterface c : Clients) c.Receive(msg);
	}

    @Override
    public synchronized void SendList(String client, int nbre) throws RemoteException {
		if(MessagesHistorique.size()>(nbre-1)){
			for(int i=MessagesHistorique.size()-(nbre-1); i<=MessagesHistorique.size();i++)
				for (ClientInterface c : Clients)
					if((c.getNom()).equals(client))
						c.Receive(MessagesHistorique.get(i-1));
		} else{
			for (ClientInterface p : Clients)
				if((p.getNom()).equals(client))
					p.ReceiveList(MessagesHistorique.size());
		}
    }

	/*
	 * Recuperer le contenu du fichier passwords et le charge dans un map (login et password)
	 */
	private void FillClientPasswords() throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(new File("passwords.txt")));
		String line = in.readLine();
		while (line != null) {
			String[] user = line.split(" ::: ");
			ClientPasswords.put(user[0], user[1]);
			line = in.readLine();
		}
		in.close();
	}

	
	/*
	 * Permet de verrifier d'un couple login, password correspond bien a une ligne du map ClientPasswords
	 */
	public synchronized boolean checkUser(String login, String pwd){
		if(ClientPasswords.containsKey(login)){
			return (ClientPasswords.get(login)).equals(pwd);
	   	}else{
	   		return false;
	   	}
	}
	
}
