package tsp;

import java.util.Collections;

public class Swap {
	
	public static void faire(Routage r , int i,int j){

		Collections.swap(r.getRoute(),i,j);
	}

}