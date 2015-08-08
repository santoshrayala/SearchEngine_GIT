/**
 * 
 */
package library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author santoshrayala
 *
 */
public class Retrieval{
	
	
	
	
	public Map<Double, ArrayList<String>> retrieve(String query, int N, Map<String, HashMap<String, Double>> II,
			Map<String, Double> idf, Map<String, HashMap<String, Posting>> termweightsmain, List<String> swl)
	{
		Map<String, Double> wordsinquery=new HashMap<String, Double>();
		Map<String, Double> relevantdocs=new HashMap<String,Double>();
		double mostfreqword=1.0;
		String delim=" ,.!?:;-_=(){}[]\' \"&<%*+|~>#$@^/\\`0123456789";
		StopWordRemoval SWR = new StopWordRemoval();
		Porter P = new Porter();
		StringTokenizer tokens = new StringTokenizer(query, delim, false);
		while(tokens.hasMoreTokens())
		{
			String str= tokens.nextToken();
			if(SWR.notStopWord(str, swl))
				{
					String str1 = P.stripAffixes(str);
					if(wordsinquery.get(str1)==null)
					{
						wordsinquery.put(str1, 1.0);
					}
					else{
						double tf=wordsinquery.get(str1)+1;
						wordsinquery.put(str1,tf );
						if(tf>mostfreqword) mostfreqword=tf;
						
					}
					
				}
		}
		termweightsmain.put("query", new HashMap<String, Posting>());
		
		
		//iterate on wordsinquery and fill the termweightsmain
		for(Map.Entry<String, Double> entry : wordsinquery.entrySet())
		{
			if(termweightsmain.get("query").get(entry.getKey())==null)
			{
				Posting po=new Posting();
				//calculating the tfij and setting the value to a Posting object
				po.setTfij(entry.getValue()/mostfreqword);
				
				if(idf.get(entry.getKey())!=null)
				po.setWeight(idf.get(entry.getKey()));
				else{
					po.setWeight(0.0);
				System.out.println(entry.getKey());
				}
				termweightsmain.get("query").put(entry.getKey(), po);
			}
		}
		
		
		//collect all the docs which have atleast one word from the query
		for(Map.Entry<String, Double> entry : wordsinquery.entrySet())
		{
			//System.out.println();
			if(II.get(entry.getKey())!=null)
			{
			for(Map.Entry<String, Double> entry1 : II.get(entry.getKey()).entrySet())
			{
				if(relevantdocs.get(entry1.getKey())==null)
				{
					relevantdocs.put(entry1.getKey(), 0.0);
				}
			}
			}
		}
		
		//start calculating the cosine similarity for those docs and insert into the relevantdocs
		for(Map.Entry<String, Double> entry : relevantdocs.entrySet())
		{
			//String doc_name=entry.getKey();
			Double numer=0.0;
			Double denom1=0.0;
			Double denom2=0.0;
			for(Map.Entry<String, Posting> entry1 : termweightsmain.get("query").entrySet())
			{
				if(termweightsmain.get(entry.getKey()).get(entry1.getKey())!=null)
				{
					numer+=(entry1.getValue().getWeight())*
							(termweightsmain.get(entry.getKey()).get(entry1.getKey()).getWeight() );
					denom1 += Math.pow(entry1.getValue().getWeight(), 2 );
					denom2 += Math.pow(termweightsmain.get(entry.getKey()).get(entry1.getKey()).getWeight(), 2 );
					
				}
			}
			Double cosine = numer/Math.sqrt(denom1*denom2);
			entry.setValue(cosine);
		}
		
		
		VocabularyExtractor VE = new VocabularyExtractor();
		VE.createInverseMap(relevantdocs);
		Map<Double, ArrayList<String>> inverseMapDouble =VE.getInverseMapDouble();
		int count=0;
		for(Map.Entry<Double, ArrayList<String>> entry : inverseMapDouble.entrySet())
		{
			if(count==10) break;
			System.out.println(entry.getKey());
			count++;
		}
		//try{
			//BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(outputpath)));
			
		//StringBuffer sbf = new StringBuffer();
		 //sbf.append("StringBuffer contents first line.");
		  
          
		/*Fw.append("----------------Query"+(count++)+"-----------------");
		for(Map.Entry<Double, ArrayList<String>> entry : inverseMapDouble.entrySet())
		{
			
			String st=null;
			for(String holder: entry.getValue())
				st= st+" "+holder;
			Fw.append(st);
			Fw.append(System.lineSeparator());
			
		}
		
		Fw.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		//sort the arraylist and present the results
	*/
		
	return inverseMapDouble;
	
	//termweightsmain.remove("query");
	}
	

}
