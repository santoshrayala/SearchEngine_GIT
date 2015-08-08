/**
 * 
 */
package library;

/**
 * @author santoshrayala
 *
 */
public class Posting {
	
	//public String docname;
	public Double tfij;
	public Double weight;
	

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double idf) {
		this.weight = idf*tfij;
	}

	public Double getTfij() {
		return tfij;
	}

	public void setTfij(Double tfij) {
		this.tfij = tfij;
	}

}
