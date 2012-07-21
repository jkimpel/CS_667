package kimpel;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Action{
	
	private String choice;
	private static ArrayList<ProductBean> products;
	
	public Action(){
		products = new ArrayList<ProductBean>();
		products.add(new ProductBean("Pizza", 10.50));
		products.add(new ProductBean("Burrito", 8.50));
		products.add(new ProductBean("Hamburger", 6.50));
		products.add(new ProductBean("Grilled Cheese", 4.50));
		products.add(new ProductBean("Hot Dog", 3.50));	
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}
	
	public ArrayList<ProductBean> getProducts(){
		return products;	
	}
	
	public String navigate(){
		if ((choice == null) || (choice.isEmpty()))
			return "CustomerInfo";
		else
			return choice;
	}
	
}