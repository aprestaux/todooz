package fr.todooz.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DummyData {
	  public static List<Task> tasks() {
	    final int TASK_COUNT = 10;

	    List<Task> tasks = new ArrayList<Task>();

	    for (int i = 0; i < TASK_COUNT; i++) {
	        tasks.add(task());
	    }

	    return tasks;
	  }

	  private static Task task() {
	    Task task = new Task();

	    task.setDate(new Date());
	    task.setTitle("Ma tache");
	    task.setText("C'est ma tache a moi");
	    task.setTags("kikou, hehe");

	    return task;
	  }
	}
