package com.example.hibernatejpaexample.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public static Employee create(String name, Task... tasks) {
        Employee e = new Employee();
        e.name = name;
        if (tasks != null) {
            e.tasks = Arrays.asList(tasks);
        }
        return e;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}