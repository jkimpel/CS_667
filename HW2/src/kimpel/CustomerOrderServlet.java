package kimpel;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kimpel.beans.CustomerInfo;
import kimpel.beans.ProductBean;

/**
 * Servlet implementation class CustomerOrderServlet
 * 
 * This servlet handles Customer Actions
 * 
 * Joe Kimpel CS667
 * 
 * 6.24.2012
 * 
 */
@WebServlet("/CustomerOrderServlet")
public class CustomerOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerOrderServlet() {
        super();
    }

    public void init() throws ServletException{
    	super.init();
    	ServletContext application = getServletContext();
    	ArrayList<ProductBean> products = new ArrayList<ProductBean>();
    	products.add(new ProductBean("Pizza", 10.50));
    	products.add(new ProductBean("Burrito", 8.50));
    	products.add(new ProductBean("Hamburger", 6.50));
    	products.add(new ProductBean("Grilled Cheese", 4.50));
    	products.add(new ProductBean("Hot Dog", 3.50));
    	application.setAttribute("products", products);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		CustomerInfo cust = (CustomerInfo) session.getAttribute("cust");
		String dest = "/index.html";
		if (cust != null){
			dest = "/jsp/CustomerInfo.jsp";
			String op = request.getParameter("operation");
			if (op != null){
				if (op.equals("order"))
					dest = "/jsp/order.jsp";
				if (op.equals("balance"))
					dest = "/jsp/balance.jsp";
				if (op.equals("payment"))
					dest = "/jsp/payment.jsp";
			}	
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(dest);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
