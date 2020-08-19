package com.deloitte.ToDoList.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deloitte.ToDoList.model.Task;
import com.deloitte.ToDoList.repository.TaskRepository;


@Service
public class TaskDAO {
	
	@Autowired
	TaskRepository taskRepository;
	
	/*to save an task*/
	public Task save(Task task) {
		return taskRepository.save(task);
		
	}
	
	/*search all task*/
	public List<Task> findAll(){
		return taskRepository.findAll();
	}
	
	/*search all task*/
	public List<Task> findTasksFromUser(String user){
		return taskRepository.findTasksFromUser(user);
	}
	
	/*get an task*/
	public Task findOne(Long taskid) {
		return taskRepository.findById(taskid).get();
	}
	
	/*get an task*/
	public Optional<Task> findById(Long taskid) {
		return taskRepository.findById(taskid);
	}
	
	/*delete an task*/
	public void delete (Task task) {
		taskRepository.delete(task);
	}
}
