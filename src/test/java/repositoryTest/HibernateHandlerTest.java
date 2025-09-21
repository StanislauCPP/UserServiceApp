package repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import entity.User;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import repository.HibernateHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Collections;

public class HibernateHandlerTest {

	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.6")
			.withCreateContainerCmdModifier(cmd -> cmd.getHostConfig()																												//Using it because Docker ToolBox is used on machine with W7
																			.withSecurityOpts(Collections.singletonList("seccomp=unconfined")))
			.withInitScript("database.sql");

	@BeforeAll
	static void beforeAll() {	postgres.start();	}

	HibernateHandler hibernateHandler;

	@BeforeEach
	void beforeEach() {
		HibernateHandler.createConfigAndSessionFactory(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
		hibernateHandler = new HibernateHandler(User.class);
	}

	@AfterEach
	void afterEach() { hibernateHandler.close(); }

	@AfterAll
	static void afterAll() { postgres.stop(); }

	static User readJDBC(int id) {
		try (Connection connection = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
				 PreparedStatement preparedStatement = connection.prepareStatement("""
						 SELECT * from users WHERE id = ?
						 """)){
			preparedStatement.setLong(1, id);

			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				if(!(resultSet.next()))
					return null;
				else {
					User user = new User();
					user.setId(resultSet.getInt("id"));
					user.setName(resultSet.getString("name_"));
					user.setEmail(resultSet.getString("email"));
					user.setAge(resultSet.getInt("age"));
					user.setCreatedAt(resultSet.getDate("created_at").toLocalDate());

					return user;
				}
			}
			catch (Exception e) {	throw new RuntimeException(e); }

		}
		catch (Exception e) {	throw new RuntimeException(e);	}
	}

	@Test
	public void userInput_createOperation_expectedCreatedUser() {
		User expected = new User();
		expected.setName("name");
		expected.setEmail("email@em.ru");
		expected.setAge(100);
		expected.setCreatedAt(LocalDate.now());
		User actual = (User) hibernateHandler.create(expected);

		assertEquals(expected, actual);
	}

	@Test
	public void userWithNullParameterInput_createOperation_ThrowException() {
		User expected = new User();
		expected.setName(null);
		expected.setEmail("email@em.ru");
		expected.setAge(100);
		expected.setCreatedAt(LocalDate.now());
		Throwable exception = assertThrows(PersistenceException.class, () -> hibernateHandler.create(expected));
	}

	@Test
	public void existedUserIdInput_readOperation_expectedExistedUser() {
		int id = 1;
		User expected = readJDBC(id);

		User actual = (User) hibernateHandler.readById(id);

		assertEquals(expected, actual);
	}

	@Test
	public void notExistedUserIdInput_readOperation_expectedNull()	{
		int id = 100;
		User expected = readJDBC(id);

		User actual = (User) hibernateHandler.readById(id);

		assertEquals(expected, actual);
	}

	@Test
	public void existedUserIdUpdatedUserParametersInput_updateOperation_expectedUpdatedUser() {
		int id = 2;

		User expected = readJDBC(id);

		expected.setName("updatedName");
		expected.setEmail("updatedEmail@mail.ru");
		expected.setAge(100);
		User actual = (User) hibernateHandler.update(expected);

		assertEquals(expected, actual);
	}

	@Test
	public void existedUserIdInput_deleteOperation_expectedNull() {
		int id = 1;
		User expected = readJDBC(id);
		if(expected == null)
			throw new RuntimeException("record by this id = %d must to be not null".formatted(id));

		User actual = (User) hibernateHandler.readById(id);
		hibernateHandler.delete(actual);

		expected = readJDBC(id);
		actual = (User) hibernateHandler.readById(id);

		assertEquals(expected, actual);
	}
}