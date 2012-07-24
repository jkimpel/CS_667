package kimpel;

/*
 * CustomerInfo.java
 * Joe Kimpel
 * CS 667
 * 7.24.12
 * 
 * This bean contains information about a new customer
 * and handles validation of user input, and navigates to info page
 */

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean
@SessionScoped
public class CustomerInfo{
	
	private static Map<String, CustomerInfo> idMap = new HashMap<String, CustomerInfo>();
	
	private String customerId;
	private String lastName;
	private String firstName;
	private String emailAddress;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String navigate(){
		if (validate())
			return "info?faces-redirect=true";
		else
			return "register?faces-redirect=true";
	}
	
	//Most of the validation is handled by the xhtml, however, we have to wait for the navigation
	//	to add the new customer to the idMap, which requires another check, just in case another 
	//	thread added the same customerID between field validation and here.
	public boolean validate(){
		synchronized(idMap){
			if (!idMap.containsKey(customerId)){
				idMap.put(customerId, this);
				return true;
			}else{
				return false;
			}
			
		}
	}
	
	  public void validateId(FacesContext context, UIComponent componentToValidate,Object value)
            		  throws ValidatorException {
		  String id = (String)value;
		  	if (idMap.containsKey(id)) {
		  		FacesMessage message =
		  				new FacesMessage("Customer ID: " + id + " has already been used.");
		  		throw new ValidatorException(message);
		  	}
}
	
}