package kitchen.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class RequestSacrifice implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;
	private Request request;
	private Sacrifice sacrifice;
	private int plusVotes;
	private int minusVotes;
	private boolean frozen;
	private String whyFrozen;
	private boolean hidden;
	private String creationTime;
	
	public RequestSacrifice(){
		plusVotes = 0;
		minusVotes = 0;
		frozen = false;
		hidden = false;
		creationTime = new java.util.Date().toString();
	}
	
	public RequestSacrifice(RequestSacrifice rs){
		this.id = rs.id;
		this.request = new Request(rs.getRequest());
		this.sacrifice = new Sacrifice(rs.getSacrifice());
		this.plusVotes = rs.plusVotes;
		this.minusVotes = rs.minusVotes;
		this.frozen = rs.frozen;
		this.whyFrozen = rs.whyFrozen;
		this.hidden = rs.hidden;
		this.creationTime = rs.creationTime;
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
	public int netVotes(){
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
	

}