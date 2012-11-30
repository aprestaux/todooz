package fr.todooz.domain;

import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.MySQL5Dialect;

public class SqlGenerator {

	public static void main(String[] args) {
		Configuration configuration = new Configuration();

		configuration.addAnnotatedClass(Task.class);

		for (String string : configuration.generateSchemaCreationScript(new MySQL5Dialect())) {
		   System.out.println(string + ";");
		}

	}

}
