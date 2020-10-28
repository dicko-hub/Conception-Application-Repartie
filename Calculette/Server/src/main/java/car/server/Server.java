package car.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class Server
{	
	private ServerSocket Socket;
    private DataOutputStream Out;
	private BufferedReader In;
	
    public Server() throws IOException {
    	Socket = new ServerSocket(4000);
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
	 * Attends la connection d'un client et tant qu'il est connecter,
	 * va attendre de recevoir un message afin de le traiter et de renvoyer
	 * la réponse au client.
	 */
	public void Run() {
		Log("Server Online");
		try {
			String msg = "";
			while (!msg.equals("close")) {
				WaitForClientConnection();
				msg = ReceiveMsg();
		    	while (!msg.equals("stop")) {
			    	String resp = GetResultFromMsg(msg);
			    	SendMsg(resp);
			    	msg = ReceiveMsg();
		    	}
		    	SendMsg("stopped\n");
		    	if (!TryClose()) {
		    		Log("Cannot close client Connection, leaving!");
		    		msg = "close";
		    	}
			}
		} catch (IOException e) {
			Log("Une erreur est survenue : " + e.getMessage());
		}
	}
	
	/**
	 * Calcule l'opération envoyée par le client
	 * @param msg le message reçu par le client
	 * @return le résultat du parsing et des calculs
	 */
	private String GetResultFromMsg(String msg) {
		String res;
		try {
    		int result = OperationParser.Parse(msg);
    		res = Integer.toString(result);
    	} catch (Exception e) {
    		res = "Une erreur est survenue : " + e.getMessage();
    	}
    	return res + '\n';
	}
	
	/**
	 * Attend la connection d'un client et initialise les canaux de communication
	 * @throws IOException
	 */
	private void WaitForClientConnection() throws IOException {
		Socket as = Socket.accept();
    	Log("Connection established");
    	In = new BufferedReader(
    	new InputStreamReader(as.getInputStream()));
    	Out = new DataOutputStream(as.getOutputStream());
	}

	/**
	 * Main method, lance le serveur
	 * @param args aucun argument à donner
	 */
	public static void main( String[] args )
    {
		try {
			Server instance = new Server();
			instance.Run();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
}