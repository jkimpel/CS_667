package kimpel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.*;

import kitchen.entity.Kitchen;
import kitchen.manager.Manager;

@ManagedBean
@SessionScoped
public class ControllerBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	Manager bean;
	
	private String kitchenName;
	
	public List<SelectItem> getKitchens(){
		List<SelectItem> kitchens = new ArrayList<SelectItem>();
		kitchens.add(new SelectItem("Select a Kitchen"));
		
		List<Kitchen> ks = bean.getAllKitchens();
		
		for (Kitchen k : ks){
			kitchens.add(new SelectItem(k.getName()));
		}
		
		return kitchens;
	}

	public String getKitchenName() {
		return kitchenName;
	}

	public void setKitchenName(String kitchenName) {
		this.kitchenName = kitchenName;
	}
	
}