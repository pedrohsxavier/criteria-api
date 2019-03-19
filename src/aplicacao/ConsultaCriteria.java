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
		 * Uma consulta tipada n�o permite que erros de sintaxe sejam cometidos, como trocar
		 * 'select' por 'seletc' ou 'from' por 'form', tamb�m seria poss�vel facilmente
		 * trocar por exemplo a classe Departamento por Setor, pois a IDE alteraria o 
		 * c�digo sem grandes problemas. Os erros seriam exibidos em tempo de compila��o
		 * e n�o de execu��o, permitindo construir consultas de forma program�tica.
		 */
	
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("empresa-eclipselink");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		//Criando o objeto criteriaBuilder
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		//Criando a query passando a classe departamento como par�metro e definindo que o retorno ser� departamento
		CriteriaQuery<Departamento> createQuery = criteriaBuilder.createQuery(Departamento.class);
		
		//Root representa a raiz da consulta, equivale a 'from dept d' em JPQL
		//Daqui � poss�vel acessar os atributos da entidade
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
