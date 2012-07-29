package kitchen.manager;

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

	public void createRequest(Request request) {
		try {
            em.persist(request);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
	}

	public void createSacrifice(Sacrifice sacrifice) {
		try {
            em.persist(sacrifice);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
		
	}

	public void createRequestSacrifice(RequestSacrifice requestSacrifice) {
		try {
            em.persist(requestSacrifice);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
		
	}

	public void createVote(Vote vote) {
		try {
            em.persist(vote);
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

            return kitchens;
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
	}

	@SuppressWarnings("unchecked")
	public List<Request> getRequestsByKitchen(String kitchenId) {
		List<Request> requests = null;
		
		try {
            Kitchen kitchen = em.find(Kitchen.class, kitchenId);
            requests = (List<Request>) em.createNamedQuery(
                        "kitchen.entity.Request.findRequestsByKitchen")
                                       .setParameter("kitchen", kitchen)
                                       .getResultList();
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
		
		return requests;
	}

	@SuppressWarnings("unchecked")
	public List<Sacrifice> getSacrificesByKitchen(String kitchenId) {
		List<Sacrifice> sacrifices = null;
		
		try {
            Kitchen kitchen = em.find(Kitchen.class, kitchenId);
            sacrifices = (List<Sacrifice>) em.createNamedQuery(
                        "kitchen.entity.Sacrifice.findSacrificesByKitchen")
                                       .setParameter("kitchen", kitchen)
                                       .getResultList();
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
		
		return sacrifices;
	}

	@SuppressWarnings("unchecked")
	public List<RequestSacrifice> getRequestSacrificesByRequest(String requestId) {
		List<RequestSacrifice> requestSacrifices = null;
		
		try{
			Request request = em.find(Request.class, requestId);
			requestSacrifices = (List<RequestSacrifice>) em.createNamedQuery(
						"kitchen.entity.RequestSacrifice.findRequestSacrificeByRequest")
										.setParameter("request", request)
										.getResultList();
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

            return votes;
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
	}
	

}