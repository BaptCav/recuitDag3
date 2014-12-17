package mutation;

import java.util.Collections;

import modele.Etat;
import modele.Routage;

public class Swap implements IMutation {
	int i;
	int j;

	public Swap(int i, int j){
		this.i=i;
		this.j=j;
	}
	@Override
	public double calculer(Etat e) {
		// TODO Auto-generated method stub
		return 0;
	}


	public void faire(Etat e){
		Routage r = (Routage) e;
		Collections.swap(r.getRoute(),this.i,this.j);
	}




}
