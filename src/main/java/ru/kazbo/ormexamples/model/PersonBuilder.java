package ru.kazbo.ormexamples.model;

public class PersonBuilder {
	
	private String name, email;
	private Integer age = 18;
	
	public PersonBuilder(String name) {
		this.name = name;
	}
	
	public PersonBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public PersonBuilder setAge(Integer age) {
		this.age = age;
		return this;
	}
	
	public Person build() {
		var person = new Person();
		person.setEmail(email);
		person.setAge(age);
		person.setName(name);
		return person;
	}
}