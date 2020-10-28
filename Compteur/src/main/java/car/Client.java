package car;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

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
	 * Essaye de fermer la connection avec le client
	 * @return True si réussi, False sinon
	 */
	public boolean TryClose() {
    	try {
    		In.close();
        	Out.close();
        	Socket.close();
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
			if (args.length == 1) client.RunWithFile(args[0]);
			else client.RunWithUserInput();
			client.PrintServerAnswer();
			client.TryClose();
		} catch (UnknownHostException ex){
			System.out.println("Hote inconnu");
			System.exit(-1);
		} catch (IOException e) {
			System.out.println("Une erreur est survenue : " + e.getMessage());
			System.exit(-1);
		}
    }
	
	/**
	 * Envoie au serveur les lignes du fichier donné 
	 * @param filename le nom du fichier
	 */
	protected void RunWithFile(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String line = "";
			while (!line.equals(":::END:::\n")) {
				line = br.readLine();
				if (line == null) line = ":::END:::";
				line += "\n";
				Out.writeBytes(line);
			}
		} catch (IOException e) {
    		Log("");
    	}
	}
	
	/**
	 * Reçoit du serveur la liste des mots les plus utilisés et l'écris
	 * @throws IOException
	 */
	public void PrintServerAnswer() throws IOException {
		int nbWords = Integer.parseInt(In.readLine());
		Log("Liste des " + nbWords + " mots les plus utilises");
		for (int i = 0; i<nbWords; i++) {
			Log("- " + In.readLine());
		}
		int nbuse = Integer.parseInt(In.readLine());
		Log("Ils ont ete utilises " + nbuse + " fois.");
	}

	/**
	 * Envoie les messages que l'utilisateur écrit
	 * Pour mettre fin à l'écriture, le message à écrire est ":::END:::"
	 */
	protected void RunWithUserInput() {
		Scanner in = new Scanner(System.in);
		String line = "";
    	try {
    		while (!line.equals(":::END:::\n")) {
    			line = in.nextLine() + "\n";
    			Out.writeBytes(line);
        	}
    	} catch (IOException e) {
    		Log("");
    	}
    	
	}

}
