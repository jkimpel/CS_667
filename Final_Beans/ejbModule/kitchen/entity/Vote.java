package kitchen.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Vote implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;
	private Request request;
	private RequestSacrifice requestSacrifice;
	private int value;	//Should be -1 or 1
	private String time;
	
	public Vote(){
		value = 0;
		time = new java.util.Date().toString();
	}
	
	public Vote(Vote v){
		this.id = v.id;
		if (v.request != null){
			this.request = new Request(v.request);
		}
		if (v.requestSacrifice != null){
			this.requestSacrifice = new RequestSacrifice(v.requestSacrifice);
		}
		this.value = v.value;
		this.time = v.time;
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
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	@ManyToOne
	public RequestSacrifice getRequestSacrifice() {
		return requestSacrifice;
	}
	public void setRequestSacrifice(RequestSacrifice requestSacrifice) {
		this.requestSacrifice = requestSacrifice;
	}
	
	public String stringValue() {
		String s;
		if (request != null){
			s = request.getKitchen().getName() + " - " + request.getName() + " ";
			if (value > 0){
				s += "+1";
			}else{
				s += "-1";
			}
			return s;
		}else{
			s = requestSacrifice.getRequest().getKitchen().getName() +
					" - " + requestSacrifice.getRequest().getName() + "/" + 
					requestSacrifice.getSacrifice().getName() + " ";
			if (value > 0){
				s += "+1";
			}else{
				s += "-1";
			}
			return s;
		}	
	}
	
}