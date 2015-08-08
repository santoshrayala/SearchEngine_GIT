package library;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 
 */

/**
 * @author santoshrayala
 *
 */
public class VocabularyExtractor {
	
	
	List<String> wordList = new ArrayList<>();
	public Map<Double, ArrayList<String>> getInverseMapDouble() {
		return inverseMapDouble;
	}
	public void setInverseMapDouble(Map<Double, ArrayList<String>> inverseMapDouble) {
		this.inverseMapDouble = inverseMapDouble;
	}
	Map<String, Integer> vocabulary = new HashMap<>();
	Map<Integer, ArrayList<String>> inverseMap;
	Map<Double, ArrayList<String>> inverseMapDouble;
	Set<String> uniqueSet;
	public List<String> getWordList() {
		return wordList;
	}
	public void setWordList(List<String> wordList) {
		this.wordList = wordList;
	}
	public Map<String, Integer> getVocabulary() {
		return vocabulary;
	}
	public void setVocabulary(Map<String, Integer> vocabulary) {
		this.vocabulary = vocabulary;
	}
	public Map<Integer, ArrayList<String>> getInverseMap() {
		return inverseMap;
	}
	public void setInverseMap(Map<Integer, ArrayList<String>> inverseMap) {
		this.inverseMap = inverseMap;
	}
	public Set<String> getUniqueSet() {
		return uniqueSet;
	}
	public void setUniqueSet(Set<String> uniqueSet) {
		this.uniqueSet = uniqueSet;
	}
	
	public void extractWithMap(List<String> list)
	{
		wordList = list;
		
		for(String s : wordList)
		{
			Integer count = vocabulary.get(s);
			vocabulary.put(s, (count == null)?1:count+1);
		}
	}
	
	public void createInverseMap(List<String> list)
	{
		
		wordList = list;
		Set<String> uniqueSet = new HashSet<String>(wordList);
		Map<Integer, ArrayList<String>> imap = new HashMap<Integer, ArrayList<String>>();
		for(String value : uniqueSet)
		{
			Integer freq = (Integer)Collections.frequency(wordList, value);
			if(imap.get(freq)==null)
				{
				imap.put(freq, new ArrayList<String>());
				imap.get(freq).add(value);
				}
			else{
				imap.get(freq).add(value);
			}
		}
		inverseMap = new TreeMap<Integer, ArrayList<String>>(Collections.reverseOrder());
		inverseMap.putAll(imap);
		
		
		
		/*for(String s : wordList)
		{
			Integer count = vocabulary.get(s);
			vocabulary.put(s, (count == null)?1:count+1);
		}*/
	}
	public void createInverseMap(Map<String, Double> map)
	{
		
		Map<Double, ArrayList<String>> imap = new HashMap<Double, ArrayList<String>>();
		for(Map.Entry<String, Double> entry : map.entrySet())
		{
			Double freq = entry.getValue();
			if(imap.get(freq)==null)
				{
				imap.put(freq, new ArrayList<String>());
				imap.get(freq).add(entry.getKey());
				}
			else{
				imap.get(freq).add(entry.getKey());
			}
		}
		inverseMapDouble = new TreeMap<Double, ArrayList<String>>(Collections.reverseOrder());
		inverseMapDouble.putAll(imap);
		
	}
	

}
