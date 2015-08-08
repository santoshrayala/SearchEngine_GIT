package library;
import java.util.List;

/**
 * 
 */

/**
 * @author santoshrayala
 *
 */
public class StopWordRemoval {
	
	

	public Boolean notStopWord(String token, List<String> swl)
	{
		
		if(swl.contains(token))
			{
				
				return false;
				//System.out.println("removed : "+s);
			}
		
		
		return true;
		
	}
	
}
