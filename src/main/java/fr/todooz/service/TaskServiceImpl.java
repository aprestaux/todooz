package fr.todooz.service;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateMidnight;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.todooz.domain.Task;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Inject
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public Long save(Task task) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(task);
	    return task.getId();
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
	    Criteria crit = session.createCriteria(Task.class);
	    crit.addOrder(Order.desc("date"));
	    List<Task> tasks = crit.list();
	    return tasks;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findByQuery(String enteredQuery) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Task.class);
		crit.add(Restrictions.ilike("title", enteredQuery, MatchMode.ANYWHERE));
		crit.addOrder(Order.desc("date"));
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
		crit.addOrder(Order.desc("date"));
		List<Task> tasks = crit.list();
	    return tasks;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Task> findByDate(DateMidnight date) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Task.class);
		crit.add(Restrictions.between("date", date.toDate(), date.plusDays(1).toDate()));
		crit.addOrder(Order.desc("date"));
		List<Task> tasks = crit.list();
	    return tasks;
	}
	
	@Override
	@Transactional
	public Task findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
	    Query query = session.createQuery("from Task where id = :id");
	    query.setString("id", id.toString());
	    List<Task> result = query.list();
	    if (result.size() > 0) {
	    	return (Task) query.list().get(0);
	    }else{
	    	return null;
	    }
	}
}
