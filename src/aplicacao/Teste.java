package aplicacao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import modelo.Departamento;
import modelo.Empregado;
import modelo.Gerente;
import modelo.Vendedor;

public class Teste {
	protected static EntityManager manager;

	public Teste() {
		EntityManagerFactory factory = 
				Persistence.createEntityManagerFactory("empresa-eclipselink");
		manager = factory.createEntityManager();

		cadastrar();
		atualizar();
		consultar();	

		manager.close();
		factory.close();
	}


	public void cadastrar() {
		Empregado e1,e2,e3,e4,e5;
		Departamento d1,d2,d3,d4;
		e1 = new Empregado("joao",1000);
		e2 = new Empregado("maria",2000);
		e3 = new Empregado("jose",3000);
		e4 = new Gerente("ana",4000);
		e5 = new Vendedor("paulo",4000);	

		manager.getTransaction().begin();	
		d1 = new Departamento("informatica");
		d1.adicionarEmpregado(e1);
		d1.adicionarEmpregado(e2);
		d1.setChefe(e1);
		manager.persist(d1);
		manager.getTransaction().commit();

		manager.getTransaction().begin();	
		d2 = new Departamento("eletronica");			
		d2.adicionarEmpregado(e3);
		d2.adicionarEmpregado(e4);
		d2.adicionarEmpregado(e5);
		d2.setChefe(e3);
		manager.persist(d2);
		manager.getTransaction().commit();

		manager.getTransaction().begin();	
		d3 = new Departamento("matematica");   // 
		d3.setChefe(null);
		manager.persist(d3);
		manager.getTransaction().commit();

		manager.getTransaction().begin();	
		d4 = new Departamento("fisica");   // 
		d4.setChefe(e1);
		manager.persist(d4);
		manager.getTransaction().commit();

		System.out.println("cadastro concluido !");
	}


	public void atualizar() {
		manager.getTransaction().begin();
		System.out.println("\nAUMENTAR SALARIOS");
		Query q = manager.createQuery("UPDATE Empregado e set e.salario = e.salario * 1.10  where e.dep.nome = 'informatica' ");
		int n2 = q.executeUpdate();
		manager.getTransaction().commit();
		System.out.println("salarios alterados="+n2);
	}


	public void consultar() {		
		List<Departamento> deps;
		List<Empregado> emps;

		System.out.println("\nempregados do dep de informatica ordenados decrescente por salario ");
		Query query = manager.createQuery("SELECT e FROM Empregado e where e.dep.nome= 'informatica' order by e.salario desc");
		emps = query.getResultList();
		listarEmpregados(emps);


		System.out.println("\nempregados que ganham mais que o seu chefe ");
		query = manager.createQuery("select e from Empregado e where e.salario > e.dep.chefe.salario");
		emps = query.getResultList();
		listarEmpregados(emps);

		System.out.println("\nempregados do departamento eletronica");
		query = manager.createQuery("select e from Empregado e where e.dep.nome = 'eletronica'");
		emps = query.getResultList();
		listarEmpregados(emps);

		System.out.println("\nempregados do departamento eletronica");
		query = manager.createQuery("select e from Departamento d JOIN d.empregados e where d.nome = 'eletronica'");
		emps = query.getResultList();
		listarEmpregados(emps);

		System.out.println("\ndepartamentos que tem empregados com salario >=2000");
		query = manager.createQuery("select distinct e.dep from Empregado e where e.salario >= 2000" );
		deps = query.getResultList();
		listarDepartamentos(deps);

		System.out.println("\ndepartamentos que tem empregados com salario >=2000");
		query = manager.createQuery("select distinct d from Departamento d JOIN d.empregados e where e.salario >= 2000" );
		deps = query.getResultList();
		listarDepartamentos(deps);


		System.out.println("\ndepartamentos sem empregados ");
		query = manager.createQuery("select d from Departamento d where size(d.empregados) = 0" );
		deps = query.getResultList();
		listarDepartamentos(deps);

		System.out.println("\nempregados que ganham mais que o joao ");
		query = manager.createQuery("select e from Empregado e where e.salario > (select e1.salario from Empregado e1 where e1.nome='joao')");
		emps = query.getResultList();
		listarEmpregados(emps);

		System.out.println("\nGerentes ou Vendedores");
		query = manager.createQuery("select e from Empregado e where TYPE(e) IN (Gerente, Vendedor)");
		emps = query.getResultList();
		listarEmpregados(emps);

		System.out.println("\nTOTAL DE  EMPREGADOS do dep informatica");
		query = manager.createQuery("select count(d.empregados) from Departamento d where d.nome='informatica' ");
		long n1 = (Long) query.getSingleResult();
		System.out.println(n1);

		System.out.println("\ndepartamentos que tem 2 empregados ");
		query = manager.createQuery("select d from Departamento d where SIZE(d.empregados) = 2" );
		deps = query.getResultList();
		listarDepartamentos(deps);

		System.out.println("\ndepartamentos sem chefe ");
		query = manager.createQuery("select d from Departamento d where d.chefe = null" );
		deps = query.getResultList();
		listarDepartamentos(deps);

		System.out.println("\nempregados que ganham mais que o chefe do dep de eletronica ");
		query = manager.createQuery("select e from Empregado e where e.salario > (select d.chefe.salario from Departamento d where d.nome='eletronica')");
		emps = query.getResultList();
		listarEmpregados(emps);

		System.out.println("\nSQL ");
		query= manager.createNativeQuery("select * from Empregado", Empregado.class);
		emps = query.getResultList();
		listarEmpregados(emps);
	}

	private void listarEmpregados(List<Empregado> emps) {
		for (Empregado emp : emps) 
			System.out.println(emp.toString());
	}	
	private void listarDepartamentos(List<Departamento> deps) {
		for (Departamento dep : deps) {
			System.out.println(dep.toString());
			List<Empregado> empregados = (List<Empregado>) dep.getEmpregados();
			System.out.println("  Empregados:");
			for (Empregado emp : empregados) {
				System.out.println("  "+emp.getNome());
			}		
		}
	}	
	
	//==================
	public static void main(String[] args) throws Exception {
		new Teste();
	}
}



















