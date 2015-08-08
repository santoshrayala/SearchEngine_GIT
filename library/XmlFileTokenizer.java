package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author santoshrayala
 *
 */
//add the xml tags from which you want to extract the text in the if condition inside the while 
//in the buffered reader and similarly the end tags in the next if  
public class XmlFileTokenizer {
	File fileToTokenize;
	
	Map<String, HashMap<String, Posting>> termweights = new HashMap<String, HashMap<String, Posting>>();
	public Map<String, HashMap<String, Posting>> getTermweights() {
		return termweights;
	}

	//Map<String, Posting> termWeights = new HashMap<String, Posting>();
	Map<String, HashMap<String, Double>> II = new HashMap<String, HashMap<String, Double>>();
//	Posting p = new Posting();
//	String s = p.docname;
	
	public Map<String, HashMap<String, Double>> getII() {
		return II;
	}

	public List<String> tokenizeTheXMLFile(String s, List<String> arraylist, List<String> swl)
	{

		StopWordRemoval SWR = new StopWordRemoval();
		Porter P = new Porter();
		fileToTokenize = new File(s);
		String docname=fileToTokenize.getName();
		Double mostfreqword =1.0;	//assuming there is at least one word in the document
		
		int flag=0;
		String line;
		String delim=" ,.!?:;-_=(){}[]\' \"&<%*+|~>#$@^/\\`0123456789";
		List<String> linesInFile = new ArrayList<>();
		try{
			FileReader FR = new FileReader(fileToTokenize);
			BufferedReader BR = new BufferedReader(FR);
			while((line = BR.readLine()) != null)
			{
				
				if(line.equals("<TITLE>") || line.equals("<TEXT>")){
					line=BR.readLine();
					flag=1;
					
				}
				if(line.equals("</TITLE>") || line.equals("</TEXT>")) flag=0;
				if(flag==1){
					linesInFile.add(line);
					
				}
			}
			BR.close();
			
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}
		
		for(String st : linesInFile)
		{
			if(st!=null)
			{
				StringTokenizer tokens = new StringTokenizer(st, delim, false);
				while(tokens.hasMoreTokens())
				{
					String str= tokens.nextToken();
					if(SWR.notStopWord(str, swl))
						{
							String str1 = P.stripAffixes(str);
							arraylist.add(str1);
							if(II.get(str1)==null)
							{
									II.put(str1, new HashMap<String, Double>());
									II.get(str1).put(docname, 1.0);
							}
							else{
									if(II.get(str1).get(docname)==null)
									{
										II.get(str1).put(docname, 1.0);
									}
									else{
										Double tf=II.get(str1).get(docname);
										tf+=1;
										II.get(str1).put(docname, tf);
										if(tf>mostfreqword) mostfreqword=tf;
									}
							}
						
						}
				}
				
				
			}
		}
		//finished traversing all words in the document
		if(II.get("mostfreq_word")==null)
		{
			II.put("mostfreq_word", new HashMap<String, Double>());
		}
		else{
			II.get("mostfreq_word").put(docname, mostfreqword);
		}
		//iterating the II again and filling the termweights hashmap (2nd pass)
		termweights.put(docname, new HashMap<String, Posting>());
		for(Map.Entry<String, HashMap<String, Double>> entry : II.entrySet())
		{
			String word = entry.getKey();
			if(II.get(word).get(docname)!=null)
			{
				Double tfij=II.get(word).get(docname)/mostfreqword;
				Posting po= new Posting();
				po.tfij=tfij;
				termweights.get(docname).put(word, po);
			}
		}
		
		return arraylist;
		
	}

}
