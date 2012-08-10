package kitchen.util;

/*
 * 	Joe Kimpel
 * 	CS 667 - Final Project
 * 	8.10.2012
 * 
 *	RequestSacrificeComparator.java
 *	This class is used to sort a list of RequestSacrifices.
 * 
 */

import java.util.Comparator;

import kitchen.entity.RequestSacrifice;

public class RequestSacrificeComparator implements Comparator<RequestSacrifice>{
	
	@Override
	public int compare(RequestSacrifice rs1, RequestSacrifice rs2){
		return (rs2.netVotes() - rs1.netVotes());
	}
}