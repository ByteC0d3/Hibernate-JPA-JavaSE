/**
 *	Данный класс является моделью для "маппинга" в базу данных
 *	Грубо говоря, он создаст таблицу persons (Entity и Table)
 *  +------+----+
 *	| name | id |
 *	+------+----+
 */
package ru.kazbo.ormexamples.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity(name="persons") // В Postgres Entity должен быть с маленькой буквы
@Table(name="persons")
public class Person implements Serializable {
	
	/*
	 *	Id будет генерироваться автоматически
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "age", nullable = false)
	private Integer age;
	
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@NotNull
	@Email
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Integer getAge() {
		return age;
	}
	
	@Override
	public String toString() {
		return """
				[name = "%s", email = "%s", age = "%d", id = "%d"]""".formatted(name, email, age, id);
	}
}