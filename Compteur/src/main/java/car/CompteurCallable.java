package car;

import java.util.Map;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 
 * @author charles
 *
 */
public class CompteurCallable implements Callable<Map<String,Integer>> {
	private Compteur Compteur;
	private List<String> Lines;
	public final int Index;
	
	/**
	 * Constructeur
	 * @param index numéro du thread
	 * @param lines les lignes à traiter
	 * @param nbLinesToRead nombre de lignes à traiter
	 * @param startIndex où commencer à lire dans la liste
	 */
	public CompteurCallable(int index, List<String> lines, int nbLinesToRead, int startIndex) {
		Compteur = new Compteur();
		Lines = lines.subList(startIndex, startIndex + nbLinesToRead);
		Index = index;
	}

	/**
	 * Utilise
	 */
	@Override
	public Map<String,Integer> call() throws Exception {
		Compteur.CountWordsInLines(Lines);
		Compteur.AnalyseMostUsedWords();
		return Compteur.GetWordsUse();
	}

}
