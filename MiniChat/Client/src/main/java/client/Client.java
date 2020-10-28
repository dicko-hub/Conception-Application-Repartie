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
import java.util.Objects;
import java.util.Scanner;

import static java.rmi.Naming.lookup;

public class Client extends UnicastRemoteObject implements ClientInterface {

    private static Scanner in = new Scanner(System.in);
    private static ChatInterface ChatRoom;
    private String Nom;
    private String Login;
    private String Pwd;
    public static boolean connecte =false;
    private  LocalDateTime lastTime ;
    private  long estimateTime;

    public Client(String nom,String login, String pwd) throws MalformedURLException, RemoteException,NotBoundException {
        super();
        Remote chatRoom = lookup("rmi://localhost:1099/chatroom");
        ChatRoom = (ChatInterface) chatRoom;
        System.out.println("Lancement du client");
        Nom = nom;
        Login = login;
        Pwd = pwd;
        lastTime = LocalDateTime.now();
        connecte=ChatRoom.Connect(this);
        System.out.println("connecte : " +connecte);
        if (connecte) ChatRoom.SendMsgToAllClients(new Message(this.getNom(), Message.ConnectMessage));
    }
    /*
     * Permet de recuperer successivement le nom,login et password du client
     * Afin de construire une obeject complet client
     * Cet objet sera ainsi retourner
     */
    private static Client AskUserInformation() {
        System.out.println("Entrer votre nom de plus de 2 lettres");
        String NomClient = in.nextLine();
        while (NomClient.length() < 3) {
            NomClient = in.nextLine();
        }
        System.out.println("Entrer le login de plus de 2 lettres");
        String Login = in.nextLine();
        while (Login.length() < 3) {
            Login = in.nextLine();
        }
        System.out.println("Entrer le password de plus de 2 lettres");
        String Pwd = in.nextLine();
        while (Pwd.length() < 3) {
            Pwd = in.nextLine();
        }
        try {
            return new Client(NomClient,Login,Pwd);
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            System.out.println("Impossible de connecter le client à la chatroom "+ e.getMessage());
            return null;
        }
    }

    @Override
    public void Receive(MessageInterface msg) throws RemoteException {
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
     * Cettte fonction recupere successivement les lignes entrée par le client
     * elle contruit un object message qu'elle envoit au serveur puis attend
     * jusqu'a rencontrer le mot "disconnect" ainsi il va fermer la session client
     */
    public void run() {
        System.out.println("Vous pouvez entrer un message et disconnect pour terminer");
        System.out.println("Pour un message cibler, ecrire 'nomPersonne : message' ");
        System.out.println("Envoyer 'get : nbre' pour les nbre derniers messages du chat");
        String nextMessage = in.nextLine();
        try {
            while (!Objects.equals(nextMessage, "disconnect") && nextMessage !=null ) {
                if(!checkSend(nextMessage)) {
                    System.out.println("Vous avez été déconnecté  il ya "+(estimateTime-30)+" secondes");
                    break;
                }
                nextMessage = in.nextLine();
            }
        } catch (RemoteException e) {
            System.out.println("Une erreur est survenue lors de l'envoie du message "+e.getMessage());
        }
        in.close();
        Stop();
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

    public static void main(String[] args) {
        Client client;
        do client = AskUserInformation();
        while(!connecte);
        if (null != client)client.run();
        System.exit(0);
    }
}
