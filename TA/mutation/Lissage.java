package mutation;

import modele.Routage;

public class Lissage implements IMutation {

	public void faire(Routage r) {

		int n = r.tailleRoute();
		for( int i=0; i<n;i++){
			int maxj=0;
			double delta=0;
			for(int j=0;j<n;j++){
				double c=TwoOptMove.calculer(r, i, j);
				if(delta>c){
					delta=c;
					maxj=j;
				}
			}
			TwoOptMove.faire(r, i, maxj);
		}

	}


	@Override
	public double calculer(Routage r) {
		// TODO Auto-generated method stub
		return 0;
	}


}
