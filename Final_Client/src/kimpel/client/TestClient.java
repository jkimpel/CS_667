package kimpel.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;

import kitchen.entity.Kitchen;
import kitchen.entity.Request;
import kitchen.entity.RequestSacrifice;
import kitchen.entity.Sacrifice;
import kitchen.entity.Vote;
import kitchen.manager.Manager;
import kitchen.manager.ManagerBean;

public class TestClient{
	
	private static Manager manager;
	
	public static void main(String [] args) {
		
		manager = doLookup();
		
		System.out.println("Lookup succeeded!");
		
		createKitchens();
		
		createRequests();
		
		createSacrifices();
		
		createRequestSacrifices();
		
		createVotes();
		
		//insertInfo();
		
		lookupInfo();
		
	}
	
	private static void createKitchens(){
		try{
			manager.createKitchen(new Kitchen("14th Floor Kitchen"));
			manager.createKitchen(new Kitchen("11th Floor Kitchen"));
		} catch (Exception ex) {
			System.err.println("Caught an exception:");
			ex.printStackTrace();
		}
		
	}
	
	private static void createRequests(){
		try{
			List<Kitchen> kitchens = manager.getAllKitchens();
			Request r = new Request();
			r.setName("Beef Jerky");
			r.setDescription("High Protein!");
			manager.createRequestInKitchen(r, kitchens.get(0).getId());
			r = new Request();
			r.setName("Almond Butter");
			r.setDescription("High Protein!");
			manager.createRequestInKitchen(r, kitchens.get(0).getId());
			r = new Request();
			r.setName("Jax");
			r.setDescription("Cheesy!");
			manager.createRequestInKitchen(r, kitchens.get(1).getId());
		} catch (Exception ex) {
			System.err.println("Caught an exception:");
			ex.printStackTrace();
		}
		
	}
	
	private static void createSacrifices(){
		try{
			List<Kitchen> kitchens = manager.getAllKitchens();
			Sacrifice s = new Sacrifice();
			s.setName("Twinkies");
			s.setDescription("Gross.");
			manager.createSacrificeInKitchen(s, kitchens.get(0).getId());
			s = new Sacrifice();
			s.setName("English Muffins");
			s.setDescription("Too messy.");
			manager.createSacrificeInKitchen(s, kitchens.get(1).getId());
		} catch (Exception ex) {
			System.err.println("Caught an exception:");
			ex.printStackTrace();
		}
	}
	
	private static void createRequestSacrifices(){
		try{
			List<Kitchen> kitchens = manager.getAllKitchens();
			List<Request> r1 = manager.getRequestsByKitchen(kitchens.get(0).getId());
			List<Request> r2 = manager.getRequestsByKitchen(kitchens.get(1).getId());
			List<Sacrifice> s1 = manager.getSacrificesByKitchen(kitchens.get(0).getId());
			List<Sacrifice> s2 = manager.getSacrificesByKitchen(kitchens.get(1).getId());
			RequestSacrifice rs = new RequestSacrifice();
			manager.createRequestSacrifice(rs, r1.get(0).getId(), s1.get(0).getId());
			rs = new RequestSacrifice();
			manager.createRequestSacrifice(rs, r2.get(0).getId(), s2.get(0).getId());
			
		} catch (Exception ex) {
			System.err.println("Caught an exception:");
			ex.printStackTrace();
		}
		
	}
	
	private static void createVotes(){
		try{
			List<Kitchen> kitchens = manager.getAllKitchens();
			List<Request> r1 = manager.getRequestsByKitchen(kitchens.get(0).getId());
			List<Request> r2 = manager.getRequestsByKitchen(kitchens.get(1).getId());
			List<RequestSacrifice> rs1 = manager.getRequestSacrificesByRequest(r1.get(0).getId());
			List<RequestSacrifice> rs2 = manager.getRequestSacrificesByRequest(r2.get(0).getId());
			Vote v = new Vote();
			v.setValue(1);
			manager.createVoteForRequest(v, r1.get(0).getId());
			v = new Vote();
			v.setValue(-1);
			manager.createVoteForRequest(v, r2.get(0).getId());
			v = new Vote();
			v.setValue(1);
			manager.createVoteForRequestSacrifice(v, rs1.get(0).getId());
			v = new Vote();
			v.setValue(-1);
			manager.createVoteForRequestSacrifice(v, rs2.get(0).getId());
			
		} catch (Exception ex) {
			System.err.println("Caught an exception:");
			ex.printStackTrace();
		}
		
	}
	
	private static void insertInfo(){
		
		try{
			//manager.createKitchen(new Kitchen("3", "4th Floor Kitchen"));
			
			/*Request r = new Request();
			r.setId("4");
			r.setName("Beef Jerky");
			r.setDescription("High Protein!");
			manager.createRequestInKitchen(r, "1");
			r = new Request();
			r.setId("5");
			r.setName("Lettuce");
			r.setDescription("I hate meat!");
			manager.createRequestInKitchen(r, "2");*/
			
			/*Sacrifice s = new Sacrifice();
			s.setId("2");
			s.setName("Twinkies");
			s.setDescription("Gross.");
			manager.createSacrificeInKitchen(s, "1");
			s = new Sacrifice();
			s.setId("3");
			s.setName("English Muffins");
			s.setDescription("Too messy.");
			manager.createSacrificeInKitchen(s, "2");*/
			
			/*RequestSacrifice rs = new RequestSacrifice();
			rs.setId("2");
			manager.createRequestSacrifice(rs, "4", "0");
			rs = new RequestSacrifice();
			rs.setId("3");
			manager.createRequestSacrifice(rs, "5", "1");*/
			
			/*Vote v = new Vote();
			v.setValue(1);
			manager.createVoteForRequest(v, "0");
			v = new Vote();
			v.setValue(-1);
			manager.createVoteForRequest(v, "4");
			v = new Vote();
			v.setValue(1);
			manager.createVoteForRequestSacrifice(v, "2");
			v = new Vote();
			v.setValue(-1);
			manager.createVoteForRequestSacrifice(v, "3");*/
			
			
		
		} catch (Exception ex) {
			System.err.println("Caught an exception:");
			ex.printStackTrace();
		}
	}
	
	private static void lookupInfo(){
		
		try{
			List<Kitchen> kitchens = manager.getAllKitchens();
			System.out.println("All Kitchens:");
			for (int i = 0; i < kitchens.size(); i++){
				Kitchen k = (Kitchen)kitchens.get(i);
				System.out.println(k.getId() + " : " + k.getName());
				List<Request> requests = manager.getRequestsByKitchen(k.getId());
				System.out.println("\t" + k.getName() + "'s Requests:");
				for (int j = 0; j < requests.size(); j++){
					Request r = (Request)requests.get(j);
					System.out.println("\t\t" + r.getId() + " : " + r.getName() + " : " + r.getDescription());
					System.out.println("\t\t" + r.getName() + "'s proposed sacrifices:");
					List<RequestSacrifice> rss = manager.getRequestSacrificesByRequest(r.getId());
					for (int m=0;m<rss.size();m++){
						RequestSacrifice rs = (RequestSacrifice)rss.get(m);
						System.out.println("\t\t\t" + rs.getSacrifice().getName());
					}
				}
				List<Sacrifice> sacrifices = manager.getSacrificesByKitchen(k.getId());
				System.out.println("\t" + k.getName() + "'s Sacrifices:");
				for (int l = 0; l < sacrifices.size(); l++){
					Sacrifice s = (Sacrifice)sacrifices.get(l);
					System.out.println("\t\t" + s.getId() + " : " + s.getName() + " : " + s.getDescription());
				}	
			}
			
			List<Vote> votes = manager.getAllVotes();
			System.out.println("All Votes");
			for (int i = 0; i < votes.size(); i++){
				Vote v = (Vote)votes.get(i);
				if (v.getRequest() != null){
					System.out.println("Vote " + v.getId() + " of " + v.getValue() + 
							" for " + v.getRequest().getName() + " at " + v.getTime());
				} else{
					System.out.println("Vote " + v.getId() + " of " + v.getValue() + 
							" for " + v.getRequestSacrifice().getRequest().getName() + ":" 
							+ v.getRequestSacrifice().getSacrifice().getName() + " at " + v.getTime());
				}
			}
		
		} catch (Exception ex) {
			System.err.println("Caught an exception:");
			ex.printStackTrace();
		}
	}
	
	private static Manager doLookup(){
        Context context = null;
        Manager bean = null;
        try {
            // 1. Obtaining Context
            context = ClientUtility.getInitialContext();
            // 2. Generate JNDI Lookup name
            String lookupName = getLookupName();
            
           
            
            // 3. Lookup and cast
            bean = (Manager) context.lookup(lookupName);
 
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return bean;
	}
	
    private static String getLookupName() {
/*
The app name is the EAR name of the deployed EJB without .ear suffix.
Since we haven't deployed the application as a .ear,
the app name for us will be an empty string
*/
        String appName = "";
 
        /* The module name is the JAR name of the deployed EJB
        without the .jar suffix.
        */
        String moduleName = "Final_Project_Beans_Kimpel";
 
/*AS7 allows each deployment to have an (optional) distinct name.
This can be an empty string if distinct name is not specified.
*/
        String distinctName = "";
 
        // The EJB bean implementation class name
        String beanName = ManagerBean.class.getSimpleName();
 
        // Fully qualified remote interface name
        final String interfaceName = Manager.class.getName();
 
        // Create a look up string name
        String name = "ejb:" + appName + "/" + moduleName + "/" +
            distinctName    + "/" + beanName + "!" + interfaceName + 
            "?stateless";
        
        System.out.println("Lookup Name: " + name);
 
        return name;
    }
	
}