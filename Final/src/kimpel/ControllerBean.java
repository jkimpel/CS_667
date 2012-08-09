package kimpel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
	private List<Request> unhiddenRequests;
	private long detailedRequest;
	
	private List<Vote> votes;
	
	private Date lastVote = new Date();
	
	private String newRequestName;
	private String newRequestDescription;
	
	private String newSacrificeName;
	private String newSacrificeDescription;
	
	private String newKitchenName;
	
	private long newRequestSacrificeRequestId;
	private long newRequestSacrificeSacrificeId;
	private String newRequestSacrificeName;
	private String newRequestSacrificeDescription;
	
	private String freezeExplanation;
	private long freezeRS;
	
	private boolean badTryRSName = false;
	private boolean badTryRSDescription = false;
	private boolean badTryRequestName = false;
	private boolean badTryRequestDescription = false;
	private boolean badTrySacrificeName = false;
	private boolean badTrySacrificeDescription = false;
	private boolean badTryKitchenName = false;
	
	public List<SelectItem> getKitchens(){
		List<SelectItem> kitchens = new ArrayList<SelectItem>();
		kitchens.add(new SelectItem(-1, " - Select a Kitchen - "));
		
		List<Kitchen> ks = bean.getAllKitchens();
		
		for (Kitchen k : ks){
			kitchens.add(new SelectItem(k.getId(), k.getName()));
		}
		
		return kitchens;
	}
	
	public List<SelectItem> getAdminKitchens(){
		List<SelectItem> kitchens = getKitchens();
		kitchens.add(new SelectItem(-2, " - Create New Kitchen - "));
		return kitchens;
	}
	
	public List<SelectItem> getNewRequestSacrificeSacrifices(){
		List<SelectItem> nrss = new ArrayList<SelectItem>();
		if (newRequestSacrificeRequestId <= 0){
			nrss.add(new SelectItem(-1, "--Choose a Request--"));
		} else {
			nrss.add(new SelectItem(-1, "-Select Existing Item-"));
			nrss.add(new SelectItem(-2, "-Add Existing Item not listed-"));
			List<RequestSacrifice> rs = bean.getRequestSacrificesByRequest(newRequestSacrificeRequestId);
			List<Sacrifice> notToInclude = new ArrayList<Sacrifice>();
			for (int i=0;i<rs.size();i++){
				notToInclude.add(rs.get(i).getSacrifice());
			}
			List<Sacrifice> sacrifices = bean.getSacrificesByKitchen(kitchenId);
			for (Sacrifice s : sacrifices){
				if (!notToInclude.contains(s) && !s.isHidden())
					nrss.add(new SelectItem(s.getId(), s.getName()));
			}
		}
		return nrss;
	}
	
	public List<SelectItem> getRsList(){
		List<SelectItem> rss = new ArrayList<SelectItem>();
		rss.add(new SelectItem(-1, "-Select A Trade-Out-"));
		List<RequestSacrifice> rs = bean.getRequestSacrificesByRequest(detailedRequest);
		for (int i=0;i<rs.size();i++){
			rss.add(new SelectItem(rs.get(i).getId(), rs.get(i).getSacrifice().getName()));
		}
		return rss;
	}
	
	public String hideOrShow(long requestId){
		if (requestId == detailedRequest){
			return "Hide Details";
		}else{
			return "Show Details";
		}
	}
	
	public void toggleDetails(long requestId){
		if (requestId == detailedRequest){
			detailedRequest = 0;
		}else{
			detailedRequest = requestId;
			clearDetails();
		}
	}
	
	//Set the details pane to the default state
	private void clearDetails(){
		newRequestSacrificeRequestId = detailedRequest;
		newRequestSacrificeSacrificeId = -1;
		newRequestSacrificeName = "";
		newRequestSacrificeDescription = "";
		badTryRSName = false;
		badTryRSDescription = false;
		freezeExplanation = "";
		freezeRS = -1;
	}
	
	private void clearNewRequest(){
		this.setNewRequestName("");
		this.setNewRequestDescription("");
		this.setBadTryRequestName(false);
		this.setBadTryRequestDescription(false);
	}
	
	private void clearNewSacrifice(){
		this.setNewSacrificeName("");
		this.setNewSacrificeDescription("");
		this.setBadTrySacrificeName(false);
		this.setBadTrySacrificeDescription(false);
	}
	
	private void clearNewKitchen(){
		this.setNewKitchenName("");
		this.setBadTryKitchenName(false);
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
		long lastKitchenId = this.kitchenId;
		this.kitchenId = kitchenId;
		if (kitchenId > 0){
			setupData();
			if (lastKitchenId != kitchenId){
				clearNewRequest();
				clearNewSacrifice();
				
			}
		} else{
			setKitchenName("No Kitchen Selected!");
			detailedRequest = 0;
			clearNewKitchen();
		}
	}

	public Kitchen getCurrentKitchen() {
		return currentKitchen;
	}

	public void setCurrentKitchen(Kitchen currentKitchen) {
		this.currentKitchen = currentKitchen;
	}
	
	public String plusRequestVote(long id){
		Date now = new Date();
		if (now.getTime() - lastVote.getTime() < 5000)
			return null;
		Request r = bean.getRequest(id);
		r.setPlusVotes(r.getPlusVotes() + 1);
		bean.updateRequest(r);
		Vote v = new Vote();
		v.setValue(1);
		bean.createVoteForRequest(v, id);
		lastVote = now;
		updateDisplay();
		return null;
	}
	
	public String minusRequestVote(long id){
		Date now = new Date();
		if (now.getTime() - lastVote.getTime() < 5000)
			return null;
		Request r = bean.getRequest(id);
		r.setMinusVotes(r.getMinusVotes() + 1);
		bean.updateRequest(r);
		Vote v = new Vote();
		v.setValue(-1);
		bean.createVoteForRequest(v, id);
		lastVote = now;
		updateDisplay();
		return null;
	}
	
	public String plusRequestSacrificeVote(long id){
		Date now = new Date();
		if (now.getTime() - lastVote.getTime() < 5000)
			return null;
		RequestSacrifice rs = bean.getRequestSacrifice(id);
		rs.setPlusVotes(rs.getPlusVotes() + 1);
		bean.updateRequestSacrifice(rs);
		Vote v = new Vote();
		v.setValue(1);
		bean.createVoteForRequestSacrifice(v, id);
		lastVote = now;
		updateDisplay();
		return null;
	}
	
	public String minusRequestSacrificeVote(long id){
		Date now = new Date();
		if (now.getTime() - lastVote.getTime() < 5000)
			return null;
		RequestSacrifice rs = bean.getRequestSacrifice(id);
		rs.setMinusVotes(rs.getMinusVotes() + 1);
		bean.updateRequestSacrifice(rs);
		Vote v = new Vote();
		v.setValue(-1);
		bean.createVoteForRequestSacrifice(v, id);
		lastVote = now;
		updateDisplay();
		return null;
	}
	
	public String createNewRequest(){
		Request r = new Request();
		if ((newRequestName == null)
				||(newRequestName.length() < 3)
				||(newRequestName.length() > 20)){
			badTryRequestName = true;
		}else{
			badTryRequestName = false;
		}
		if ((newRequestDescription != null)
				&&(newRequestDescription.length()>40)){
			badTryRequestDescription = true;
		}else{
			badTryRequestDescription = false;
		}
		if (badTryRequestName || badTryRequestDescription)
			return null;
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
	
	public String createNewSacrifice(){
		if ((newSacrificeName == null)
				||(newSacrificeName.length() < 3)
				||(newSacrificeName.length() > 20)){
			badTrySacrificeName = true;
		}else{
			badTrySacrificeName = false;
		}
		if ((newSacrificeDescription != null)
				&&(newSacrificeDescription.length()>40)){
			badTrySacrificeDescription = true;
		}else{
			badTrySacrificeDescription = false;
		}
		if (badTrySacrificeName || badTrySacrificeDescription)
			return null;
		Sacrifice s = new Sacrifice();
		s.setName(newSacrificeName);
		s.setDescription(newSacrificeDescription);
		bean.createSacrificeInKitchen(s, kitchenId);
		setNewSacrificeName("");
		setNewSacrificeDescription("");
		return null;
	}
	
	public String createNewKitchen(){
		if ((newKitchenName == null)
				||(newKitchenName.length() < 3)
				||(newKitchenName.length() > 20)){
			badTryKitchenName = true;
			return null;
		}else{
			badTryKitchenName = false;
		}
		Kitchen k = new Kitchen();
		k.setName(newKitchenName);
		bean.createKitchen(k);
		setNewKitchenName("");
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
			if ((newRequestSacrificeName == null)
					||(newRequestSacrificeName.length()<3)
					||(newRequestSacrificeName.length()>20)){
				badTryRSName = true;
				return null;
			}else{
				badTryRSName = false;
			}
			if ((newRequestSacrificeDescription != null) 
					&&(newRequestSacrificeDescription.length()>40)){
				badTryRSDescription = true;
				return null;
			}
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
		setNewRequestSacrificeSacrificeId(-1);
		setNewRequestSacrificeName("");
		badTryRSName = false;
		badTryRSDescription = false;
		setNewRequestSacrificeDescription("");
		updateDisplay();
		return null;
	}
	
	public String freezeLabel(){
		Request r = bean.getRequest(detailedRequest);
		if (!r.isFrozen()){
			return "Freeze " + r.getName();
		}else if (!r.isHidden()){
			return "Hide " + r.getName();
		}else{
			return "Un-Hide " + r.getName();
		}
	}
	
	public String freezeRequest(){
		Request r = bean.getRequest(detailedRequest);
		if (!r.isFrozen()){
			r.setFrozen(true);
		}else if (!r.isHidden()){
			r.setHidden(true);
		}else{
			r.setFrozen(false);
			r.setHidden(false);
		}
		r.setWhyFrozen(freezeExplanation);
		bean.updateRequest(r);
		setFreezeExplanation("");
		updateDisplay();
		return null;
	}
	
	public String hideSacrificeLabel(long sacrificeId){
		Sacrifice s = bean.getSacrifice(sacrificeId);
		if (s.isHidden()){
			return "Un-Hide " + s.getName();
		}else{
			return "Hide " + s.getName();
		}
	}
	
	public String hideSacrifice(long sacrificeId){
		Sacrifice s = bean.getSacrifice(sacrificeId);
		s.setHidden(!s.isHidden());
		bean.updateSacrifice(s);
		updateDisplay();
		return null;
	}
	
	private void setupData(){
		setCurrentKitchen(bean.getKitchen(kitchenId));
		setKitchenName(getCurrentKitchen().getName());
		setRequests(bean.getRequestsByKitchen(kitchenId));	
		unhiddenRequests = new ArrayList<Request>();
		for (int i = 0; i < requests.size(); i ++){
			Request r = requests.get(i);
			r.setRequestSacrifices(bean.getRequestSacrificesByRequest(r.getId()));
			if (!r.isHidden()){
				unhiddenRequests.add(r);
			}
		}
		
	}
	
	public String freezeRSLabel(){
		RequestSacrifice rs = bean.getRequestSacrifice(freezeRS);
		if (!rs.isFrozen()){
			return "Freeze " + rs.getRequest().getName() + "/" + rs.getSacrifice().getName();
		} else if (!rs.isHidden()){
			return "Hide " + rs.getRequest().getName() + "/" + rs.getSacrifice().getName();
		}else{
			return "Un-Freeze " + rs.getRequest().getName() + "/" + rs.getSacrifice().getName();
		}
	}
	
	public String freezeRequestSacrifice(){
		RequestSacrifice rs = bean.getRequestSacrifice(freezeRS);
		if (!rs.isFrozen()){
			rs.setFrozen(true);
		} else if (!rs.isHidden()){
			rs.setHidden(true);
		}else{
			rs.setFrozen(false);
			rs.setHidden(false);
		}
		rs.setWhyFrozen(freezeExplanation);
		bean.updateRequestSacrifice(rs);
		setFreezeExplanation("");
		updateDisplay();
		return null;
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
	
	public List<Sacrifice> getSacrifices() {
		return bean.getSacrificesByKitchen(kitchenId);
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
		badTryRSName = false;
		badTryRSDescription = false;
		this.newRequestSacrificeSacrificeId = newRequestSacrificeSacrificeId;
	}

	public List<Vote> getVotes() {
		votes = bean.getAllVotes();
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}
	
	public long getDetailedRequest(){
		return detailedRequest;
	}
	public void setDetailedRequest(long id){
		this.detailedRequest = id;
	}

	public boolean isBadTryRSName() {
		return badTryRSName;
	}

	public void setBadTryRSName(boolean badTryRSName) {
		this.badTryRSName = badTryRSName;
	}

	public boolean isBadTryRequestName() {
		return badTryRequestName;
	}

	public void setBadTryRequestName(boolean badTryRequestName) {
		this.badTryRequestName = badTryRequestName;
	}

	public boolean isBadTryRSDescription() {
		return badTryRSDescription;
	}

	public void setBadTryRSDescription(boolean badTryRSDescription) {
		this.badTryRSDescription = badTryRSDescription;
	}

	public boolean isBadTryRequestDescription() {
		return badTryRequestDescription;
	}

	public void setBadTryRequestDescription(boolean badTryRequestDescription) {
		this.badTryRequestDescription = badTryRequestDescription;
	}

	public String getFreezeExplanation() {
		return freezeExplanation;
	}

	public void setFreezeExplanation(String freezeExplanation) {
		this.freezeExplanation = freezeExplanation;
	}

	public List<Request> getUnhiddenRequests() {
		return unhiddenRequests;
	}

	public void setUnhiddenRequests(List<Request> unhiddenRequests) {
		this.unhiddenRequests = unhiddenRequests;
	}

	public long getFreezeRS() {
		return freezeRS;
	}

	public void setFreezeRS(long freezeRS) {
		this.freezeRS = freezeRS;
	}

	public String getNewSacrificeName() {
		return newSacrificeName;
	}

	public void setNewSacrificeName(String newSacrificeName) {
		this.newSacrificeName = newSacrificeName;
	}

	public String getNewSacrificeDescription() {
		return newSacrificeDescription;
	}

	public void setNewSacrificeDescription(String newSacrificeDescription) {
		this.newSacrificeDescription = newSacrificeDescription;
	}

	public boolean isBadTrySacrificeName() {
		return badTrySacrificeName;
	}

	public void setBadTrySacrificeName(boolean badTrySacrificeName) {
		this.badTrySacrificeName = badTrySacrificeName;
	}

	public boolean isBadTrySacrificeDescription() {
		return badTrySacrificeDescription;
	}

	public void setBadTrySacrificeDescription(boolean badTrySacrificeDescription) {
		this.badTrySacrificeDescription = badTrySacrificeDescription;
	}

	public String getNewKitchenName() {
		return newKitchenName;
	}

	public void setNewKitchenName(String newKitchenName) {
		this.newKitchenName = newKitchenName;
	}

	public boolean isBadTryKitchenName() {
		return badTryKitchenName;
	}

	public void setBadTryKitchenName(boolean badTryKitchenName) {
		this.badTryKitchenName = badTryKitchenName;
	}
	
}