package modelo;

import javax.persistence.*;

@Entity
public class Vendedor extends Empregado{
	private double comissao;

	public Vendedor() {
		super();
	}
	public Vendedor(String n, double s) {
		super( n, s);
		comissao=0.0;
	}
	public double getTotalVendas() {
		return comissao;
	}
	public void setTotalVendas(double totalvendas) {
		this.comissao = totalvendas;
	}
	public void Vender(double valor) {
		 comissao += valor;
	}
	public double getSalario() {
		return super.getSalario() + getTotalVendas()/10;
	}

	public String toString() {
		return super.toString() + " , total vendas:" + getTotalVendas();
	}
}
