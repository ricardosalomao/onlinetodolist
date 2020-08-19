package com.deloitte.ToDoList.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deloitte.ToDoList.model.Task;


public interface TaskRepository extends JpaRepository<Task, Long> {
	
	@Query("FROM Task WHERE user_name = ?1")
	List<Task> findTasksFromUser(String userName);
}
