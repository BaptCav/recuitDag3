package modele;
import java.util.ArrayList;

import parametrage.Temperature;

public class Particule {
	ArrayList<Etat> etat;
	Temperature T;
	
	public Particule(ArrayList<Etat> etat,Temperature T){
		/**
		 * Le Constructeur s'occupe du bouclage lui meme
		 */
		
		this.setEtat(etat);
		this.T=T;
	}
	public Particule(){
		
	}
	
	public Temperature getT(){
		return this.T;
	}
	public void setT(Temperature t){
		this.T=t;
	}

	public void setT(double value){
		Temperature temp = new Temperature(value);
		this.T = temp;
	}

	public ArrayList<Etat> getEtat(){
		return this.etat;
	}
	public int nombreEtat(){
		return etat.size();
	}
	
	public Particule clone(){
		int n = this.etat.size();
		ArrayList<Etat> r = new ArrayList<Etat>(n);
		for(int i=0; i<n; i++){
			r.add(( (this.etat.get(i)).clone()));
		}
		 Particule p = new Particule(r,this.T) ;
		 
		return p;
		
	}
	
	public void setEtat( ArrayList<Etat> e){
		this.etat= e;
		for( int i =1;i< e.size()-1; i++){
			e.get(i).setprevious(e.get(i-1));
			e.get(i).setnext(e.get(i+1));
		}
		e.get(e.size()-1).setprevious( e.get(e.size()-2));
		e.get(e.size()-1).setnext( e.get(0));
		e.get(0).setprevious( e.get(e.size()-1));
		e.get(0).setnext(e.get(1));
	}

	public int getNextIndex(int index){
		if (index==(this.getEtat().size()-1)) {
			return 0;
		} else {
			return (index+1);
		}
	}

	public int getPreviousIndex(int index){
		if (index==0) {
			return (this.getEtat().size() - 1);
		} else {
			return (index-1);
		}
	}
}
