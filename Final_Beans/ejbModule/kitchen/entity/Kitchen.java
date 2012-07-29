package kitchen.entity;

import static javax.persistence.CascadeType.ALL;

import java.util.Collection;

import javax.persistence.Entity;
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
	
	private String id;
	private String name;
	private Collection<Request> requests;
	private Collection<Sacrifice> sacrifices;
	
	public Kitchen(){}
	
	public Kitchen(String id, String name){
		this.id = id;
		this.name = name;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = ALL, mappedBy = "kitchen")
	public Collection<Request> getRequests() {
		return requests;
	}

	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}

	@OneToMany(cascade = ALL, mappedBy = "kitchen")
	public Collection<Sacrifice> getSacrifices() {
		return sacrifices;
	}

	public void setSacrifices(Collection<Sacrifice> sacrifices) {
		this.sacrifices = sacrifices;
	}
	
	
	

}