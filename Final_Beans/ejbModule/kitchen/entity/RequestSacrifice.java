package kitchen.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class RequestSacrifice implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Request request;
	private Sacrifice sacrifice;
	private int plusVotes;
	private int minusVotes;
	private boolean frozen;
	private String whyFrozen;
	private boolean hidden;
	private String creationTime;
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	
	@ManyToOne
	public Sacrifice getSacrifice() {
		return sacrifice;
	}
	public void setSacrifice(Sacrifice sacrifice) {
		this.sacrifice = sacrifice;
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
	

}