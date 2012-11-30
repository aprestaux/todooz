package fr.todooz.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.todooz.domain.Task;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Inject
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void save(Task task) {
		Session session = sessionFactory.getCurrentSession();
	    session.save(task);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Session session = sessionFactory.getCurrentSession();
	    Query query = session.createQuery("delete from Task where id = :id");
	    query.setString("id", id.toString());
	    query.executeUpdate();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findAll() {
		Session session = sessionFactory.getCurrentSession();
	    Query query = session.createQuery("from Task");
	    List<Task> tasks = query.list();
	    return tasks;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findByQuery(String enteredQuery) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Task.class);
		crit.add(Restrictions.ilike("title", enteredQuery, MatchMode.ANYWHERE));
		List<Task> tasks = crit.list();
	    return tasks;
	}

	@Override
	@Transactional(readOnly = true)
	public int count() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select count(*) from Task");
		long count = ((Long) query.list().get(0));
		return (int) count;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Task> findByTag(String tag) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Task.class);
		crit.add(Restrictions.ilike("tags", tag, MatchMode.ANYWHERE));
		List<Task> tasks = crit.list();
	    return tasks;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Task> findByDate(DateMidnight date) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Task.class);
		crit.add(Restrictions.between("date", date.toDate(), date.plusDays(1).toDate()));
		List<Task> tasks = crit.list();
	    return tasks;
	}
}
