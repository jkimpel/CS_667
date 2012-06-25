package kimpel.beans;

/* Simple bean that represents a product
 * 
 * Joe Kimpel CS667
 * 
 * 6.24.2012
 * 
 */

public class ProductBean{
	private static final String DEFAULT_NAME = "";
	private static final double DEFAULT_PRICE = 0.0;
	
	private String name = DEFAULT_NAME;
	private double price = DEFAULT_PRICE;
	
	public ProductBean(){
		
	}
	
	public ProductBean(String n, double p){
		setName(n);
		setPrice(p);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
}