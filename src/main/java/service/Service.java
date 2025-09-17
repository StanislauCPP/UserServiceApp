package service;

import entity.User;
import repository.HibernateHandler;

import java.time.LocalDate;

public class Service {
	static { HibernateHandler.createConfigAndSessionFactory("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin"); }

	private HibernateHandler hibernateHandler = new HibernateHandler();

	public void createUser(String name, String email, int age) {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setAge(age);
		user.setCreatedAt(LocalDate.now());

		hibernateHandler.create(user);
	}

	public Object searchUserById(int id) {
		return hibernateHandler.readById(id);
	}

	public void updateUser(int id, String name, String email, int age) {
		User user = (User) searchUserById(id);
		if (user == null)
			System.out.println("Record wasn't found");
		else {
			user.setName(name);
			user.setEmail(email);
			user.setAge(age);
			hibernateHandler.update(user);
		}
	}

	public void deleteUser(int id) {
		User user = (User) searchUserById(id);
		if (user == null)
			System.out.println("Record wasn't found");
		else
			hibernateHandler.delete(user);
	}

	public void close() {	hibernateHandler.close();	}
}
