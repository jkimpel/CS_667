package kimpel;

public class HTMLUtilities{
	
	//  Taken from Core Servlets and JavaServer Pages 2nd Edition
	//  from Prentice Hall and Sun Microsystems Press,
	//  http://www.coreservlets.com/.
	//  &copy; 2003 Marty Hall; may be freely used or adapted.
	//
	// Given a string, this method replaces all occurrences of
	//  '<' with '&lt;', all occurrences of '>' with
	//  '&gt;', and (to handle cases that occur inside attribute
	//  values), all occurrences of double quotes with
	//  '&quot;' and all occurrences of '&' with '&amp;'.
	//  Without such filtering, an arbitrary string
	// could not safely be inserted in a Web page.
	public static String filter(String input) {
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
		for(int i=0; i<input.length(); i++) {
			c = input.charAt(i);
			if (c == '<') {
				filtered.append("&lt;");
			} else if (c == '>') {
				filtered.append("&gt;");
			} else if (c == '"') {
				filtered.append("&quot;");
			} else if (c == '&') {
				filtered.append("&amp;");
			} else {
				filtered.append(c);
			}
		}
		return(filtered.toString());
	}
}