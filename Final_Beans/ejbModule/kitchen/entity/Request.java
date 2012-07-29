package kitchen.entity;

import static javax.persistence.CascadeType.ALL;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Request implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
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
	
	public Request(){}
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	
	
	
}