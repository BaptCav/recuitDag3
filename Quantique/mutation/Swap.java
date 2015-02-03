package mutation;

import java.util.Collections;

import modele.Etat;
import modele.Probleme;
import modele.Routage;

public class Swap implements IMutation {


	public void faire(Routage r) {
		// TODO Auto-generated method stub

	}


	public double calculer(Routage r) {
		// TODO Auto-generated method stub
		return 0;
	}


	public static void faire(Routage r , int i,int j){

		Collections.swap(r.getRoute(),i,j);
	}

	@Override
	public void faire(Probleme p) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public double calculer(Probleme p) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double calculer(Probleme p, Etat e) {
		// TODO Auto-generated method stub
		return 0;
	}




}
