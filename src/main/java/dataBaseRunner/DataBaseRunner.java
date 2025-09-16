package dataBaseRunner;

import consoleMenu.ConsoleDataBaseMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class DataBaseRunner {
	private static final Logger log = LoggerFactory.getLogger(DataBaseRunner.class);

	public static void main(String[] args) {
		log.info("DataBase runner start executing");

		ConsoleDataBaseMenu.menuCRUD();
	}
}