package com.example.hibernatejpaexample.util;

import com.example.hibernatejpaexample.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

import static com.example.hibernatejpaexample.util.PersistEmployees.entityManagerFactory;
import static com.example.hibernatejpaexample.util.PersistEmployees.persistEmployees;

public class InnerJoinFetch {

    public static void main(String[] args) {
        try {
            persistEmployees();
            executeQueryInnerJoinFetch();
        } finally {
            entityManagerFactory.close();
        }
    }

    private static void executeQueryInnerJoinFetch() {
        System.out.println("-- executing query --");
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery("SELECT DISTINCT e FROM Employee e INNER JOIN FETCH e.tasks t");
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
Hibernate: select distinct e1_0.id,e1_0.name,t1_0.Employee_id,t1_1.id,t1_1.description,t1_1.supervisor from Employee e1_0 join (Employee_Task t1_0 join Task t1_1 on t1_1.id=t1_0.tasks_id) on e1_0.id=t1_0.Employee_id
Diana - [Task{id=1, description='Coding', supervisor='Denise'}, Task{id=2, description='Designing', supervisor='Denise'}]
Mike - [Task{id=3, description='Refactoring', supervisor='Rose'}, Task{id=4, description='Documentation', supervisor='Mike'}]
Tim - [Task{id=2, description='Designing', supervisor='Denise'}, Task{id=4, description='Documentation', supervisor='Mike'}]
     */
}
