package car;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Assert;

/**
 * Unit test for simple App.
 */
public class CompteurTest 
{
	protected Compteur Compteur;
	private static final String FILENAME = "Test";
	private static final String EXTENSION = "txt";
	private static File FILE;
	
	@SuppressWarnings("serial")
	private static final List<String> ListTest = new LinkedList<String>() {
		{
			add("La Cigale, ayant chanté");
			add("Tout l'été,");
            add("Se trouva fort dépourvue");
            add("Quand la bise fut venue.");
            add("    ");
		}
	};
	
	@BeforeClass
	public static void BeforeAll() throws IOException {
		FILE = File.createTempFile(FILENAME, EXTENSION);
		FileWriter Writer = new FileWriter(FILE);
		for (String line : ListTest) {
			Writer.write(line + "\n");
		}
		Writer.close();
	}
	
	@Before
	public void Before() {
		Compteur = CompteurFactory();
	}
	
	protected Compteur CompteurFactory() {
		return new Compteur();
	}
	
	@AfterClass
	public static void AfterClass() {
		FILE.deleteOnExit();
	}

    @Test
    public void ReadIntoArrayListTest()
    {
        List<String> list = Compteur.ReadIntoArrayList(FILE);
        Assert.assertEquals(ListTest, list);
    }
    
    @Test
    public void ReadIntoArrayListTestFileDoNotExist()
    {
        List<String> list = Compteur.ReadIntoArrayList(new File("donotexist.txt"));
        Assert.assertTrue(list.isEmpty());
    }
    
    @Test
    public void ReadIntoArrayListTestImpossibleToRead()
    {
    	FILE.setReadable(false);
        List<String> list = Compteur.ReadIntoArrayList(FILE);
        Assert.assertTrue(list.isEmpty());
    }
    
    @Test
    public void CountWordsInLineTestFirstTime()
    {
        Compteur.CountWordsInLine(ListTest.get(0));
        
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("la", 1);
        map.put("cigale", 1);
        map.put("ayant", 1);
        map.put("chanté", 1);
        
        Assert.assertEquals(map, Compteur.GetWordsUse());
    }
    
    @Test
    public void CountWordsInLineTestEmptyLine() {
    	Compteur.CountWordsInLine("");
    	Assert.assertTrue(Compteur.GetWordsUse().isEmpty());
    }
    
    @Test
    public void CountWordsInLinesTest()
    {
        Compteur.CountWordsInLines(ListTest);
        
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("la", 2);
        map.put("cigale", 1);
        map.put("ayant", 1);
        map.put("chanté", 1);
        map.put("tout", 1);
        map.put("l'été", 1);
        map.put("se", 1);
        map.put("trouva", 1);
        map.put("fort", 1);
        map.put("dépourvue", 1);
        map.put("quand", 1);
        map.put("bise", 1);
        map.put("fut", 1);
        map.put("venue", 1);
        
        Assert.assertEquals(map, Compteur.GetWordsUse());
    }
    
    @Test
    public void AnalyseMostUsedWordsTest()
    {
        Map<String,Integer> map = Compteur.GetWordsUse();
        map.put("la", 2);
        map.put("le", 2);
        map.put("x", 1);
        
        Compteur.AnalyseMostUsedWords();
        Assert.assertEquals(2, Compteur.GetNbUse());
        Set<String> expectedWords = new HashSet<String>();
        expectedWords.add("le");
        expectedWords.add("la");
        Assert.assertEquals(expectedWords, Compteur.GetMostUsedWords());
    }
    
    @Test
    public void AnalyseTest()
    {
    	Compteur.Analyse(FILE);
    	Assert.assertEquals(2 , Compteur.GetNbUse());
    	
    	Set<String> expected = new HashSet<String>();
    	expected.add("la");
    	
    	Assert.assertEquals(expected, Compteur.GetMostUsedWords());
    }
    
    @Test
    public void AnalyseTestFileDoesntExist()
    {
    	Compteur.Analyse(new File("donotexist.txt"));
    	Assert.assertEquals(0, Compteur.GetNbUse());
    	
    	Assert.assertTrue(Compteur.GetMostUsedWords().isEmpty());
    }
}
