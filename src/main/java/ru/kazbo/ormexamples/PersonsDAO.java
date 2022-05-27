package ru.kazbo.ormexamples;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import ru.kazbo.ormexamples.model.*;

public class PersonsDAO {
	
	private EntityManager manager;
	
	public PersonsDAO(String unitName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(unitName);
        manager = emf.createEntityManager();
	}
	
	public EntityManager getEntityManager() {
		return manager;
	}

	public void renamePersonById(Long id, String newName) {
		startTransaction(manager -> {
			Person p = getPersonById(id);
			p.setName(newName);
			manager.persist(p);
		});
	}
	
	public void renamePersonByName(String name, String newName) {
		startTransaction(manager -> {
			Person p = getPersonByName(name);
			p.setName(newName);
			manager.persist(p);
		});
	}
	
	public List<Person> getPersonsList(int maxResults) {
			Query personsQuery = manager.createQuery("SELECT p FROM persons p");
			if(maxResults != 0)
				personsQuery.setMaxResults(maxResults);
			return personsQuery.getResultList();
	}
	
	public void addPerson(String personName, String personEmail, Integer age) {
		startTransaction(m -> m.persist(createPerson(personName, personEmail, age)));
	}
	
	public void removePersonByName(String personName) {
		startTransaction(m -> {
			Query removeQuery = m.createQuery("DELETE FROM persons p WHERE p.name=:name");
			removeQuery.setParameter("name", personName);
			removeQuery.executeUpdate();
		});
	}
	
	public Person getPersonById(Long id) {
		return (Person) manager.find(Person.class, id);
	}
	
	public Person getPersonByName(String name) {
		Person person = (Person) manager
				.createQuery("SELECT p FROM persons p WHERE p.name=:name")
				.setParameter("name", name)
				.getSingleResult();
		return person;
	}
	
	private Person createPerson(String name, String email, Integer age) {
		return new PersonBuilder(name).setEmail(email).setAge(age).build();
	}
	
	/*
	 * Т.к. в Java SE нету аннотаций для транзакций, используем следующий
	 * костыль:
	 */
	private void startTransaction(TransactionInterface transactionInterface) {
		try {
			manager.getTransaction().begin();
			transactionInterface.startTransaction(manager);
			manager.getTransaction().commit();
		} catch(PersistenceException e) {
			EntityTransaction trans = getEntityManager().getTransaction();
			if(trans.isActive()) trans.rollback();
			throw e;
		}
	}
	
	/*
	 * Интерфейс для работы с транзакциями,
	 * когда мы записываем/удаляем/изменяем объект
	 * в БД.
	 */
	private interface TransactionInterface {
		public void startTransaction(EntityManager manager);
	}
}