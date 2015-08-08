import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import library.Corpus;
import library.FileTokenizer;
import library.Porter;
import library.StopWordRemoval;
import library.VocabularyExtractor;



/**
 * @author santoshrayala
 *
 */
public class Tokenize {

	public static void main(String[] args) {
		System.out.println("Starting Tokenization...");
		String corpuspathname = "C:/Users/santoshrayala/Documents/IR/citeseer/citeseer";
		String stopwordspathname = "C:/Users/santoshrayala/Documents/IR/citeseer/stopwords.txt";
		final File Directory = new File(corpuspathname);
		Corpus corpus = new Corpus();
		FileTokenizer FT = new FileTokenizer();
	
		List<String> wordList = new ArrayList<>();
		List<String> allFiles = new ArrayList<>();
		List<String> wordListWithoutStopWords = new ArrayList<>();
		List<String> stemmedWordListWithoutStopWords = new ArrayList<>();
		List<String> stopWordList = new ArrayList<>();
		
		Map<String, Integer> vocabMap = new HashMap<String, Integer>();
		Map<String, Integer> vocabMapPorter = new HashMap<String, Integer>();
		Map<Integer, ArrayList<String>> inverseVocabMap = new HashMap<Integer, ArrayList<String>>();
		Map<Integer, ArrayList<String>> inverseVocabMapPorter = new HashMap<Integer, ArrayList<String>>();
		allFiles = corpus.listFilesInCorpus(Directory);
		System.out.println("Finished collecting all files in the corpus...");
		for(String s : allFiles)
		{
			//System.out.println(s);
			wordList = FT.tokenizeTheFile(s, wordList);
			
		}
		System.out.println("Finished compiling the wordlist...");
		
		stopWordList=FT.tokenizeTheFile(stopwordspathname, stopWordList);
		System.out.println("Finished compiling the stopwordlist...");
		
		StopWordRemoval SWR = new StopWordRemoval();
		//Hey future me! Stop word removal has been changed to accommodate XmlFileTokenizer.java with embedded stop word removal and stemming. 
		//change the below line when trying to figure out whats going wrong. 
		//wordListWithoutStopWords=SWR.removeStopWords(wordList, stopWordList);
		System.out.println("Finished removing Stop words from the word list...");
		
		Porter P = new Porter();
		for(String str : wordListWithoutStopWords)
		{
		stemmedWordListWithoutStopWords.add(P.stripAffixes(str));
		}
		System.out.println("Finished stemming all the words in the word list without stop words...");
		
		VocabularyExtractor VE = new VocabularyExtractor();
		VE.createInverseMap(wordList);
		inverseVocabMap=VE.getInverseMap();
		VE.createInverseMap(stemmedWordListWithoutStopWords);
		inverseVocabMapPorter=VE.getInverseMap();
		VE.extractWithMap(wordList);
		vocabMap= VE.getVocabulary();
		VE.extractWithMap(stemmedWordListWithoutStopWords);
		vocabMapPorter= VE.getVocabulary();
		
		/*for(String s : stopWordList)
		{
			System.out.println(s);
			
		}
		*/
		int wordListSize = wordList.size();
		int vocabSize = vocabMap.size();
		int vocabPorterSize = vocabMapPorter.size();
		int stemmedWordListWithoutStopWordsSize = stemmedWordListWithoutStopWords.size();
		System.out.println("Word list size : "+wordListSize);
		System.out.println("Vocabulary Size : "+vocabSize);
		System.out.println("Stemmed Word list without stop words Size : "+stemmedWordListWithoutStopWordsSize);
		System.out.println("Vocabulary Size of Stemmed Word list without stop words : "+vocabPorterSize);
		
		//System.out.println(wordListWithoutStopWords.size());
		
		//For normal word list...
		
		System.out.println(inverseVocabMap.size());
		System.out.println("The top 20 words are...");
		int count=0;
		int minFreq = (wordListSize*15)/100;
		//Displaying the top 20 words..
		for (Map.Entry<Integer, ArrayList<String>> entry : inverseVocabMap.entrySet()) {
			if(count>19) break;
			
			String value="";
			for(String s : entry.getValue())
			{
				value += s+" ";
			}
			System.out.println(entry.getKey() +"\t\t\t"+ value);
			count++;
		}
		//Displaying the min no of words with tf atleast 15% of the total size of the word collection..
		count=0;
		for (Map.Entry<Integer, ArrayList<String>> entry : inverseVocabMap.entrySet()) {
			if(entry.getKey()>=minFreq)
			{
				String value="";
				for(String s : entry.getValue())
				{
					value += s+" ";
					count++;
				}
				System.out.println(entry.getKey() +"\t\t\t"+ value);
				
			}
		}
		System.out.println("minimum number of unique words accounting for 15% of total words : "+count);
		
		//For Porter Stemmer List...
		
		System.out.println(inverseVocabMapPorter.size());
		System.out.println("The top 20 words are...");
		count=0;
		minFreq = (stemmedWordListWithoutStopWordsSize*15)/100;
		//Displaying the top 20 words..
		for (Map.Entry<Integer, ArrayList<String>> entry : inverseVocabMapPorter.entrySet()) {
			if(count>19) break;
			
			String value="";
			for(String s : entry.getValue())
			{
				value += s+" ";
			}
			System.out.println(entry.getKey() +"\t\t\t"+ value);
			count++;
		}
		//Displaying the min no of words with tf atleast 15% of the total size of the word collection..
		count=0;
		for (Map.Entry<Integer, ArrayList<String>> entry : inverseVocabMapPorter.entrySet()) {
			if(entry.getKey()>=minFreq)
			{
				String value="";
				for(String s : entry.getValue())
				{
					value += s+" ";
					count++;
				}
				System.out.println(entry.getKey() +"\t\t\t"+ value);
				
			}
		}
		System.out.println("minimum number of unique words accounting for 15% of total words Stemmed and stop words removed : "+count);
		
		/*try{
		PrintWriter writer = new PrintWriter("C:/Users/santoshrayala/Documents/IR/output.txt", "UTF-8");
		
		for (Map.Entry<Integer, ArrayList<String>> entry : inverseVocabMap.entrySet()) {
			String value="";
			for(String s : entry.getValue())
			{
				value += s+" ";
			}
			writer.println(entry.getKey() +"\t\t\t"+ value);
		}
		writer.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}*/
		/*for (Map.Entry<Integer, ArrayList<String>> entry : inverseVocabMap.entrySet()) {
			String value="";
			for(String s : entry.getValue())
			{
				value += s+" ";
			}
			System.out.println(entry.getKey() +"\t\t\t"+ value);
		}*/
		
		
		
		
		
	}

}
