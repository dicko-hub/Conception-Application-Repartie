package car;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.io.File;

public class CompteurMultiThread extends Compteur{
	private int NbThreads;
	private ExecutorService ThreadPool;
	
	/**
	 * Constructeur
	 * @param nbThreads nombre de threads
	 */
	public CompteurMultiThread(int nbThreads) {
		super();
		NbThreads = nbThreads;
		ThreadPool = Executors.newFixedThreadPool(nbThreads);
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Il manque un argument, (nombre de threads, nom du fichier)");
			System.exit(0);
		}
		int nbThreads = 0;
		try {
			nbThreads = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.println("Nombre de thread incompréhensible");
			System.exit(0);
		}
		CompteurMultiThread compteur = new CompteurMultiThread(nbThreads);
		compteur.Analyse(new File(args[0]));
		compteur.PrintResult();
		System.exit(0);
	}
	
	@Override
	public void CountWordsInLines(List<String> lines) {
		List<Future<Map<String,Integer>>> listResult = new ArrayList<Future<Map<String,Integer>>>();
		int nbLinesPerThread = lines.size() / NbThreads;
		int nbThreadWithOneMoreLine = lines.size() - nbLinesPerThread * NbThreads;		
		int index = 0;
		for (int i = 0; i<NbThreads; i++) {
			int nbLinesToRead = i<nbThreadWithOneMoreLine ? nbLinesPerThread + 1 : nbLinesPerThread;
			Callable<Map<String, Integer>> c = new CompteurCallable(i, lines, nbLinesToRead, index);
			index += nbLinesToRead;
			listResult.add(ThreadPool.submit(c));
			
		} 
		for (Future<Map<String, Integer>> f : listResult) {
			try {
				MergeWordsUse(f.get());
			} catch (InterruptedException e) {
				System.out.println("ERROR : Thread intérrompu");
			} catch (ExecutionException e) {
				System.out.println("ERROR : éxécution d'un thread");
			}
		}
	}

	/**
	 * Ajoute au compteur de mot actuel,
	 * @param map
	 */
	public void MergeWordsUse(Map<String, Integer> map) {
		for(Map.Entry<String, Integer> entry : map.entrySet()) {
		    String word = entry.getKey();
		    Integer nbUse = entry.getValue();
		    Integer storedNbUse = WordsUse.get(word);
		    
		    if (storedNbUse == null) WordsUse.put(word, nbUse);
		    else WordsUse.put(word, nbUse + storedNbUse);
		}
	}
}
