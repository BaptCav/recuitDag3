package modele;
import java.util.ArrayList;
import java.util.Collections;

import parametrage.Temperature;

public class Particule {
 ArrayList<Etat> etat;
 Temperature T;
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
 public void setEtat( ArrayList<Etat> e){
	 this.etat= e;
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
	
	public void shuffle(){
		ArrayList<Etat> r = this.etat;
		Collections.shuffle(r);
		this.setEtat(r);
	}
}
