package mutation;

import java.util.Collections;

import modele.Routage;

public class Swap implements IMutation {

	@Override
	public void faire(Routage r) {
		// TODO Auto-generated method stub

	}

	@Override
	public double calculer(Routage r) {
		// TODO Auto-generated method stub
		return 0;
	}


	public static void faire(Routage r , int i,int j){

		Collections.swap(r.getRoute(),i,j);
	}




}
