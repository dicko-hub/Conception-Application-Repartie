package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends Remote {
	boolean Connect(ClientInterface client) throws RemoteException;
	void SendMsgToAllClients(MessageInterface msg) throws RemoteException;
	void SendList(String client,int nbre) throws RemoteException;
	void Send(String client, MessageInterface msg) throws RemoteException;
	boolean Disconnect(ClientInterface client) throws RemoteException;
}