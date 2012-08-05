package kimpel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.*;

import kitchen.entity.Kitchen;
import kitchen.entity.Request;
import kitchen.entity.RequestSacrifice;
import kitchen.entity.Sacrifice;
import kitchen.entity.Vote;
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
	private List<Request> requests;
	
	private List<Vote> votes;
	
	private String newRequestName;
	private String newRequestDescription;
	
	private long newRequestSacrificeRequestId;
	private long newRequestSacrificeSacrificeId;
	private String newRequestSacrificeName;
	private String newRequestSacrificeDescription;
	
	public List<SelectItem> getKitchens(){
		List<SelectItem> kitchens = new ArrayList<SelectItem>();
		kitchens.add(new SelectItem(-1, "Select a Kitchen"));
		
		List<Kitchen> ks = bean.getAllKitchens();
		
		for (Kitchen k : ks){
			kitchens.add(new SelectItem(k.getId(), k.getName()));
		}
		
		return kitchens;
	}
	
	public List<SelectItem> getNewRequestSacrificeRequests(){
		List<SelectItem> nrsr = new ArrayList<SelectItem>();
		if (requests == null)
			nrsr.add(new SelectItem(-1, "--Choose a Kitchen--"));
		else{
			nrsr.add(new SelectItem(-1, "-Select a Request-"));
			for (Request r : requests){
				nrsr.add(new SelectItem(r.getId(), r.getName()));
			}
		}
		return nrsr;
	}
	
	public List<SelectItem> getNewRequestSacrificeSacrifices(){
		List<SelectItem> nrss = new ArrayList<SelectItem>();
		if (newRequestSacrificeRequestId <= 0){
			nrss.add(new SelectItem(-1, "--Choose a Request--"));
		} else {
			nrss.add(new SelectItem(-1, "-Select a Sacrifice-"));
			nrss.add(new SelectItem(-2, "-Create a new Sacrifice-"));
			List<RequestSacrifice> rs = bean.getRequestSacrificesByRequest(newRequestSacrificeRequestId);
			List<Sacrifice> notToInclude = new ArrayList<Sacrifice>();
			for (int i=0;i<rs.size();i++){
				notToInclude.add(rs.get(i).getSacrifice());
			}
			List<Sacrifice> sacrifices = bean.getSacrificesByKitchen(kitchenId);
			for (Sacrifice s : sacrifices){
				if (!notToInclude.contains(s))
					nrss.add(new SelectItem(s.getId(), s.getName()));
			}
		}
		return nrss;
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
			setupData();
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
	
	public String plusRequestVote(long id){
		Request r = bean.getRequest(id);
		r.setPlusVotes(r.getPlusVotes() + 1);
		bean.updateRequest(r);
		Vote v = new Vote();
		v.setValue(1);
		bean.createVoteForRequest(v, id);
		updateDisplay();
		return null;
	}
	
	public String minusRequestVote(long id){
		Request r = bean.getRequest(id);
		r.setMinusVotes(r.getMinusVotes() + 1);
		bean.updateRequest(r);
		Vote v = new Vote();
		v.setValue(-1);
		bean.createVoteForRequest(v, id);
		updateDisplay();
		return null;
	}
	
	public String plusRequestSacrificeVote(long id){
		RequestSacrifice rs = bean.getRequestSacrifice(id);
		rs.setPlusVotes(rs.getPlusVotes() + 1);
		bean.updateRequestSacrifice(rs);
		Vote v = new Vote();
		v.setValue(1);
		bean.createVoteForRequestSacrifice(v, id);
		updateDisplay();
		return null;
	}
	
	public String minusRequestSacrificeVote(long id){
		RequestSacrifice rs = bean.getRequestSacrifice(id);
		rs.setMinusVotes(rs.getMinusVotes() + 1);
		bean.updateRequestSacrifice(rs);
		Vote v = new Vote();
		v.setValue(-1);
		bean.createVoteForRequestSacrifice(v, id);
		updateDisplay();
		return null;
	}
	
	public String createNewRequest(){
		Request r = new Request();
		r.setName(getNewRequestName());
		r.setDescription(getNewRequestDescription());
		r.setPlusVotes(1);
		r.setId(bean.createRequestInKitchen(r, kitchenId));
		Vote v = new Vote();
		v.setValue(1);
		bean.createVoteForRequest(v, r.getId());
		setNewRequestName("");
		setNewRequestDescription("");
		updateDisplay();
		return null;
	}
	
	/*
	 * private long newRequestSacrificeRequestId;
	 * private long newSacrificeRequestSacrificeId;
	 * private String newRequestSacrificeName;
	 * private String newRequestSacrificeDescription;
	 */
	public String createNewRequestSacrifice(){
		Request r = null;
		Sacrifice s = null;
		if (newRequestSacrificeRequestId < 0){
			return null;
		} else {
			r = bean.getRequest(newRequestSacrificeRequestId);
		}
		if (newRequestSacrificeSacrificeId < 0){
			//Create a new Sacrifice first
			s = new Sacrifice();
			s.setName(newRequestSacrificeName);
			s.setDescription(newRequestSacrificeDescription);
			s.setId(bean.createSacrificeInKitchen(s, kitchenId));
		} else{
			s = bean.getSacrifice(newRequestSacrificeSacrificeId);
		}
		RequestSacrifice rs = new RequestSacrifice();
		rs.setPlusVotes(1);
		rs.setId(bean.createRequestSacrifice(rs, r.getId(), s.getId()));
		Vote v = new Vote();
		v.setValue(1);
		bean.createVoteForRequestSacrifice(v, rs.getId());
		setNewRequestSacrificeRequestId(-1);
		setNewRequestSacrificeSacrificeId(-1);
		setNewRequestSacrificeName("");
		setNewRequestSacrificeDescription("");
		updateDisplay();
		return null;
	}
	
	private void setupData(){
		setCurrentKitchen(bean.getKitchen(kitchenId));
		setKitchenName(getCurrentKitchen().getName());
		setRequests(bean.getRequestsByKitchen(kitchenId));
		for (int i = 0; i < requests.size(); i ++){
			Request r = requests.get(i);
			r.setRequestSacrifices(bean.getRequestSacrificesByRequest(r.getId()));
		}
	}
	
	private void updateDisplay(){
		setKitchenId(this.kitchenId);	//this is a hack
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public String getNewRequestName() {
		return newRequestName;
	}

	public void setNewRequestName(String newRequestName) {
		this.newRequestName = newRequestName;
	}

	public String getNewRequestDescription() {
		return newRequestDescription;
	}

	public void setNewRequestDescription(String newRequestDescription) {
		this.newRequestDescription = newRequestDescription;
	}

	public long getNewRequestSacrificeRequestId() {
		return newRequestSacrificeRequestId;
	}

	public void setNewRequestSacrificeRequestId(long newRequestSacrificeRequestId) {
		this.newRequestSacrificeRequestId = newRequestSacrificeRequestId;
		setNewRequestSacrificeSacrificeId(-1);
	}

	public String getNewRequestSacrificeName() {
		return newRequestSacrificeName;
	}

	public void setNewRequestSacrificeName(String newRequestSacrificeName) {
		this.newRequestSacrificeName = newRequestSacrificeName;
	}

	public String getNewRequestSacrificeDescription() {
		return newRequestSacrificeDescription;
	}

	public void setNewRequestSacrificeDescription(
			String newRequestSacrificeDescription) {
		this.newRequestSacrificeDescription = newRequestSacrificeDescription;
	}

	public long getNewRequestSacrificeSacrificeId() {
		return newRequestSacrificeSacrificeId;
	}

	public void setNewRequestSacrificeSacrificeId(
			long newRequestSacrificeSacrificeId) {
		this.newRequestSacrificeSacrificeId = newRequestSacrificeSacrificeId;
	}

	public List<Vote> getVotes() {
		votes = bean.getAllVotes();
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}
	
}