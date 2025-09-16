package consoleMenu;

import lombok.Setter;
import service.Service;

import java.util.Locale;
import java.util.Scanner;

public class ConsoleDataBaseMenu {
	static enum CRUDOperation {
		CREATE,
		READ,
		UPDATE,
		DELETE
	}

	private ConsoleDataBaseMenu(){}

	private static Scanner input = new Scanner(System.in);
	private static Service service = new Service();


	static public void menuCRUD()	{
		while(true) {
			System.out.println("Choose operation" +
					"\ncreate\tread\tupdate\tdelete\t\texit - for exit from CRUD menu\n");

			String operation = input.nextLine().toUpperCase(Locale.ROOT);

			if((operation.contentEquals("EXIT")))	{
				service.close();
				input.close();
				return;
			}
			else
				switch (CRUDOperation.valueOf(operation)) {
					case CREATE: {
						System.out.println("Set name, email, age - for creating user record\n");

						service.createUser(input.nextLine(), input.nextLine(), input.nextInt());
						input.nextLine();																																														// take \n after nextInt
					}
					break;

					case READ: {
						System.out.println("Set id for searching user record\n");
						Object user = service.searchUserById(input.nextInt());
						input.nextLine();																																														// take \n after nextInt
						System.out.println(user);
					}
						break;

					case UPDATE: {
						System.out.println("Set id for updating user record\n");
						int id = input.nextInt();
						input.nextLine();																																														// take \n after nextInt

						System.out.println("Set name, email, age - for updating user record\n");

						service.updateUser(id,input.nextLine(), input.nextLine(), input.nextInt());
						input.nextLine();																																														// take \n after nextInt
					}
						break;

					case DELETE: {
						System.out.println("Set id for deleting user record\n");
						service.deleteUser(input.nextInt());
						input.nextLine();																																														// take \n after nextInt
					}
						break;

					default:
						System.out.println("Wrong number");
						break;
				};
		}
	}
}
