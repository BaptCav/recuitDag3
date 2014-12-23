package modele;
import java.util.ArrayList;

import parametrage.ParametreT;

public class Particule {
 ArrayList<Etat> etat;
 ParametreT T;
 public ParametreT getT(){
	 return this.T;
 }
 public void setT(ParametreT t){
	  this.T=t;
 }
 
 public ArrayList<Etat> getEtat(){
	 return this.etat;
 }
 public void setEtat( ArrayList<Etat> e){
	 this.etat= e;
 }
}
