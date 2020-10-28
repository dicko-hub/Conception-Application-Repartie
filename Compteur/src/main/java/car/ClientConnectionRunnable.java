package car;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ClientConnectionRunnable implements Runnable {
	private BufferedReader In;
	private DataOutputStream Out;
	
	private int NbThreads;
	
	public ClientConnectionRunnable(BufferedReader in, DataOutputStream out, int nbThreads) {
		In = in;
		Out = out;
		NbThreads = nbThreads;
	}

	/**
	 * Récupère les lignes à traiter du client
	 * éxécute l'analyse et envoie les données résultantes au client
	 */
	@Override
	public void run() {
		List<String> lines = GetStringListFromClient();
		CompteurMultiThread compteur = new CompteurMultiThread(NbThreads);
		try {
			compteur.Analyse(lines);
			SendResultToClient(compteur.GetMostUsedWords(), compteur.GetNbUse());
		} catch (IOException e) {
			System.out.println("Un problème de connection est survenu avec un client");
		}
	}
	
	/**
	 * Reçoit du client la liste des lignes à traiter
	 * @return la liste
	 */
	private List<String> GetStringListFromClient(){
		List<String> lines = new ArrayList<String>();
		try {
			String msg = In.readLine();
			while (msg != null && !msg.equals(":::END:::")) {
				lines.add(msg);
				msg = In.readLine();
			}
		} catch (IOException e) {
			lines.clear();
		}
		return lines;
	}
	
	/**
	 * Envoie la liste des mots les plus utilisés au client
	 * @param set les mots
	 * @param nbUse le nombre de fois que les mots ont été utilisés
	 * @throws IOException
	 */
	private void SendResultToClient(Set<String> set, int nbUse) throws IOException {
		Out.writeBytes(set.size() + "\n");
		for (String word : set) {
			Out.writeBytes(word + "\n");
		}
		Out.writeBytes(nbUse + "\n");
	}

}
