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
	
	void createRequestInKitchen(Request request, long kitchenId);
	
	void createSacrificeInKitchen(Sacrifice sacrifice, long kitchenId);
	
	void createRequestSacrifice(RequestSacrifice requestSacrifice, long requestId, long sacrificeId);
	
	void createVoteForRequest(Vote vote, long requestId);
	
	void createVoteForRequestSacrifice(Vote vote, long requestSacrificeId);
	
	List<Kitchen> getAllKitchens();
	
	List<Request> getRequestsByKitchen(long kitchenId);
	
	List<Sacrifice> getSacrificesByKitchen(long kitchenId);
	
	List<RequestSacrifice> getRequestSacrificesByRequest(long requestId);
	
	List<Vote> getAllVotes();
	
}