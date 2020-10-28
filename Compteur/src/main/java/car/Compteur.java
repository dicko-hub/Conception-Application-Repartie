package car;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Compteur {
	protected HashMap<String, Integer> WordsUse;
	protected Set<String> MostUsedWords;
	protected int NbUse;
	
	protected final Set<String> CharactersToRemove = new HashSet<String>() {
		{
			add("[");add(",");add("?");	add(";");add(".");add(":");	add("!");
			add("%");add("\"");add("-)");add("_");add("]");add("(");add(")");
		}
	};
	
	
	/**
	 * Constructeur
	 */
	public Compteur(){
		WordsUse = new HashMap<String,Integer>();
		MostUsedWords = new HashSet<String>();
		NbUse = 0;
	}
	
	/**
	 * Retourne la liste des lignes du fichier donné
	 * @param f le fichier
	 * @return la liste des lignes
	 */
	public List<String> ReadIntoArrayList(File f){
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader (f));
			String line;
			while ((line = in.readLine())!= null) list.add(line);
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable");
			list.clear();
		} catch (IOException e) {
			System.out.println("Impossible de lire une ligne du fichier");
			list.clear();
		}
		return list;
	}
	
	/**
	 * Compte le nombre de fois qu'un mot est utilisé dans une liste de ligne
	 * @param list la liste
	 */
	public void CountWordsInLines(List<String> list) {
		for (String line : list) CountWordsInLine(line);
	}
	
	/**
	 * Compte le nombre de fois qu'un mot est utilisé dans une ligne
	 * @param line la ligne
	 */
	public void CountWordsInLine(String line) {
		for (String c : CharactersToRemove) {
			line = line.replace(c, "");
		}
		String[] words = line.split(" ");
		for (String word : words) {
			if (!word.equals("")) {
				word = word.toLowerCase();
				Integer count = WordsUse.get(word);
				if (count == null) {
					WordsUse.put(word, 1);
				} 
				else {
					WordsUse.put(word, count.intValue()+1);
				}
			}
		}
	}
	
	/**
	 * Cherche les mots les plus utilisés dans le fichier donné
	 * @param file le fichier
	 */
	public void Analyse(File file) {
		Analyse(this.ReadIntoArrayList(file));
	}
	
	public void Analyse(List<String> lines) {
		LocalTime start = LocalTime.now();
		CountWordsInLines(lines);
		LocalTime end = LocalTime.now();
		System.out.println("Temps de traitement : " + start.until(end, java.time.temporal.ChronoUnit.SECONDS));
		AnalyseMostUsedWords();
	}
	
	/**
	 * Cherche les mots les plus utilisés A
	 */
	public void AnalyseMostUsedWords(){
		for(Map.Entry<String, Integer> entry : WordsUse.entrySet()) {
		    String word = entry.getKey();
		    Integer nbUse = entry.getValue();
		    if (nbUse > NbUse) {
		    	NbUse = nbUse;
		    	MostUsedWords.clear();
		    	MostUsedWords.add(word);
		    }
		    else if (nbUse == NbUse) MostUsedWords.add(word);
		}
	}
	
	public Set<String> GetMostUsedWords(){
		return MostUsedWords;
	}
	
	public int GetNbUse() {
		return NbUse;
	}
	
	public Map<String,Integer> GetWordsUse(){
		return WordsUse;
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Nom de fichier requis.");
			System.exit(0);
		}
		File file = new File(args[0]);
		Compteur compteur = new Compteur();
		compteur.Analyse(file);
		compteur.PrintResult();
	}
	/**
	 * Ecrit la liste des mots le plus utilisés après analyse.
	 */
	public void PrintResult() {
		System.out.println("Liste des mots les plus utilisés");
		for (String word : GetMostUsedWords()) {
			System.out.println(word);
		}
		System.out.println("Ils ont été utilisés " + NbUse + " fois.");
	}

}
