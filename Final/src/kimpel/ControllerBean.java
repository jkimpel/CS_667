package kimpel;

/*
 * 	Joe Kimpel
 * 	CS 667 - Final Project
 * 	8.10.2012
 * 
 *	ControllerBean.java
 *	This is the session bean that handles the web interface.
 * 
 */

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
	private static final int TIME_BETWEEN_VOTES = 5000;
	private static final int MIN_NAME_CHARS = 3;
	private static final int MAX_NAME_CHARS = 20;
	private static final int MAX_DESC_CHARS = 40;
	private static final String LOGIN_NAME = "admin";
	private static final String PASSWORD = "8wonders";
	
	@EJB
	Manager bean;
	
	//Some state info
	private String kitchenName;
	private long kitchenId;
	private Kitchen currentKitchen;
	private List<Request> requests;
	private List<Request> unhiddenRequests;
	private List<Vote> votes;
	private boolean admin = false;
	
	//this is the id of whichever request we are taking a closer look at
	private long detailedRequest;
	
	//time stamp of the last vote
	private Date lastVote = new Date();
	
	//these hold input from the forms
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
	private String loginName;
	private String password;
	
	//these flags handle the input validation
	private boolean badTryRSName = false;
	private boolean badTryRSDescription = false;
	private boolean badTryRequestName = false;
	private boolean badTryRequestDescription = false;
	private boolean badTrySacrificeName = false;
	private boolean badTrySacrificeDescription = false;
	private boolean badTryKitchenName = false;
	private boolean badTryLoginName = false;
	private boolean badTryPassword = false;
	private boolean badTryLogin = false;
	
	//This sets up the initial select box of kitchens (for the client view)
	public List<SelectItem> getKitchens(){
		List<SelectItem> kitchens = new ArrayList<SelectItem>();
		kitchens.add(new SelectItem(-1, " - Select a Kitchen - "));
		
		List<Kitchen> ks = bean.getAllKitchens(true);
		
		for (Kitchen k : ks){
			kitchens.add(new SelectItem(k.getId(), k.getName()));
		}
		
		return kitchens;
	}
	
	//This sets up the initial select box of kitchens (for the admin view)
	public List<SelectItem> getAdminKitchens(){
		List<SelectItem> kitchens = getKitchens();
		kitchens.add(new SelectItem(-2, " - Create New Kitchen - "));
		return kitchens;
	}
	
	//This sets up a select box of Sacrifices when a client is creating a new Request-Sacrifice
	public List<SelectItem> getNewRequestSacrificeSacrifices(){
		List<SelectItem> nrss = new ArrayList<SelectItem>();
		if (newRequestSacrificeRequestId <= 0){
			nrss.add(new SelectItem(-1, "--Choose a Request--"));
		} else {
			nrss.add(new SelectItem(-1, "-Select Existing Item-"));
			nrss.add(new SelectItem(-2, "-Add Existing Item not listed-"));
			List<RequestSacrifice> rs = bean.getRequestSacrificesByRequest(newRequestSacrificeRequestId, true);
			List<Sacrifice> notToInclude = new ArrayList<Sacrifice>();
			for (int i=0;i<rs.size();i++){
				notToInclude.add(rs.get(i).getSacrifice());
			}
			List<Sacrifice> sacrifices = bean.getSacrificesByKitchen(kitchenId, true);
			for (Sacrifice s : sacrifices){
				if (!notToInclude.contains(s) && !s.isHidden())
					nrss.add(new SelectItem(s.getId(), s.getName()));
			}
		}
		return nrss;
	}
	
	//This sets up a select box of the current request's RequestSacrifices (for admin view)
	public List<SelectItem> getRsList(){
		List<SelectItem> rss = new ArrayList<SelectItem>();
		rss.add(new SelectItem(-1, "-Select A Trade-Out-"));
		List<RequestSacrifice> rs = bean.getRequestSacrificesByRequest(detailedRequest, true);
		for (int i=0;i<rs.size();i++){
			rss.add(new SelectItem(rs.get(i).getId(), rs.get(i).getSacrifice().getName()));
		}
		return rss;
	}
	
	//Generates a label for the button controlling the details pane
	public String hideOrShow(long requestId){
		if (requestId == detailedRequest){
			return "Hide Details";
		}else{
			return "Show Details";
		}
	}
	
	//Change which Request is currently being detailed
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
	
	//clear the form to create a new Request
	private void clearNewRequest(){
		this.setNewRequestName("");
		this.setNewRequestDescription("");
		this.setBadTryRequestName(false);
		this.setBadTryRequestDescription(false);
	}
	
	//clear the form to create a new Sacrifice (admin view)
	private void clearNewSacrifice(){
		this.setNewSacrificeName("");
		this.setNewSacrificeDescription("");
		this.setBadTrySacrificeName(false);
		this.setBadTrySacrificeDescription(false);
	}
	
	//clear the form to create a new Kitchen (admin view)
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

	//Called when the user selects a kitchen
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
	
	//Handles plus votes on a Request
	public String plusRequestVote(long id){
		Date now = new Date();
		if (now.getTime() - lastVote.getTime() < TIME_BETWEEN_VOTES)
			return null;
		Vote v = new Vote();
		v.setValue(1);
		bean.createVoteForRequest(v, id);
		lastVote = now;
		updateDisplay();
		return null;
	}
	
	//Handles minus votes on a Request
	public String minusRequestVote(long id){
		Date now = new Date();
		if (now.getTime() - lastVote.getTime() < TIME_BETWEEN_VOTES)
			return null;
		Vote v = new Vote();
		v.setValue(-1);
		bean.createVoteForRequest(v, id);
		lastVote = now;
		updateDisplay();
		return null;
	}
	
	//handles plus votes on a Request-Sacrifice
	public String plusRequestSacrificeVote(long id){
		Date now = new Date();
		if (now.getTime() - lastVote.getTime() < TIME_BETWEEN_VOTES)
			return null;
		Vote v = new Vote();
		v.setValue(1);
		bean.createVoteForRequestSacrifice(v, id);
		lastVote = now;
		updateDisplay();
		return null;
	}
	
	//Handles minus votes on a Request-Sacrifice
	public String minusRequestSacrificeVote(long id){
		Date now = new Date();
		if (now.getTime() - lastVote.getTime() < TIME_BETWEEN_VOTES)
			return null;
		RequestSacrifice rs = bean.getRequestSacrifice(id, true);
		bean.updateRequestSacrifice(rs);
		Vote v = new Vote();
		v.setValue(-1);
		bean.createVoteForRequestSacrifice(v, id);
		lastVote = now;
		updateDisplay();
		return null;
	}
	
	//Handles client creating a new request
	public String createNewRequest(){
		Request r = new Request();
		if ((newRequestName == null)
				||(newRequestName.length() < MIN_NAME_CHARS)
				||(newRequestName.length() > MAX_NAME_CHARS)){
			badTryRequestName = true;
		}else{
			badTryRequestName = false;
		}
		if ((newRequestDescription != null)
				&&(newRequestDescription.length()>MAX_DESC_CHARS)){
			badTryRequestDescription = true;
		}else{
			badTryRequestDescription = false;
		}
		if (badTryRequestName || badTryRequestDescription)
			return null;
		r.setName(getNewRequestName());
		r.setDescription(getNewRequestDescription());
		r.setId(bean.createRequestInKitchen(r, kitchenId));
		Vote v = new Vote();
		v.setValue(1);
		bean.createVoteForRequest(v, r.getId());
		clearNewRequest();
		updateDisplay();
		return null;
	}
	
	//Handles when an admin creates a new sacrifice
	public String createNewSacrifice(){
		if ((newSacrificeName == null)
				||(newSacrificeName.length() < MIN_NAME_CHARS)
				||(newSacrificeName.length() > MAX_NAME_CHARS)){
			badTrySacrificeName = true;
		}else{
			badTrySacrificeName = false;
		}
		if ((newSacrificeDescription != null)
				&&(newSacrificeDescription.length()>MAX_DESC_CHARS)){
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
		clearNewSacrifice();
		return null;
	}
	
	//handles when an admin creates a new kitchen
	public String createNewKitchen(){
		if ((newKitchenName == null)
				||(newKitchenName.length() < MIN_NAME_CHARS)
				||(newKitchenName.length() > MAX_NAME_CHARS)){
			badTryKitchenName = true;
			return null;
		}else{
			badTryKitchenName = false;
		}
		Kitchen k = new Kitchen();
		k.setName(newKitchenName);
		bean.createKitchen(k);
		clearNewKitchen();
		return null;
	}
	
	public String login(){
		if ((loginName == null)
				||(loginName.length() < MIN_NAME_CHARS)
				||(loginName.length() > MAX_NAME_CHARS)){
			badTryLoginName = true;
		}else{
			badTryLoginName = false;
		}
		if ((password == null)
				||(password.length() < MIN_NAME_CHARS)
				||(password.length() > MAX_NAME_CHARS)){
			badTryPassword = true;
		}else{
			badTryPassword = false;
		}
		if (badTryLoginName || badTryPassword){
			badTryLogin = false;
			return null;
		}
		if ((loginName.equals(LOGIN_NAME)) && (password.equals(PASSWORD))){
			badTryLogin = false;
			setAdmin(true);
		} else{
			badTryLogin = true;
		}
		return null;
	}
	
	public String logout(){
		loginName = "";
		password = "";
		badTryLoginName = false;
		badTryPassword = false;
		badTryLogin = false;
		setAdmin(false);
		return null;
	}
	
	//Handles when a client tries to create a new Request-Sacrifice
	public String createNewRequestSacrifice(){
		Request r = null;
		Sacrifice s = null;
		if (newRequestSacrificeRequestId < 0){
			return null;
		} else {
			r = bean.getRequest(newRequestSacrificeRequestId, true);
		}
		if (newRequestSacrificeSacrificeId < 0){	//Create a new Sacrifice first
			if ((newRequestSacrificeName == null)
					||(newRequestSacrificeName.length()<MIN_NAME_CHARS)
					||(newRequestSacrificeName.length()>MAX_NAME_CHARS)){
				badTryRSName = true;
			}else{
				badTryRSName = false;
			}
			if ((newRequestSacrificeDescription != null) 
					&&(newRequestSacrificeDescription.length()>MAX_DESC_CHARS)){
				badTryRSDescription = true;
			} else {
				badTryRSDescription = false;
			}
			if (badTryRSName || badTryRSDescription){
				return null;
			}
			s = new Sacrifice();
			s.setName(newRequestSacrificeName);
			s.setDescription(newRequestSacrificeDescription);
			s.setId(bean.createSacrificeInKitchen(s, kitchenId));
		} else{
			s = bean.getSacrifice(newRequestSacrificeSacrificeId, true);
		}
		RequestSacrifice rs = new RequestSacrifice();
		rs.setId(bean.createRequestSacrifice(rs, r.getId(), s.getId()));
		Vote v = new Vote();
		v.setValue(1);
		bean.createVoteForRequestSacrifice(v, rs.getId());
		clearDetails();
		updateDisplay();
		return null;
	}
	
	//Generate a label for the button to handle Request freeze/hides (admin view)
	public String freezeLabel(){
		Request r = bean.getRequest(detailedRequest, true);
		if (!r.isFrozen()){
			return "Freeze " + r.getName();
		}else if (!r.isHidden()){
			return "Hide " + r.getName();
		}else{
			return "Un-Hide " + r.getName();
		}
	}
	
	//Handles when an admin modifies freeze/hide state of a request
	public String freezeRequest(){
		Request r = bean.getRequest(detailedRequest, true);
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
	
	//Generate label for the button to modify hide status of a Sacrifice (admin view)
	public String hideSacrificeLabel(long sacrificeId){
		Sacrifice s = bean.getSacrifice(sacrificeId, true);
		if (s.isHidden()){
			return "Un-Hide " + s.getName();
		}else{
			return "Hide " + s.getName();
		}
	}
	
	//handles hiding/unhiding a sacrifice
	public String hideSacrifice(long sacrificeId){
		Sacrifice s = bean.getSacrifice(sacrificeId, true);
		s.setHidden(!s.isHidden());
		bean.updateSacrifice(s);
		updateDisplay();
		return null;
	}
	
	//Generate label for modifying freeze/hide status of a request-sacrifice (admin view)
	public String freezeRSLabel(){
		RequestSacrifice rs = bean.getRequestSacrifice(freezeRS, true);
		if (!rs.isFrozen()){
			return "Freeze " + rs.getRequest().getName() + "/" + rs.getSacrifice().getName();
		} else if (!rs.isHidden()){
			return "Hide " + rs.getRequest().getName() + "/" + rs.getSacrifice().getName();
		}else{
			return "Un-Freeze " + rs.getRequest().getName() + "/" + rs.getSacrifice().getName();
		}
	}
	
	//Handles modifying freeze/hide status of a request-sacrifice (admin view)
	public String freezeRequestSacrifice(){
		RequestSacrifice rs = bean.getRequestSacrifice(freezeRS, true);
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
	
	//Prepare data for page generation
	private void setupData(){
		setCurrentKitchen(bean.getKitchen(kitchenId, true));
		setKitchenName(getCurrentKitchen().getName());
		setRequests(bean.getRequestsByKitchen(kitchenId, true));	
		unhiddenRequests = new ArrayList<Request>();
		for (int i = 0; i < requests.size(); i ++){
			Request r = requests.get(i);
			r.setRequestSacrifices(bean.getRequestSacrificesByRequest(r.getId(), true));
			if (!r.isHidden()){
				unhiddenRequests.add(r);
			}
		}	
	}

	//This will reload much of the data
	private void updateDisplay(){
		setKitchenId(this.kitchenId);
	}
	
	//Below here are the getters & setters

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}
	
	public List<Sacrifice> getSacrifices() {
		return bean.getSacrificesByKitchen(kitchenId, true);
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
		votes = bean.getAllVotes(true);
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isBadTryLoginName() {
		return badTryLoginName;
	}

	public void setBadTryLoginName(boolean badTryLoginName) {
		this.badTryLoginName = badTryLoginName;
	}

	public boolean isBadTryPassword() {
		return badTryPassword;
	}

	public void setBadTryPassword(boolean badTryPassword) {
		this.badTryPassword = badTryPassword;
	}

	public boolean isBadTryLogin() {
		return badTryLogin;
	}

	public void setBadTryLogin(boolean badTryLogin) {
		this.badTryLogin = badTryLogin;
	}
	
}