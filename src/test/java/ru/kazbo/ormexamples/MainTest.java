package ru.kazbo.ormexamples;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.List;
import ru.kazbo.ormexamples.model.Person;

@TestMethodOrder(OrderAnnotation.class)
public class MainTest {
	
	static PersonsDAO dao;
	static Person testPerson;
	
	@BeforeAll
	static void startDatabase() throws IOException, InterruptedException {
		PostgresDB.execPostgres(true);
	}
	
	@Test
	@Order(1)
	void initializePersonsDAO() {
		dao = new PersonsDAO("postgresql_unit");
	}
	
	@Test
	@Order(2)
	void addPerson() {
		dao.addPerson("Alex", "alex@gmail.com", 25);
		testPerson = dao.getPersonByName("Alex");
		assertEquals("[name = \"Alex\", email = \"alex@gmail.com\", age = \"25\", id = \"1\"]", testPerson.toString());
	}
	
	@Test
	@Order(3)
	void renamePerson() {
		dao.renamePersonById(testPerson.getId(), "Max");
		testPerson = dao.getPersonByName("Max");
		assertEquals("[name = \"Max\", email = \"alex@gmail.com\", age = \"25\", id = \"1\"]", testPerson.toString());
	}
	
	@Test
	@Order(4)
	void removePersonByName() {
		dao.removePersonByName("Max");
		List<Person> emptyPersons = dao.getPersonsList(0);
		assertEquals(emptyPersons.size(), 0);
	}
	
	@AfterAll
	static void stopDatabase() throws InterruptedException, IOException {
		PostgresDB.execPostgres(false);
	}
}