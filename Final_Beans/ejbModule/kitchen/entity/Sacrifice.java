package kitchen.entity;

/*
 * 	Joe Kimpel
 * 	CS 667 - Final Project
 * 	8.10.2012
 * 
 *	Sacrifice.java
 *	This class represents an existing item in a Kitchen with the potential to be traded for a Request.
 * 
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SACRIFICE_JK")
public class Sacrifice implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;
	private Kitchen kitchen;
	private String name;
	private String description;
	private boolean hidden;
	private String creationTime;
	
	public Sacrifice(){
		hidden = false;
		creationTime = new java.util.Date().toString();
	}
	
	public Sacrifice(Sacrifice s){
		this.id = s.id;
		this.kitchen = new Kitchen(s.getKitchen());
		this.name = s.name;
		this.description = s.description;
		this.hidden = s.hidden;
		this.creationTime = s.creationTime;
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
	
	@Override
	public boolean equals(Object s){
		if (s.getClass() != Sacrifice.class){
			return false;
		}else{
			return (this.id == ((Sacrifice)s).getId());
		}
	}
	
	
}