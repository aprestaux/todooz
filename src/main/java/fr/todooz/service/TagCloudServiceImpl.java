package fr.todooz.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import fr.todooz.domain.Task;
import fr.todooz.util.TagCloud;

@Service
public class TagCloudServiceImpl implements TagCloudService {
	
	@Inject
	private TaskService taskService;

	@Override
	public TagCloud buildTagCloud() {
		List<Task> tasks = taskService.findAll();
		TagCloud result = new TagCloud();
		for ( Task task : tasks ){
    		result.add(task.getTagArray());
		}
		return result;
	}

}
