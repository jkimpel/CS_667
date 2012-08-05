package kitchen.util;

import java.util.Comparator;

import kitchen.entity.RequestSacrifice;

public class RequestSacrificeComparator implements Comparator<RequestSacrifice>{
	
	@Override
	public int compare(RequestSacrifice rs1, RequestSacrifice rs2){
		return (rs2.netVotes() - rs1.netVotes());
	}
}