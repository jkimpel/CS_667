package kitchen.manager;

/*
 * 	Joe Kimpel
 * 	CS 667 - Final Project
 * 	8.10.2012
 * 
 *	Manager.java
 *	This interface specifies the API to any client applications.
 *	Note: The boolean 'inContainer' specifies whether the calling application is in the same 
 *		JAVA container, which will determine whether or not hibernate wrapper classes can be returned
 * 
 */

import java.util.List;

import javax.ejb.Remote;

import kitchen.entity.Kitchen;
import kitchen.entity.Request;
import kitchen.entity.RequestSacrifice;
import kitchen.entity.Sacrifice;
import kitchen.entity.Vote;

@Remote
public interface Manager{
	
	void createKitchen(Kitchen kitchen);
	
	long createRequestInKitchen(Request request, long kitchenId);
	
	long createSacrificeInKitchen(Sacrifice sacrifice, long kitchenId);
	
	long createRequestSacrifice(RequestSacrifice requestSacrifice, long requestId, long sacrificeId);
	
	void createVoteForRequest(Vote vote, long requestId);
	
	void createVoteForRequestSacrifice(Vote vote, long requestSacrificeId);
	
	List<Kitchen> getAllKitchens(boolean inContainer);
	
	Kitchen getKitchen(long kitchenId, boolean inContainer);
	
	List<Request> getRequestsByKitchen(long kitchenId, boolean inContainer);
	
	Request getRequest(long requestId, boolean inContainer);
	
	void updateRequest(Request request);
	
	List<Sacrifice> getSacrificesByKitchen(long kitchenId, boolean inContainer);
	
	Sacrifice getSacrifice(long sacrificeId, boolean inContainer);
	
	List<RequestSacrifice> getRequestSacrificesByRequest(long requestId, boolean inContainer);
	
	RequestSacrifice getRequestSacrifice(long requestSacrificeId, boolean inContainer);
	
	void updateRequestSacrifice(RequestSacrifice rs);
	
	void updateSacrifice(Sacrifice s);
	
	List<Vote> getAllVotes(boolean inContainer);
	
}