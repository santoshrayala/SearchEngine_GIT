/**
 * 
 */
package library;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author santoshrayala
 *
 */
public class Corpus {
	
	List<String> newList = new ArrayList<>();
	public List<String> listFilesInCorpus(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesInCorpus(fileEntry);
	        } else {
	            newList.add(fileEntry.getPath());
	        }
	    }
	    
		return newList;
	}

}
