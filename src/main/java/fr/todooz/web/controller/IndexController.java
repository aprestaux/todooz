package fr.todooz.web.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.joda.time.DateMidnight;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.todooz.domain.Task;
import fr.todooz.service.TagCloudService;
import fr.todooz.service.TaskService;
import fr.todooz.util.TagCloud;

@Controller
public class IndexController {

	@Inject
	private TaskService taskService;
	
	@Inject
	private DataSource dataSource;

	@Inject
	private TagCloudService tagCloudService;

	@ModelAttribute("cloud")
	public TagCloud tagCloud() {
		return tagCloudService.buildTagCloud();
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping({ "/", "/index" })
	public String index(Model model) {
		model.addAttribute("main_title", "Find All");

		model.addAttribute("tasks", taskService.findAll());

		return "index";
	}

	@RequestMapping("/search")
	public String search(String query, Model model) {
		model.addAttribute("main_title", "Results for " + query);

		model.addAttribute("tasks", taskService.findByQuery(query));

		return "index";
	}

	@RequestMapping("/today")
	public String today(String query, Model model) {
		model.addAttribute("main_title", "Tasks for today");

		DateMidnight now = new DateMidnight();
		model.addAttribute("tasks", taskService.findByDate(now));

		return "index";
	}

	@RequestMapping("/tomorrow")
	public String tomorrow(String query, Model model) {
		model.addAttribute("main_title", "Tasks for tomorrow");

		DateMidnight now = new DateMidnight();
		DateMidnight tomorrow = now.plusDays(1);
		model.addAttribute("tasks", taskService.findByDate(tomorrow));

		return "index";
	}

	@RequestMapping("/tag/{tag}")
	public String tag(@PathVariable String tag, Model model) {
		model.addAttribute("main_title", "Tasks with tag " + tag);

		model.addAttribute("tasks", taskService.findByTag(tag));

		return "index";
	}

	@PostConstruct
	public void bootstrap() {
		if (taskService.count() == 0) {
			Task task = new Task();
			task.setDate(new Date());
			task.setTitle("Ma tache");
			task.setText("Description de la tache");
			task.setTags("tag1, tag2");
			taskService.save(task);

			task.setDate(new Date());
			task.setTitle("Ma tache 2");
			task.setText("Description de la tache");
			task.setTags("tag1, tag2");
			taskService.save(task);

			task.setDate(new Date());
			task.setTitle("Ma tache 3");
			task.setText("Description de la tache");
			task.setTags("tag0");
			taskService.save(task);
		}

	}

	@PostConstruct
	public void initUsers() throws SQLException {
		Connection connection = dataSource.getConnection();

		try {
			// create table users if missing
			if (!tableExists("users")) {
				connection
						.prepareStatement(
								"create table users (username varchar(50), password varchar(50), enabled boolean)")
						.execute();
			}

			// create table authorities if missing
			if (!tableExists("authorities")) {
				connection
						.prepareStatement(
								"create table authorities (username varchar(50), authority varchar(50))")
						.execute();
			}

			// fill tables with test data
			connection.prepareStatement("delete from authorities").execute();
			connection.prepareStatement("delete from users").execute();

			connection
					.prepareStatement(
							"insert into users (username, password, enabled) values ('test', 'user', true)")
					.execute();
			connection
					.prepareStatement(
							"insert into authorities (username, authority) values ('test', 'ROLE_USER')")
					.execute();
		} finally {
			connection.close();
		}
	}

	private boolean tableExists(String name) throws SQLException {
		Connection connection = dataSource.getConnection();

		try {
			DatabaseMetaData dbmd = connection.getMetaData();
			ResultSet rs = dbmd
					.getTables(null, "APP", name.toUpperCase(), null);
			return rs.next();
		} finally {
			connection.close();
		}
	}

	// @PreDestroy
	// public void cleanBootstrap() {
	// for (Task task : taskService.findAll())
	// taskService.delete(task.getId());
	// }
}
