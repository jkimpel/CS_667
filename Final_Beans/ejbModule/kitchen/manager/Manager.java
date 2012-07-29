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
	
	void createRequest(Request request);
	
	void createSacrifice(Sacrifice sacrifice);
	
	void createRequestSacrifice(RequestSacrifice requestSacrifice);
	
	void createVote(Vote vote);
	
	List<Kitchen> getAllKitchens();
	
	List<Request> getRequestsByKitchen(String kitchenId);
	
	List<Sacrifice> getSacrificesByKitchen(String kitchenId);
	
	List<RequestSacrifice> getRequestSacrificesByRequest(String requestId);
	
	List<Vote> getAllVotes();
	
}