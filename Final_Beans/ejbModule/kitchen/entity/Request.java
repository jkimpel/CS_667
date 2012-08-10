package kitchen.entity;

/*
 * 	Joe Kimpel
 * 	CS 667 - Final Project
 * 	8.10.2012
 * 
 *	Request.java
 *	This class represents a Request for a new item in a kitchen.
 * 
 */

import static javax.persistence.CascadeType.ALL;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "REQUEST_JK")
public class Request implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;
	private Kitchen kitchen;
	private String name;
	private String description;
	private int plusVotes;
	private int minusVotes;
	private Collection<RequestSacrifice> requestSacrifices;
	private boolean frozen;
	private String whyFrozen;
	private boolean hidden;
	private String creationTime;
	
	public Request(){
		plusVotes = 0;
		minusVotes = 0;
		frozen = false;
		hidden = false;
		creationTime= new java.util.Date().toString();
	}
	
	public Request(Request r){
		this.id = r.id;
		this.kitchen = new Kitchen(r.kitchen);
		this.name = r.name;
		this.description = r.description;
		this.plusVotes = r.plusVotes;
		this.minusVotes = r.minusVotes;
		//Leave out the Collection
		this.frozen = r.frozen;
		this.whyFrozen = r.whyFrozen;
		this.hidden = r.hidden;
		this.creationTime = r.creationTime;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne
	public Kitchen getKitchen() {
		return kitchen;
	}

	public void setKitchen(Kitchen kitchen) {
		this.kitchen = kitchen;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String summary() {
		if (frozen){
			return "Frozen: " + whyFrozen;
		}else{
			return description;
		}
	}

	public int getPlusVotes() {
		return plusVotes;
	}

	public void setPlusVotes(int plusVotes) {
		this.plusVotes = plusVotes;
	}

	public int getMinusVotes() {
		return minusVotes;
	}

	public void setMinusVotes(int minusVotes) {
		this.minusVotes = minusVotes;
	}
	
	public int netVotes() {
		if (frozen)
			return 0;
		else
			return plusVotes - minusVotes;
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

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	@OneToMany(cascade = ALL, mappedBy = "request")
	public Collection<RequestSacrifice> getRequestSacrifices() {
		return requestSacrifices;
	}

	public void setRequestSacrifices(Collection<RequestSacrifice> requestSacrifices) {
		this.requestSacrifices = requestSacrifices;
	}	
	
	public Collection<RequestSacrifice> unhiddenRequestSacrifices() {
		Collection<RequestSacrifice> rss = this.getRequestSacrifices();
		Collection<RequestSacrifice> rssr = new ArrayList<RequestSacrifice>();
		
		for (RequestSacrifice rs : rss){
			if (!rs.isHidden() && !rs.getSacrifice().isHidden()){
				rssr.add(rs);
			}
		}
		
		return rssr;
	}
}

