package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Serializable ,Remote
{
	void Receive(MessageInterface m) throws RemoteException;
	void ReceiveList(int max) throws RemoteException;
	String getLogin() throws RemoteException;
	String getPwd()throws RemoteException;
	String getNom() throws RemoteException;
}