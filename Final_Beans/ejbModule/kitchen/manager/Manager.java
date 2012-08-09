package kitchen.manager;

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
	
	List<Kitchen> getAllKitchens();
	
	Kitchen getKitchen(long kitchenId);
	
	List<Request> getRequestsByKitchen(long kitchenId);
	
	Request getRequest(long requestId);
	
	void updateRequest(Request request);
	
	List<Sacrifice> getSacrificesByKitchen(long kitchenId);
	
	Sacrifice getSacrifice(long sacrificeId);
	
	List<RequestSacrifice> getRequestSacrificesByRequest(long requestId);
	
	RequestSacrifice getRequestSacrifice(long requestSacrificeId);
	
	void updateRequestSacrifice(RequestSacrifice rs);
	
	void updateSacrifice(Sacrifice s);
	
	List<Vote> getAllVotes();
	
}