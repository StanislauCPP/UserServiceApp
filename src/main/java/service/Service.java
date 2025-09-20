package service;

import dto.UserDto;
import dto.mapper.UserMapper;
import entity.User;
import repository.HibernateHandler;

import java.time.LocalDate;

public class Service {
	static { HibernateHandler.createConfigAndSessionFactory("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin"); }

	private HibernateHandler hibernateHandler = new HibernateHandler();

	public UserDto createUser(UserDto userDto) {
		User user = UserMapper.toEntity(userDto);

		user = (User) hibernateHandler.create(user);

		return UserMapper.toDto(user);
	}

	public UserDto searchUserById(int id) {
		User user = (User) hibernateHandler.readById(id);
		return UserMapper.toDto(user);
	}

	public UserDto updateUser(int id, UserDto userDto) {
		UserDto updateResult = null;
		User user = (User) hibernateHandler.readById(id);
		if (user == null)
			System.out.println("Record wasn't found");
		else {
			user = UserMapper.toEntity(userDto);
			user.setId(id);
			user = (User) hibernateHandler.update(user);

			updateResult = UserMapper.toDto(user);
		}

		return updateResult;
	}

	public boolean deleteUser(int id) {
		User user = (User) hibernateHandler.readById(id);
		if (user == null) {
			System.out.println("Record wasn't found");
			return false;
		}
		else
			hibernateHandler.delete(user);

		return true;
	}

	public void close() {	hibernateHandler.close();	}
}
