package kitchen.manager;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kitchen.entity.Kitchen;
import kitchen.entity.Request;
import kitchen.entity.RequestSacrifice;
import kitchen.entity.Sacrifice;
import kitchen.entity.Vote;

@Stateless
public class ManagerBean implements Manager{

	@PersistenceContext(unitName="kitchenDB")
	private EntityManager em;

	public void createKitchen(Kitchen kitchen) {
		try {
            em.persist(kitchen);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
	}

	public void createRequestInKitchen(Request request, long kitchenId) {
		try {
			Kitchen kitchen = em.find(Kitchen.class, kitchenId);
            em.persist(request);
            request.setKitchen(kitchen);
            kitchen.addRequest(request);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
	}

	public void createSacrificeInKitchen(Sacrifice sacrifice, long kitchenId) {
		try {
			Kitchen kitchen = em.find(Kitchen.class, kitchenId);
            em.persist(sacrifice);
            sacrifice.setKitchen(kitchen);
            kitchen.addSacrifice(sacrifice);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
		
	}

	public void createRequestSacrifice(RequestSacrifice requestSacrifice, long requestId, long sacrificeId) {
		try {
			Request request = em.find(Request.class, requestId);
			Sacrifice sacrifice = em.find(Sacrifice.class, sacrificeId);
            em.persist(requestSacrifice);
            requestSacrifice.setRequest(request);
            requestSacrifice.setSacrifice(sacrifice);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
		
	}

	public void createVoteForRequest(Vote vote, long requestId) {
		try {
			Request request = em.find(Request.class, requestId);
            em.persist(vote);
            vote.setRequest(request);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
	}
	
	public void createVoteForRequestSacrifice(Vote vote, long requestSacrificeId){
		try {
			RequestSacrifice rs = em.find(RequestSacrifice.class, requestSacrificeId);
            em.persist(vote);
            vote.setRequestSacrifice(rs);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
	}

	@SuppressWarnings("unchecked")
	public List<Kitchen> getAllKitchens() {
        List<Kitchen> kitchens = null;
        try {
            kitchens = (List<Kitchen>) em.createNamedQuery(
                        "kitchen.entity.Kitchen.findAllKitchens")
                                       .getResultList();

            kitchens = unwrapKitchens(kitchens);
            return kitchens;
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
	}

	@SuppressWarnings("unchecked")
	public List<Request> getRequestsByKitchen(long kitchenId) {
		List<Request> requests = null;
		
		try {
            Kitchen kitchen = em.find(Kitchen.class, kitchenId);
            requests = (List<Request>) em.createNamedQuery(
                        "kitchen.entity.Request.findRequestsByKitchen")
                                       .setParameter("kitchen", kitchen)
                                       .getResultList();
            requests = unwrapRequests(requests);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
		
		return requests;
	}

	@SuppressWarnings("unchecked")
	public List<Sacrifice> getSacrificesByKitchen(long kitchenId) {
		List<Sacrifice> sacrifices = null;
		
		try {
            Kitchen kitchen = em.find(Kitchen.class, kitchenId);
            sacrifices = (List<Sacrifice>) em.createNamedQuery(
                        "kitchen.entity.Sacrifice.findSacrificesByKitchen")
                                       .setParameter("kitchen", kitchen)
                                       .getResultList();
            sacrifices = unwrapSacrifices(sacrifices);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
		
		return sacrifices;
	}

	@SuppressWarnings("unchecked")
	public List<RequestSacrifice> getRequestSacrificesByRequest(long requestId) {
		List<RequestSacrifice> requestSacrifices = null;
		
		try{
			Request request = em.find(Request.class, requestId);
			requestSacrifices = (List<RequestSacrifice>) em.createNamedQuery(
						"kitchen.entity.RequestSacrifice.findRequestSacrificesByRequest")
										.setParameter("request", request)
										.getResultList();
			requestSacrifices = unwrapRequestSacrifices(requestSacrifices);
		} catch (Exception ex) {
			throw new EJBException(ex);
		}
		
		return requestSacrifices;
	}

	@SuppressWarnings("unchecked")
	public List<Vote> getAllVotes() {
        List<Vote> votes = null;

        try {
            votes = (List<Vote>) em.createNamedQuery(
                        "kitchen.entity.Vote.findAllVotes")
                                       .getResultList();
            
            votes = unwrapVotes(votes);

            return votes;
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
	}
	
	private List<Kitchen> unwrapKitchens(List<Kitchen> k){
		List<Kitchen> r = new ArrayList<Kitchen>();
		
		for (int i = 0; i < k.size(); i++){
			r.add(new Kitchen(k.get(i)));
		}
		
		return r;
		
	}
	
	private List<Request> unwrapRequests(List<Request> r){
		List<Request> q = new ArrayList<Request>();
		
		for (int i=0; i < r.size(); i++){
			q.add(new Request(r.get(i)));
		}
		
		return q;
	}
	
	private List<Sacrifice> unwrapSacrifices(List<Sacrifice> s){
		List<Sacrifice> t = new ArrayList<Sacrifice>();
		
		for (int i=0;i<s.size();i++){
			t.add(new Sacrifice(s.get(i)));
		}
		return t;
	}
	
	private List<RequestSacrifice> unwrapRequestSacrifices(List<RequestSacrifice> rs){
		List<RequestSacrifice> r = new ArrayList<RequestSacrifice>();
		
		for (int i=0; i<rs.size(); i++){
			r.add(new RequestSacrifice(rs.get(i)));
		}
		
		return r;
	}
	
	private List<Vote> unwrapVotes(List<Vote> votes){
		List<Vote> v = new ArrayList<Vote>();
		
		for (int i=0;i<votes.size();i++){
			v.add(new Vote(votes.get(i)));
		}
		
		return v;
	}

}