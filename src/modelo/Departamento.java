package modelo;

import java.util.*;
import javax.persistence.*;

@Entity
public class Departamento  {
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int id;
	private String nome;
	
	@OneToMany(mappedBy="dep", cascade=CascadeType.ALL, fetch=FetchType.LAZY )  
	private List<Empregado> empregados = new ArrayList<Empregado>();   
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Empregado chefe;

	public Departamento() {}
	public Departamento(String nome) {
		this.nome = nome;
	}
	public int getId() {
		return id;
	}
	public void setId(int cod) {
		this.id = cod;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void adicionarEmpregado(Empregado emp) {
		empregados.add(emp);
		emp.setDepartamento(this);
	}
	public void removerEmpregado(Empregado emp) {
		empregados.remove(emp);
		emp.setDepartamento(null);
	}
	public List<Empregado> getEmpregados() {
		return empregados;
	}

	public Empregado getChefe() {
		return chefe;
	}
	public void setChefe(Empregado chefe) {
		this.chefe = chefe;
	}


	public String toString(){
		// a lista empregados está com fetch=LAZY
		return 
				"id:" + getId() + " , Nome:" + getNome()  +  
				", Chefe:" + ((chefe!=null) ? chefe.getNome() :"") ;
				//"\n Empregados:" + ((empregados.size()>0) ? empregados : "");
	}
}