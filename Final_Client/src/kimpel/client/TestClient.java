package kimpel.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;

import kitchen.entity.Kitchen;
import kitchen.manager.Manager;
import kitchen.manager.ManagerBean;

public class TestClient{
	
	private static Manager manager;
	
	public static void main(String [] args) {
		
		manager = doLookup();
		
		System.out.println("Lookup succeeded!");
		
		//insertInfo();
		
		lookupInfo();
		
	}
	
	private static void insertInfo(){
		
		try{
			manager.createKitchen(new Kitchen("1", "14th Floor Kitchen"));
		
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