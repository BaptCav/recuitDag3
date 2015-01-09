package modele;
import java.util.ArrayList;

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
}
