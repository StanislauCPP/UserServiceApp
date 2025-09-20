package serviceTest;

import dto.UserDto;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.HibernateHandler;
import service.Service;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {
	@InjectMocks
	private Service service;

	@Mock
	private HibernateHandler hibernateHandler;

	User user = new User();
	UserDto expected;

	@BeforeEach
	void beforeEach() {
		String name = "name5";
		String email = "email5@em.ru";
		int age = 50;
		LocalDate localDate = LocalDate.of(2000, 5, 5);

		user.setName(name);
		user.setEmail(email);
		user.setAge(50);
		user.setCreatedAt(localDate);

		expected = new UserDto(name, email, age, localDate);
	}

	@ExtendWith(MockitoExtension.class)
	@Test
	public void createUser() {
		Mockito.when(hibernateHandler.create(user)).thenReturn(user);

		UserDto actual = service.createUser(expected);

		assertEquals(expected, actual);
	}

	@ExtendWith(MockitoExtension.class)
	@Test
	public void searchUserById() {
		int id = 5;

		Mockito.when(hibernateHandler.readById(id)).thenReturn(user);

		UserDto actual = service.searchUserById(id);

		assertEquals(expected, actual);
	}

	@ExtendWith(MockitoExtension.class)
	@Test
	public void updateUser() {
		int id = 5;

		user.setId(id);
		Mockito.when(hibernateHandler.readById(id)).thenReturn(user);
		Mockito.when(hibernateHandler.update(user)).thenReturn(user);

		UserDto actual = service.updateUser(id, expected);

		assertEquals(expected, actual);
	}

	@ExtendWith(MockitoExtension.class)
	@Test
	public void updateNotExistedUser() {
		int id = 5;

		user.setId(id);
		Mockito.when(hibernateHandler.readById(id)).thenReturn(null);

		UserDto actual = service.updateUser(id, expected);

		assertNull(actual);
	}

	@ExtendWith(MockitoExtension.class)
	@Test
	public void delete(){
		int id = 5;

		user.setId(id);
		Mockito.when(hibernateHandler.readById(id)).thenReturn(user);

		boolean actual = service.deleteUser(id);

		assertTrue(actual);
	}

	@ExtendWith(MockitoExtension.class)
	@Test
	public void deleteNotExistedUser(){
		int id = 5;

		user.setId(id);
		Mockito.when(hibernateHandler.readById(id)).thenReturn(null);

		boolean actual = service.deleteUser(id);

		assertFalse(actual);
	}
}