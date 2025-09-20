package repository;

import entity.User;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
public class HibernateHandler {
	private static final Logger log = LoggerFactory.getLogger(HibernateHandler.class);
	private static SessionFactory sessionFactory;

	private Session session;

	public static void createConfigAndSessionFactory(String url, String name, String pass) {
		Configuration configuration = new Configuration();
		configuration.configure();

		configuration.setProperty("hibernate.connection.url", url);
		configuration.setProperty("hibernate.connection.username", name);
		configuration.setProperty("hibernate.connection.password", pass);

		try { sessionFactory = configuration.buildSessionFactory(); }
		catch (HibernateException e) { throw new RuntimeException(e); }
	}

	public Object create(User user) {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			session.persist(user);																										// entity (user) id is assigned in this operation

			session.getTransaction().commit();

			return readById(user.getId());
		}
		catch (Exception e)	{
			session.getTransaction().rollback();
			close();
			log.error("Transaction failed\n {}", e.getMessage());
			throw new RuntimeException("Creating record in database failed\n");
		}
		finally {	session.close(); }
	}

	public Object readById(int id) {
		Object entity = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			entity = session.get(User.class, id);
		}
		catch (Exception e)	{
			close();
			log.error("Transaction failed\n {}", e.getMessage());
			throw new RuntimeException();
		}
		finally {	session.close(); }

		return entity;
	}

	public Object update(User user) {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			session.merge(user);

			session.getTransaction().commit();

			return readById(user.getId());
		}
		catch (Exception e)	{
			session.getTransaction().rollback();
			close();
			log.error("Transaction failed\n {}", e.getMessage());
			throw new RuntimeException();
		}
		finally {	session.close(); }
	}

	public void delete(User user) {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			session.remove(user);

			session.getTransaction().commit();
		}
		catch (Exception e)	{
			session.getTransaction().rollback();
			close();
			log.error("Transaction failed\n {}", e.getMessage());
			throw new RuntimeException();
		}
		finally {	session.close(); }
	}

	public void close() { sessionFactory.close(); }
}