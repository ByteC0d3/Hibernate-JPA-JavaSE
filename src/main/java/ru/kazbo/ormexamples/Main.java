/**
 *	Содержит главный метод для запуска программы
 */
package ru.kazbo.ormexamples;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import java.util.Scanner;
import java.io.IOException;

public class Main {
	
	private static PersonsDAO dao = null;
	private static boolean isRunningLoop = true;
	
	private static final String COMMANDS_LIST = """
					Список команд:
					/add <имя пользователя> <email> <возвраст> - добавить нового пользователя
					/rm <имя пользователя> - удалить пользователя по имени
					/get-persons <кол-во> - получить список пользователей (введите 0, чтобы получить всех)
					/exit - выйти из программы
					/update-name-by-id <id> <новое имя>
					/update-name-by-name <имя> <новое имя>""";
					
	public static void main(String[] args) throws IOException, InterruptedException {
			initDataBase();
			Scanner sc = new Scanner(System.in);
			System.out.println("Java Persistence API example from Java SE");
			System.out.println(COMMANDS_LIST);
			while(isRunningLoop) {
				try {
					System.out.print("> ");
					checkCommands(sc.nextLine());
				} catch(PersistenceException e) {
					System.out.println("Ошибка при работе с БД: " + e.getMessage());
				}
			}
			PostgresDB.execPostgres(false);	// выключаем базу данных
	}
	
	private static void initDataBase() throws IOException, InterruptedException {
		PostgresDB.execPostgres(true);
		dao = new PersonsDAO("postgresql_unit");
	}
	
	private static void checkCommands(String inputText) {
		String[] command = inputText.split(" ");
		try {
			switch(command[0]) {
				case "/add" -> dao.addPerson(command[1], command[2], Integer.parseInt(command[3]));
				case "/rm" -> dao.removePersonByName(command[1]);
				case "/get-persons" -> dao.getPersonsList(Integer.parseInt(command[1])).forEach(System.out::println);
				case "/exit" -> isRunningLoop = false;
				case "/update-name-by-id" -> dao.renamePersonById(Long.parseLong(command[1]), command[2]);
				case "/update-name-by-name" -> dao.renamePersonByName(command[1], command[2]);
				case "/help" -> System.out.println(COMMANDS_LIST);
			}
		} catch(NumberFormatException exp) {
			System.out.println("Вы ввели число, вместо цифры");
		} catch(ArrayIndexOutOfBoundsException exp) {
			System.out.println("Пропущен один или несколько пар-ров в команде " + command[0]);
		} catch(PersistenceException e) {
			System.out.println(e.getMessage());
		}
	}
}