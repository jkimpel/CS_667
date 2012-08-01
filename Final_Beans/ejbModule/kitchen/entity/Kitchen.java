package kitchen.entity;

import static javax.persistence.CascadeType.ALL;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name = "kitchen.entity.Kitchen.findAllKitchens",query = "SELECT l FROM Kitchen l")
    , @NamedQuery(name = "kitchen.entity.Vote.findAllVotes",query = "SELECT p FROM Vote p")
	, @NamedQuery(name = "kitchen.entity.Request.findRequestsByKitchen", query = "SELECT DISTINCT r "
		    + "FROM Request r " + "WHERE r.kitchen = :kitchen")
	, @NamedQuery(name = "kitchen.entity.Sacrifice.findSacrificesByKitchen", query = "SELECT DISTINCT s "
		    + "FROM Sacrifice s " + "WHERE s.kitchen = :kitchen")
	, @NamedQuery(name = "kitchen.entity.RequestSacrifice.findRequestSacrificesByRequest", 
		query = "SELECT DISTINCT t " + "FROM RequestSacrifice t " + "WHERE t.request = :request")
})

public class Kitchen implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private Collection<Request> requests;
	private Collection<Sacrifice> sacrifices;
	
	public Kitchen(){}
	
	public Kitchen(Kitchen k){
		this.id = k.id;
		this.name = k.name;
		
		this.requests = k.requests;
		this.sacrifices = k.sacrifices;

	}
	
	public Kitchen(String name){
		this.name = name;
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = ALL, fetch = FetchType.EAGER, mappedBy = "kitchen")
	public Collection<Request> getRequests() {
		return requests;
	}

	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}
	
	public void addRequest(Request r){
		this.getRequests().add(r);
	}

	@OneToMany(cascade = ALL, mappedBy = "kitchen")
	public Collection<Sacrifice> getSacrifices() {
		return sacrifices;
	}

	public void setSacrifices(Collection<Sacrifice> sacrifices) {
		this.sacrifices = sacrifices;
	}
	
	public void addSacrifice(Sacrifice s){
		this.getSacrifices().add(s);
	}
	

}