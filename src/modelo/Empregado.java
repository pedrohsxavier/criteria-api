package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity 

public class Empregado {
	@Id		
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int id;
	private String nome;
	private double salario; 
	@ManyToOne
	private Departamento dep;


	public Empregado (){}
	public Empregado (String n, double s){
		nome=n;
		salario=s;
	}
	public int getId() 	{
		return id;
	}

	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Departamento getDepartamento() {
		return dep;
	}
	public void setDepartamento(Departamento d) {
		this.dep = d;
	}
	@Override
	public String toString() {
		return "Empregado [id=" + id + ", " + (nome != null ? "nome=" + nome + ", " : "") + "salario=" + salario + ", "
				+ (dep != null ? "dep=" + dep.getNome() : " sem departamento") ;
	}

	

}
