package kitchen.manager;

/*
 * 	Joe Kimpel
 * 	CS 667 - Final Project
 * 	8.10.2012
 * 
 *	ManagerBean.java
 *	This handles all the API functionality
 * 
 */

import java.util.ArrayList;
import java.util.Collections;
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
import kitchen.util.RequestComparator;
import kitchen.util.RequestSacrificeComparator;

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

	public long createRequestInKitchen(Request request, long kitchenId) {
		long requestId = -1;
		try {
			Kitchen kitchen = em.find(Kitchen.class, kitchenId);
            em.persist(request);
            request.setKitchen(kitchen);
            kitchen.addRequest(request);
            requestId = request.getId();
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
		return requestId;
	}

	public long createSacrificeInKitchen(Sacrifice sacrifice, long kitchenId) {
		long sacrificeId = -1;
		try {
			Kitchen kitchen = em.find(Kitchen.class, kitchenId);
            em.persist(sacrifice);
            sacrifice.setKitchen(kitchen);
            kitchen.addSacrifice(sacrifice);
            sacrificeId = sacrifice.getId();
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
		return sacrificeId;
	}

	public long createRequestSacrifice(RequestSacrifice requestSacrifice, long requestId, long sacrificeId) {
		long rsId = -1;
		try {
			Request request = em.find(Request.class, requestId);
			Sacrifice sacrifice = em.find(Sacrifice.class, sacrificeId);
            em.persist(requestSacrifice);
            requestSacrifice.setRequest(request);
            requestSacrifice.setSacrifice(sacrifice);
            rsId = requestSacrifice.getId();
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
		return rsId;
	}

	public void createVoteForRequest(Vote vote, long requestId) {
		try {
			Request request = em.find(Request.class, requestId);
            em.persist(vote);
            vote.setRequest(request);
            if (vote.getValue() == 1){
            	request.setPlusVotes(request.getPlusVotes() + 1);
            }else{
            	request.setMinusVotes(request.getMinusVotes() + 1);
        	}
            	
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
	}
	
	public void createVoteForRequestSacrifice(Vote vote, long requestSacrificeId){
		try {
			RequestSacrifice rs = em.find(RequestSacrifice.class, requestSacrificeId);
            em.persist(vote);
            vote.setRequestSacrifice(rs);
            if (vote.getValue() == 1){
            	rs.setPlusVotes(rs.getPlusVotes() + 1);
            }else{
            	rs.setMinusVotes(rs.getMinusVotes() + 1);
        	}
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
	}

	@SuppressWarnings("unchecked")
	public List<Kitchen> getAllKitchens(boolean inContainer) {
        List<Kitchen> kitchens = null;
        try {
            kitchens = (List<Kitchen>) em.createNamedQuery(
                        "kitchen.entity.Kitchen.findAllKitchens")
                                       .getResultList();
            if (!inContainer)
            	kitchens = unwrapKitchens(kitchens);
            return kitchens;
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
	}
	
	public Kitchen getKitchen(long kitchenId, boolean inContainer){
		Kitchen k = null;
		
		try{
			k = em.find(Kitchen.class, kitchenId);
			if (!inContainer)
				k = unwrapKitchen(k);
			
		} catch (Exception ex) {
            throw new EJBException(ex);
        }
		
		return k;
	}

	@SuppressWarnings("unchecked")
	public List<Request> getRequestsByKitchen(long kitchenId, boolean inContainer) {
		List<Request> requests = null;
		
		try {
            Kitchen kitchen = em.find(Kitchen.class, kitchenId);
            requests = (List<Request>) em.createNamedQuery(
                        "kitchen.entity.Request.findRequestsByKitchen")
                                       .setParameter("kitchen", kitchen)
                                       .getResultList();
            requests = sortRequestsByNetVotes(requests);
            if (!inContainer)
            	requests = unwrapRequests(requests);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
		
		return requests;
	}
	
	public Request getRequest(long requestId, boolean inContainer){
		Request r = null;
		try{
			r = em.find(Request.class, requestId);
			if (!inContainer)
				r = unwrapRequest(r);
		} catch (Exception ex) {
            throw new EJBException(ex);
        }
		return r;
	}
	
	public void updateRequest(Request request){
		try{
			em.merge(request);
		} catch (Exception ex) {
            throw new EJBException(ex);
        }
	}

	@SuppressWarnings("unchecked")
	public List<Sacrifice> getSacrificesByKitchen(long kitchenId, boolean inContainer) {
		List<Sacrifice> sacrifices = null;
		
		try {
            Kitchen kitchen = em.find(Kitchen.class, kitchenId);
            sacrifices = (List<Sacrifice>) em.createNamedQuery(
                        "kitchen.entity.Sacrifice.findSacrificesByKitchen")
                                       .setParameter("kitchen", kitchen)
                                       .getResultList();
            if (!inContainer)
            	sacrifices = unwrapSacrifices(sacrifices);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
		
		return sacrifices;
	}
	
	public Sacrifice getSacrifice(long sacrificeId, boolean inContainer) {
		Sacrifice s = null;
		try{
			s = em.find(Sacrifice.class, sacrificeId);
			if (!inContainer)
				s = unwrapSacrifice(s);
		} catch (Exception ex) {
            throw new EJBException(ex);
        }
		return s;
	}

	@SuppressWarnings("unchecked")
	public List<RequestSacrifice> getRequestSacrificesByRequest(long requestId, boolean inContainer) {
		List<RequestSacrifice> requestSacrifices = null;
		
		try{
			Request request = em.find(Request.class, requestId);
			requestSacrifices = (List<RequestSacrifice>) em.createNamedQuery(
						"kitchen.entity.RequestSacrifice.findRequestSacrificesByRequest")
										.setParameter("request", request)
										.getResultList();
			requestSacrifices = sortRequestSacrificesByNetVotes(requestSacrifices);
			if (!inContainer)
				requestSacrifices = unwrapRequestSacrifices(requestSacrifices);
		} catch (Exception ex) {
			throw new EJBException(ex);
		}
		
		return requestSacrifices;
	}
	
	public RequestSacrifice getRequestSacrifice(long requestSacrificeId, boolean inContainer){
		RequestSacrifice rs = null;
		try{
			rs = em.find(RequestSacrifice.class, requestSacrificeId);
			if (!inContainer)
				rs = unwrapRequestSacrifice(rs);
		} catch (Exception ex) {
			throw new EJBException(ex);
		}
		return rs;
	}
	
	public void updateRequestSacrifice(RequestSacrifice rs){
		try{
			em.merge(rs);
		} catch (Exception ex) {
            throw new EJBException(ex);
        }
	}
	
	public void updateSacrifice(Sacrifice s){
		try{
			em.merge(s);
		} catch (Exception ex) {
            throw new EJBException(ex);
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Vote> getAllVotes(boolean inContainer) {
        List<Vote> votes = null;

        try {
            votes = (List<Vote>) em.createNamedQuery(
                        "kitchen.entity.Vote.findAllVotes")
                                       .getResultList();
            if (!inContainer)
            	votes = unwrapVotes(votes);

            return votes;
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
	}
	
	private List<Request> sortRequestsByNetVotes(List<Request> requests){
		Collections.sort(requests, new RequestComparator());
		return requests;
	}
	
	private List<RequestSacrifice> sortRequestSacrificesByNetVotes(List<RequestSacrifice> rs){
		Collections.sort(rs, new RequestSacrificeComparator());
		return rs;
	}
	
	private List<Kitchen> unwrapKitchens(List<Kitchen> k){
		List<Kitchen> r = new ArrayList<Kitchen>();
		
		for (int i = 0; i < k.size(); i++){
			r.add(new Kitchen(k.get(i)));
		}
		
		return r;
		
	}
	
	private Kitchen unwrapKitchen(Kitchen k){
		Kitchen m = new Kitchen(k);
		return m;
	}
	
	private List<Request> unwrapRequests(List<Request> r){
		List<Request> q = new ArrayList<Request>();
		
		for (int i=0; i < r.size(); i++){
			q.add(new Request(r.get(i)));
		}
		
		return q;
	}
	
	private Request unwrapRequest(Request r){
		return new Request(r);
	}
	
	private List<Sacrifice> unwrapSacrifices(List<Sacrifice> s){
		List<Sacrifice> t = new ArrayList<Sacrifice>();
		
		for (int i=0;i<s.size();i++){
			t.add(new Sacrifice(s.get(i)));
		}
		return t;
	}
	
	private Sacrifice unwrapSacrifice(Sacrifice s){
		return new Sacrifice(s);
	}
	
	private List<RequestSacrifice> unwrapRequestSacrifices(List<RequestSacrifice> rs){
		List<RequestSacrifice> r = new ArrayList<RequestSacrifice>();
		
		for (int i=0; i<rs.size(); i++){
			r.add(new RequestSacrifice(rs.get(i)));
		}
		
		return r;
	}
	
	private RequestSacrifice unwrapRequestSacrifice(RequestSacrifice rs){
		return new RequestSacrifice(rs);
	}
	
	private List<Vote> unwrapVotes(List<Vote> votes){
		List<Vote> v = new ArrayList<Vote>();
		
		for (int i=0;i<votes.size();i++){
			v.add(new Vote(votes.get(i)));
		}
		
		return v;
	}

}