package kitchen.util;

/*
 * 	Joe Kimpel
 * 	CS 667 - Final Project
 * 	8.10.2012
 * 
 *	RequestComparator.java
 *	This class is used to sort a list of Requests.
 * 
 */

import java.util.Comparator;

import kitchen.entity.Request;

public class RequestComparator implements Comparator<Request>{
	@Override
	public int compare(Request r1, Request r2) {
		return r2.netVotes() - r1.netVotes();
	}	
}