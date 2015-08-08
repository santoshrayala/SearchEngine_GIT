/**
 * 
 */
package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author santoshrayala
 *
 */
public class FileTokenizer {
	
	File fileToTokenize;
	List<String> arraylist = new ArrayList<>();
	public List<String> tokenizeTheFile(String s, List<String> l)
	{
		fileToTokenize = new File(s);
		arraylist = l;
		String line;
		String delim=" ,.!?:;-_=(){}[]\'		 \"&<%*+|~>#$@^/\\`";
		List<String> linesInFile = new ArrayList<>();
		try{
			FileReader FR = new FileReader(fileToTokenize);
			BufferedReader BR = new BufferedReader(FR);
			while((line = BR.readLine()) != null)
			{
				linesInFile.add(line);
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
					arraylist.add(tokens.nextToken());
				}
			}
		}
		
		
		
		return arraylist;
		
	}
}
