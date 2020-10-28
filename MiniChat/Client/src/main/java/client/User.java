package client;

import interfaces.ChatInterface;
import interfaces.ClientInterface;
import interfaces.MessageInterface;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

import static java.rmi.Naming.lookup;

public class User extends UnicastRemoteObject implements ClientInterface {

    private static Scanner in = new Scanner(System.in);
    private static ChatInterface ChatRoom;
    private String Nom;
    private String Login;
    private String Pwd;
    public static boolean connecte =false;
    private  LocalDateTime lastTime ;
    private  long estimateTime;
    private Login Log;

    public User(Login l,String nom,String login, String pwd) throws MalformedURLException, RemoteException,NotBoundException {
        super();
        Remote chatRoom = lookup("rmi://localhost:1099/chatroom");
        ChatRoom = (ChatInterface) chatRoom;
        System.out.println("Lancement du client");
        Nom = nom;
        Login = login;
        Pwd = pwd;
        lastTime = LocalDateTime.now();
        Log=l;
        connecte=ChatRoom.Connect(this);
        System.out.println("connecte : " +connecte);
        if (connecte) ChatRoom.SendMsgToAllClients(new Message(this.getNom(), Message.ConnectMessage));
    }
    @Override
    public void Receive(MessageInterface msg) throws RemoteException {
        Log.Receive(msg.getDate(),msg.getNomClient(),msg.getTexte());
        System.out.println(msg.ToString());
    }

    @Override
    public void ReceiveList(int max)  {
        System.out.println("L'historrique contient au max "+max+" messages");
    }

    public String getLogin() {
        return Login;
    }

    public String getPwd() {
        return Pwd;
    }

    public String getNom() {
        return Nom;
    }

    /*
     * permet d'enlever le client de la liste des clients connectée ainsi
     * que du registre du serveur
     */
    private void Stop() {
        try {
            connecte=ChatRoom.Disconnect(this);
            if(connecte){
                ChatRoom.SendMsgToAllClients(new Message(this.getNom(),
                        Message.DisconnectMessage+" il ya "+(estimateTime-30)+" secondes"));
            }
        } catch (RemoteException e) {
            System.out.println("Une erreur est survenue lors de la déconnection."+e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * ....????
     */
    public boolean checkSend(String msg) throws RemoteException{
        if ( (estimateTime = Duration.between(lastTime,LocalDateTime.now()).getSeconds() )<=30){
            String [] text = msg.split(" : ");
            if(text.length==1){
                ChatRoom.SendMsgToAllClients(new Message(this.getNom(), msg));
            }else{
                if(text[0].equals("get")) {
                    ChatRoom.SendList(this.getNom(), Integer.parseInt(text[1]));
                } else
                    ChatRoom.Send(text[0],new Message(this.getNom(), text[1]));
            }
            lastTime = LocalDateTime.now();
            return true;
        }else
            return false;
    }

    public static boolean isConnecte() {
        return connecte;
    }
}
