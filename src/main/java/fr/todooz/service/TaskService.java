package fr.todooz.service;

import java.util.List;

import org.joda.time.DateMidnight;

import fr.todooz.domain.Task;

public interface TaskService {

	public Long save(Task task);

	public void delete(Long id);

	public List<Task> findAll();

	public List<Task> findByQuery(String enteredQuery);

	public int count();
	
	public List<Task> findByTag(String tag);
	
	public List<Task> findByDate(DateMidnight date);
	
	public Task findById(Long id);

}