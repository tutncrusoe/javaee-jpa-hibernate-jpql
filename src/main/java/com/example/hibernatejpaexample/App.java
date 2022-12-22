package com.example.hibernatejpaexample;

import com.example.hibernatejpaexample.model.Student;
import com.example.hibernatejpaexample.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class App {
	public static void main(String[] args) {

		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();

		Student student = new Student("Ramesh", "Fadatare", "rameshfadatare@javaguides.com");
		entityManager.persist(student);
		entityManager.getTransaction().commit();
		entityManager.close();

		JPAUtil.shutdown();
	}

}
