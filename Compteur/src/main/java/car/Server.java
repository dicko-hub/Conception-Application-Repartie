package car;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class Server
{	
	private ServerSocket Socket;
	
	private ExecutorService ThreadPool;
	private final int NbThreadsParClient;
	
    public Server(int nbClients, int nbThreadsParClient) throws IOException {
    	Socket = new ServerSocket(4000);
    	ThreadPool = Executors.newFixedThreadPool(nbClients);
    	NbThreadsParClient = nbThreadsParClient;
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
    		Socket.close();
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
			while (true) {
				WaitForClientConnection();
			}
		} catch (IOException e) {
			Log("Une erreur est survenue : " + e.getMessage());
		}
	}
	
	/**
	 * Attend la connection d'un client et initialise les canaux de communication
	 * @throws IOException
	 */
	private void WaitForClientConnection() throws IOException {
		Socket as = Socket.accept();
    	Log("Connection established");
    	BufferedReader in = new BufferedReader(
    	new InputStreamReader(as.getInputStream()));
    	DataOutputStream out = new DataOutputStream(as.getOutputStream());
    	ClientConnectionRunnable client = new ClientConnectionRunnable(in, out, NbThreadsParClient);
    	ThreadPool.submit(client);
	}

	/**
	 * Main method, lance le serveur
	 * @param args aucun argument à donner
	 */
	public static void main( String[] args )
    {
		try {
			int nbClients = Integer.parseInt(args[0]);
			int nbThreadsParClient = Integer.parseInt(args[1]);
			if (nbClients < 1 || nbThreadsParClient < 1) throw new NumberFormatException();
			Server server = new Server(nbClients, nbThreadsParClient);
			server.Run();
		} catch (NumberFormatException e) {
			System.out.println("Paramètres incorrects, paramètres : ");
			System.out.println("paramètre 1 : le nombre de clients acceptables > 0");
			System.out.println("paramètre 2 : le nombre de threads octroyés par client > 0");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Il manque un ou plusieurs paramètres");
		}
		System.exit(0);
    }
}