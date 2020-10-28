package car.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class Client
{	
	private Socket Socket;
    private DataOutputStream Out;
	private BufferedReader In;
	
	/**
	 * Initialise les canaux de communication avec le serveur
	 * @param socket le socket pour correspondre avec le serveur
	 * @throws IOException
	 */
    public Client(Socket socket) throws IOException {		
		Socket = socket;
		In = new BufferedReader(
		    	new InputStreamReader(socket.getInputStream()));
		Out = new DataOutputStream(socket.getOutputStream());
	}
    
    /**
     *  affiche un message dans la console
     * @param msg le message à afficher
     */
    private void Log(String msg) {
    	System.out.println(msg);
    }
	
    /**
     * Envoie un message au client et écrit ce message dans la console
     * @param msg le message à afficher
     * @throws IOException
     */
	private void SendMsg(String msg) throws IOException {
    	Log("Send: " + msg);
    	Out.writeBytes(msg);
    }
    
	/**
	 * Attend la réception d'un message du client et l'affiche dans la console
	 * @return le message reçu
	 * @throws IOException
	 */
	private String ReceiveMsg() throws IOException {
    	String msg = In.readLine();
    	Log("Received: " + msg);
    	return msg;
    }
    
	/**
	 * Essaye de fermer la connection avec le client
	 * @return True si réussi, False sinon
	 */
	public boolean TryClose() {
    	try {
    		In.close();
        	Out.close();
        	return true;
    	} catch (IOException e) {
    		return false;
    	}
    }

	/**
	 * Main method, Initiale le client et la connection avec le serveur
	 * @param args aucun paramètres
	 */
	public static void main( String[] args )
    {
		try {
			Socket socket = new Socket(InetAddress.getLocalHost(), 4000);
			Client client = new Client(socket);
			client.Run();
		} catch (UnknownHostException ex){
			System.out.println("Hote inconnu");
			System.exit(-1);
		} catch (IOException e) {
			System.out.println("Une erreur est survenue : " + e.getMessage());
			System.exit(-1);
		}
    }

	/**
	 * Envoie les messages que l'utilisateur écrit afin de le traiter sur le serveur
	 * Pour mettre fin à la connection, le message à écrire est "stop"
	 */
	protected void Run() {
		Scanner in = new Scanner(System.in);
		String expression = "";
    	try {
    		while (!expression.equals("stop\n")) {
    			expression = in.nextLine() + "\n";
    			SendMsg(expression);
    	    	ReceiveMsg();
        	}
        	TryClose();
        	in.close();
        	Socket.close();
    	} catch (IOException e) {
    		Log("");
    	}
    	
	}
}