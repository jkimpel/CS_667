package kimpel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.*;

import kitchen.entity.Kitchen;
import kitchen.entity.Request;
import kitchen.manager.Manager;

@ManagedBean
@SessionScoped
public class ControllerBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	Manager bean;
	
	private String kitchenName;
	private long kitchenId;
	
	private Kitchen currentKitchen;
	
	public List<SelectItem> getKitchens(){
		List<SelectItem> kitchens = new ArrayList<SelectItem>();
		kitchens.add(new SelectItem(-1, "Select a Kitchen"));
		
		List<Kitchen> ks = bean.getAllKitchens();
		
		for (Kitchen k : ks){
			kitchens.add(new SelectItem(k.getId(), k.getName()));
		}
		
		return kitchens;
	}

	public String getKitchenName() {
		return kitchenName;
	}

	public void setKitchenName(String kitchenName) {
		this.kitchenName = kitchenName;
	}

	public long getKitchenId() {
		return kitchenId;
	}

	public void setKitchenId(long kitchenId) {
		this.kitchenId = kitchenId;
		if (kitchenId > 0){
			setCurrentKitchen(bean.getKitchen(kitchenId));
			setKitchenName(getCurrentKitchen().getName());
		} else{
			setKitchenName("No Kitchen Selected!");
		}
	}

	public Kitchen getCurrentKitchen() {
		return currentKitchen;
	}

	public void setCurrentKitchen(Kitchen currentKitchen) {
		this.currentKitchen = currentKitchen;
	}
	
	public String plusVote(long id){
		Request r = bean.getRequest(id);
		r.setPlusVotes(r.getPlusVotes() + 1);
		bean.updateRequest(r);
		setKitchenId(this.kitchenId);	//this is a hack
		return null;
	}
	
}