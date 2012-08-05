package kitchen.util;

import java.util.Comparator;

import kitchen.entity.Request;

public class RequestComparator implements Comparator<Request>{
	@Override
	public int compare(Request r1, Request r2) {
		return r2.netVotes() - r1.netVotes();
	}	
}