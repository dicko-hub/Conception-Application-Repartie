package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface MessageInterface extends Serializable,Remote {
    public String ToString() throws RemoteException;
    public String getTexte() throws RemoteException;
    public String getNomClient() throws RemoteException;
    public Date getDate() throws RemoteException;
}
