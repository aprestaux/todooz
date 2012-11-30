package fr.todooz.service;

import java.util.List;

import fr.todooz.domain.Task;

public interface TaskService {

	public void save(Task task);

	public void delete(Long id);

	public List<Task> findAll();

	public List<Task> findByQuery(String enteredQuery);

	public int count();

}