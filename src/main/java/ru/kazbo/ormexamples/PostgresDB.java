package ru.kazbo.ormexamples;

import java.io.IOException;

public class PostgresDB {
	private static final String 
						PGCTL_PATH = "D:/Programs/PostgreSQL-14.2/bin/pg_ctl.exe",
						DB_PATH = "D:/Databases/MySampleDatabase";
						
	public static void execPostgres(boolean isStart) throws IOException, InterruptedException {
		String command = isStart ? "start -o \"-F -p 7777\"" : "stop";
		Runtime.getRuntime().exec("%s %s -D %s".formatted(PGCTL_PATH, command, DB_PATH));
		Thread.sleep(2000);	// Костыль: делаем тайминг для подгрузки базы данных
	}
}