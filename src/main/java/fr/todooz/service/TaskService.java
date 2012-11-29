package fr.todooz.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import fr.todooz.domain.Task;

public class TaskService {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(Task task) {
		Session session = sessionFactory.openSession();
	    session.save(task);
	    session.close();
	}

	public void delete(Long id) {
		Session session = sessionFactory.openSession();
	    Query query = session.createQuery("delete from Task where id = :id");
	    query.setString("id", id.toString());
	    query.executeUpdate();
	    session.close();
	}

	public List<Task> findAll() {
		Session session = sessionFactory.openSession();
	    Query query = session.createQuery("from Task");
	    List<Task> tasks = query.list();
	    session.close();
	    return tasks;
	}

	public List<Task> findByQuery(String enteredQuery) {
		Session session = sessionFactory.openSession();
		String queryString = "from Task where title like '" + '%' + enteredQuery + "%'";
		System.out.println(queryString);
	    Query query = session.createQuery(queryString);
	    List<Task> tasks = query.list();
	    session.close();
	    return tasks;
	}

	public int count() {
		// TODO
	}
}
