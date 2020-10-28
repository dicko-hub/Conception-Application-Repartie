package car;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;

public class CompteurMultiThreadTest extends CompteurTest {
	private final int NBTHREAD = 2;
	private CompteurMultiThread CompteurMT;
	
	@Override
	@Before
	public void Before() {
		super.Before();
		CompteurMT = (CompteurMultiThread) Compteur;
	}
	
	@Override
	protected Compteur CompteurFactory() {
		return new CompteurMultiThread(NBTHREAD);
	}
	
	@Test
	public void MergeTestOkEmpty() {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("la", 3);
		map.put("lol", 2);
		
		CompteurMT.MergeWordsUse(map);
		
		Assert.assertEquals(map, CompteurMT.GetWordsUse());
	}
	
	@Test
	public void MergeTestOkAlreadyFilled() {
		Map<String,Integer> res = new HashMap<String,Integer>();
		res.put("la", 4);
		res.put("lol", 2);
		res.put("un", 4);
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("la", 3);
		map.put("lol", 2);
		
		CompteurMT.MergeWordsUse(map);
		
		Map<String,Integer> map2 = new HashMap<String,Integer>();
		map2.put("la", 1);
		map2.put("un", 4);
		
		CompteurMT.MergeWordsUse(map2);
		
		Assert.assertEquals(res, CompteurMT.GetWordsUse());
	}

}
