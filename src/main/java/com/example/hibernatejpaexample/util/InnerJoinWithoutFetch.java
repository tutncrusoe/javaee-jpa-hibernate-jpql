package com.example.hibernatejpaexample.util;

import com.example.hibernatejpaexample.model.Employee;
import com.example.hibernatejpaexample.model.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class InnerJoinWithoutFetch {
    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("PERSISTENCE");

    public static void main(String[] args) {
        try {
            persistEmployees();
            executeQuery();
        } finally {
            entityManagerFactory.close();
        }
    }

    public static void persistEmployees() {
        Task task1 = new Task("Coding", "Denise");
        Task task2 = new Task("Refactoring", "Rose");
        Task task3 = new Task("Designing", "Denise");
        Task task4 = new Task("Documentation", "Mike");

        Employee employee1 = Employee.create("Diana", task1, task3);
        Employee employee2 = Employee.create("Mike", task2, task4);
        Employee employee3 = Employee.create("Tim", task3, task4);
        Employee employee4 = Employee.create("Jack");

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(employee1);
        em.persist(employee2);
        em.persist(employee3);
        em.persist(employee4);
        em.getTransaction().commit();
        em.close();
        System.out.println("-- Employee persisted --");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + employee1);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + employee2);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + employee3);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + employee4);
    }

    private static void executeQuery() {
        System.out.println("-- executing query --");
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery("SELECT DISTINCT e FROM Employee e INNER JOIN e.tasks t");
        List<Employee> resultList = query.getResultList();
        for (Employee employee : resultList) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + employee.getName() + " - " + employee.getTasks());
        }
        em.close();
    }
    /*
    -- Employee persisted --
Employee{id=1, name='Diana', tasks=[Task{id=1, description='Coding', supervisor='Denise'}, Task{id=2, description='Designing', supervisor='Denise'}]}
Employee{id=2, name='Mike', tasks=[Task{id=3, description='Refactoring', supervisor='Rose'}, Task{id=4, description='Documentation', supervisor='Mike'}]}
Employee{id=3, name='Tim', tasks=[Task{id=2, description='Designing', supervisor='Denise'}, Task{id=4, description='Documentation', supervisor='Mike'}]}
Employee{id=4, name='Jack', tasks=[]}
    -- executing query --
Hibernate: select distinct e1_0.id,e1_0.name from Employee e1_0 join Employee_Task t1_0 on e1_0.id=t1_0.Employee_id
Hibernate: select t1_0.Employee_id,t1_1.id,t1_1.description,t1_1.supervisor from Employee_Task t1_0 join Task t1_1 on t1_1.id=t1_0.tasks_id where t1_0.Employee_id=?
Diana - [Task{id=1, description='Coding', supervisor='Denise'}, Task{id=2, description='Designing', supervisor='Denise'}]
Hibernate: select t1_0.Employee_id,t1_1.id,t1_1.description,t1_1.supervisor from Employee_Task t1_0 join Task t1_1 on t1_1.id=t1_0.tasks_id where t1_0.Employee_id=?
Mike - [Task{id=3, description='Refactoring', supervisor='Rose'}, Task{id=4, description='Documentation', supervisor='Mike'}]
Hibernate: select t1_0.Employee_id,t1_1.id,t1_1.description,t1_1.supervisor from Employee_Task t1_0 join Task t1_1 on t1_1.id=t1_0.tasks_id where t1_0.Employee_id=?
Tim - [Task{id=2, description='Designing', supervisor='Denise'}, Task{id=4, description='Documentation', supervisor='Mike'}]
     */
}