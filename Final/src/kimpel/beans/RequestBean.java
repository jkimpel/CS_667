package kimpel.beans;

import kimpel.HTMLUtilities;

/*	This class contains all the information for a client's request
 * 
 *		Note: this can be a request for an items addition
 *		or a suggested removal
 * 
 */
public class RequestBean{
	
	//identifier in the database
	private int id;
	
	private String name;
	private String description;
	
	//plus and minus votes
	private int plusses = 0;
	private int minusses = 0;
	
	//True if this is a request to be added, false for a removal
	private boolean addition = true;
	
	//the name of the request the client would like to replace this with
	private String replacedBy = "";
	
	//is this item frozen by the admin? If so, why?
	private boolean frozen = false;
	private String whyFrozen;
	
	public RequestBean(){
		
	}
	
	public RequestBean(String n){
		setName(n);
	}
	
	public RequestBean(String n, String r){
		setName(n);
		setReplacedBy(r);
		setAddition(false);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = HTMLUtilities.filter(name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = HTMLUtilities.filter(description);
	}

	public int getPlusses() {
		return plusses;
	}

	public void setPlusses(int plusses) {
		this.plusses = plusses;
	}

	public int getMinusses() {
		return minusses;
	}

	public void setMinusses(int minusses) {
		this.minusses = minusses;
	}

	public boolean isAddition() {
		return addition;
	}

	public void setAddition(boolean addition) {
		this.addition = addition;
	}

	public String getReplacedBy() {
		return replacedBy;
	}

	public void setReplacedBy(String replacedBy) {
		this.replacedBy = replacedBy;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public String getWhyFrozen() {
		return whyFrozen;
	}

	public void setWhyFrozen(String whyFrozen) {
		this.whyFrozen = whyFrozen;
	}	
	
	public boolean isValid(){
		if ((name != null) && (!name.isEmpty()))
			return true;
		else
			return false;
	}
	
	public boolean isValidRemoval(){
		if (isValid() && !addition && (replacedBy != null) && (!replacedBy.isEmpty()))
			return true;
		else
			return false;
	}
}