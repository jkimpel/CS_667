package kimpel.beans;

import kimpel.HTMLUtilities;

/* Simple bean that represents the information needed
 * to register a new customer.
 * 
 * Joe Kimpel CS667
 * 
 * 6.24.2012
 * 
 */

public class CustomerInfo{
	private static final String DEFAULT_ID = "";
	private static final String DEFAULT_LAST_NAME = "";
	private static final String DEFAULT_FIRST_NAME = "";
	private static final String DEFAULT_EMAIL = "";
	
	private String customerID = DEFAULT_ID;
	private String lastName = DEFAULT_LAST_NAME;
	private String firstName = DEFAULT_FIRST_NAME;
	private String emailAddress = DEFAULT_EMAIL;

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = HTMLUtilities.filter(customerID);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = HTMLUtilities.filter(lastName);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = HTMLUtilities.filter(firstName);
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = HTMLUtilities.filter(emailAddress);
	}
	
	//If all of the fields are non-default, the object has been completely filled
	public boolean isComplete(){
		if (!getCustomerID().equals(DEFAULT_ID) && !getLastName().equals(DEFAULT_LAST_NAME)
				&& !getFirstName().equals(DEFAULT_FIRST_NAME) && !getEmailAddress().equals(DEFAULT_EMAIL))		
			return true;
		else
			return false;
	}	
}