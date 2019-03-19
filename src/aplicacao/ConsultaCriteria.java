package aplicacao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;

import modelo.Departamento;

public class ConsultaCriteria {

	public static void main(String[] args) {
		
		/* API Criteria nos permite criar uma consulta estruturada
		 * Uma consulta tipada não permite que erros de sintaxe sejam cometidos, como trocar
		 * 'select' por 'seletc' ou 'from' por 'form', também seria possível facilmente
		 * trocar por exemplo a classe Departamento por Setor, pois a IDE alteraria o 
		 * código sem grandes problemas. Os erros seriam exibidos em tempo de compilação
		 * e não de execução, permitindo construir consultas de forma programática.
		 */
	
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("empresa-eclipselink");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		//Criando o objeto criteriaBuilder
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		//Criando a query passando a classe departamento como parâmetro e definindo que o retorno será departamento
		CriteriaQuery<Departamento> createQuery = criteriaBuilder.createQuery(Departamento.class);
		
		//Root representa a raiz da consulta, equivale a 'from dept d' em JPQL
		//Daqui é possível acessar os atributos da entidade
		Root<Departamento> from = createQuery.from(Departamento.class);
		
		//Declarando o select
		CriteriaQuery<Departamento> select = createQuery.select(from);
		
		//Criando a consulta tipada
		TypedQuery<Departamento> typedQuery = entityManager.createQuery(select);
		
		//Resultado da consulta
		List<Departamento> resultList = typedQuery.getResultList();
		for(Departamento departamento : resultList) {
			
			System.out.println("Id: " + departamento.getId() + "; Departamento: " + departamento.getNome());
		}
		
			
	}

}
