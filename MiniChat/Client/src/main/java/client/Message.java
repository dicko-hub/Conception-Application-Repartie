package client;


import interfaces.MessageInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.util.Date;

public class Message extends UnicastRemoteObject implements MessageInterface {
    public static final String DisconnectMessage = "Deconnecte.";
    public static final String ConnectMessage = "Connecte.";

    private String NomClient;
    private String Texte;
    private Date Date;

    public Message(String nom, String texte) throws RemoteException {
        super();
        NomClient =nom;
        Texte = texte;
        Date = Date.from(Instant.now());
    }

    //Permet de decrire le contenu d'un object Message
    public String ToString() {
        return String.format("%s | %s -> %s", Date, NomClient, Texte);
    }

    public String getTexte() {
        return Texte;
    }

    public String getNomClient() {
        return NomClient;
    }
    public Date getDate(){
        return Date;
    }
}
