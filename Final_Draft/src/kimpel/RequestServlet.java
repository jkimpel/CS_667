package kimpel;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kimpel.beans.RequestBean;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 */
@WebServlet("/RequestServlet")
public class RequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, RequestBean> requests;
	private HashMap<String, RequestBean> removals;
       
    public RequestServlet() {
        super();
    }
    
    public void init() throws ServletException{
    	super.init();
    	ServletContext application = getServletContext();
    	requests = new HashMap<String, RequestBean>();
    	removals = new HashMap<String, RequestBean>();
    	
    	//TODO pull this from the DB
    	requests.put("Walnuts", new RequestBean("Walnuts"));
    	requests.put("Triscuits", new RequestBean("Triscuits"));
    	requests.put("Corn Pops", new RequestBean("Corn Pops"));
    	requests.put("Bugles", new RequestBean("Bugles"));
    	requests.put("Goat Milk", new RequestBean("Goat Milk"));
    	
    	removals.put("Walnuts>Almonds", new RequestBean("Almonds","Walnuts"));
    	removals.put("Triscuits>Wheat Thins", new RequestBean("Wheat Thins","Triscuits"));
    	removals.put("Corn Pops>Cheerios", new RequestBean("Cheerios","Corn Pops"));
    	removals.put("Bugles>Chex Mix", new RequestBean("Chex Mix","Bugles"));
    	removals.put("Goat Milk>Soy Milk", new RequestBean("Soy Milk","Goat Milk"));
    	
    	application.setAttribute("requests", requests);
    	application.setAttribute("removals", removals);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = request.getParameter("op");
		if ((op == null) || op.isEmpty()){
			//do nothing
		} else if (op.equals("new")){
			RequestBean req = new RequestBean();
			try {
				BeanUtils.populate(req, request.getParameterMap());
			} catch (IllegalAccessException e) {
				//We are checking the state of the bean after, so no action is necessary
			} catch (InvocationTargetException e) {
				//We are checking the state of the bean after, so no action is necessary
			}
			
			//is it valid?
			boolean valid = req.isValid();
			
			//is it unique?
			boolean unique;
			if (valid){
				synchronized(requests){
					unique = isUnique(req);
					if (unique && valid)
						requests.put(req.getName(), req);
				}
			}
		} else if (op.equals("remove")){
			RequestBean req = new RequestBean();
			try {
				BeanUtils.populate(req, request.getParameterMap());
			} catch (IllegalAccessException e) {
				//We are checking the state of the bean after, so no action is necessary
			} catch (InvocationTargetException e) {
				//We are checking the state of the bean after, so no action is necessary
			}
			
			//is it a valid removal RequestBean?
			boolean valid = req.isValidRemoval() && requests.containsKey(req.getReplacedBy());
			
			//is it unique?
			boolean unique;
			if (valid){
				synchronized(removals){
					unique = isUniqueRemoval(req);
					if (unique && valid)
						removals.put(req.getReplacedBy() + ">" + req.getName(), req);
				}
			}
			
		}else if (op.equals("plus")){
			String name = request.getParameter("name");
			if ((name != null) && (!name.isEmpty())){
				RequestBean req = requests.get(name);
				if (req != null){
					synchronized(req){
						req.setPlusses(req.getPlusses() + 1);
					}
				}
			}
		} else if (op.equals("minus")){
			String name = request.getParameter("name");
			if ((name != null) && (!name.isEmpty())){
				RequestBean req = requests.get(name);
				if (req != null){
					synchronized(req){
						req.setMinusses(req.getMinusses() + 1);
					}
				}
			}
		} else if (op.equals("remplus")){
			String name = request.getParameter("name");
			String replacedBy = request.getParameter("replacedBy");
			if ((name != null) && (!name.isEmpty()) && 
					(replacedBy != null) && (!replacedBy.isEmpty())){
				RequestBean rem = removals.get(replacedBy + ">" + name);
				if (rem != null){
					synchronized(rem){
						rem.setPlusses(rem.getPlusses() + 1);
					}
				}
			}
		} else if (op.equals("remminus")){
			String name = request.getParameter("name");
			String replacedBy = request.getParameter("replacedBy");
			if ((name != null) && (!name.isEmpty()) && 
					(replacedBy != null) && (!replacedBy.isEmpty())){
				RequestBean rem = removals.get(replacedBy + ">" + name);
				if (rem != null){
					synchronized(rem){
						rem.setMinusses(rem.getMinusses() + 1);
					}
				}
			}
		} else if (op.equals("freeze")){
			//TODO Authentication for admin function
			String name = request.getParameter("name");
			if ((name != null) && (!name.isEmpty())){
				RequestBean req = requests.get(name);
				if (req != null){
					synchronized(req){
						if (req.isFrozen())
							req.setFrozen(false);
						else
							req.setFrozen(true);
					}
				}
			}
			
		}
		
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Requests.jsp");
	    dispatcher.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private boolean isUnique(RequestBean r){
		if (requests.containsKey(r.getName()))
			return false;
		else
			return true;
	}
	
	private boolean isUniqueRemoval(RequestBean r){
		String key = r.getReplacedBy() + ">" + r.getName();
		if (removals.containsKey(key))
			return false;
		else
			return true;
	}

}
