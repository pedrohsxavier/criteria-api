package modelo;

import javax.persistence.*;

@Entity
public class Gerente extends Empregado{
	private double extra; //10% do salario
	
	public Gerente() {
		super();
	}
	public Gerente(String n, double s) {
		super(n, s);
		extra = super.getSalario()/10;
	}
	public double getExtra() {
		return extra;
	}
	public void setExtra(double e) {
		extra=e;
	}
	public double getSalario() {
		return super.getSalario() + getExtra();
	}
	public String toString() {
		return super.toString() + " , extra:" + getExtra();
	}
}
