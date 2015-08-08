import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import library.Corpus;
import library.FileTokenizer;
import library.Posting;
import library.Retrieval;
import library.XmlFileTokenizer;


/**
 * @author santoshrayala
 *
 */
public class VectorSpaceRetrieval {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Starting compiling the wordlist...");
		String corpuspathname = "C:/Users/santoshrayala/Documents/2015 Spring/IR/HW2/cranfield/cranfieldDocs";
		String stopwordspathname = "C:/Users/santoshrayala/Documents/2015 Spring/IR/HW1/citeseer/stopwords.txt";
		
		final File Directory = new File(corpuspathname);
		Corpus corpus = new Corpus();
		FileTokenizer FT = new FileTokenizer();
		XmlFileTokenizer XFT = new XmlFileTokenizer();
	
		List<String> wordList = new ArrayList<>();
		//needed for retrieval
		List<String> allFiles = new ArrayList<>();
		Map<String, HashMap<String, Double>> II;
		//needed for retrieval
		Map<String, Double> idf = new HashMap<String, Double>();
		//Map<String, Double> tfidf = new HashMap<String, Double>();
		// needed for retrieval
		Map<String, HashMap<String, Posting>> termweightsmain;
		List<String> stopWordList = new ArrayList<>();
		
		allFiles = corpus.listFilesInCorpus(Directory);
		int N = allFiles.size();
		System.out.println("Finished collecting all files in the corpus...");
		
		stopWordList=FT.tokenizeTheFile(stopwordspathname, stopWordList);
		System.out.println("Finished compiling the stopwordlist...");

		System.out.println("compiling the wordlist...");
		System.out.println("removing Stop words from the word list...");
		System.out.println("stemming all the words in the word list without stop words...");
		
		for(String s : allFiles)
		{
			//System.out.println(s);
			wordList = XFT.tokenizeTheXMLFile(s, wordList, stopWordList);
			
		}
		
		II = new HashMap<String, HashMap<String, Double>>(XFT.getII());
	
		termweightsmain= new HashMap<String, HashMap<String, Posting>>(XFT.getTermweights());
		for(Map.Entry<String, HashMap<String, Double>> entry : II.entrySet())
		{
			
			idf.put(entry.getKey(), ((Math.log(N/entry.getValue().size()))/Math.log(2)));
			//System.out.println("-"+entry.getKey()+"-"+"       "+idf.get(entry.getKey()));
		}
		
		System.out.println("Finished compiling the word tf's and idf's...");
		System.out.println("Calculating the word weights..");
		for(Map.Entry<String, HashMap<String,Posting>> entry : termweightsmain.entrySet())
		{
			for(Map.Entry<String, Posting> entry1 : entry.getValue().entrySet())
			{
				entry1.getValue().setWeight(idf.get(entry1.getKey()));
			}
		}
		
		
		
		Retrieval R = new Retrieval();
		
		try {
		
		
		Map<Double, ArrayList<String>> inverseMapDouble;
		
		
		Scanner a = new Scanner(System.in);
		System.out.println("Enter a query: ");
		String query = a.nextLine();
		System.out.println("How many results do you want displayed: ");
		int noofre=a.nextInt();
		System.out.println("Results for the query are:");
		inverseMapDouble=R.retrieve(query,N,II,idf,termweightsmain, stopWordList);
		
		for(Map.Entry<Double, ArrayList<String>> entry : inverseMapDouble.entrySet())
		{
			if(noofre==0)break;
			System.out.println(entry.getValue());
			noofre--;
		}
		String more;
		while(true)
		{
			System.out.println("Do you want more results, enter 'y' for YES and 'n' for NO: ");
			more=a.nextLine();
			if(more.equals('y'))
			{
				System.out.println("How many results do you want to display: ");
				noofre=a.nextInt();
				System.out.println("Results for the query are:");
				for(Map.Entry<Double, ArrayList<String>> entry : inverseMapDouble.entrySet())
				{
					if(noofre==0)break;
					System.out.println(entry.getValue());
					noofre--;
				}
				
			}
			else
				break;
		}
		
		
	
	}
		finally
		{
			System.out.println("System terminated, Run the program again to search.");
		}

}
}
