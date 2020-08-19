package com.deloitte.ToDoList.controller;


import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.deloitte.ToDoList.dao.TaskDAO;
import com.deloitte.ToDoList.model.Task;

@Controller
@RequestMapping("/tasks/")
public class TaskController {
	@Autowired
	TaskDAO TaskDAO;
	
	@GetMapping("showForm")
	public String showTaskForm(Task task) {
		return "add-task";
	}
	
	@GetMapping("list")
	public String Tasks(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("tasks", this.TaskDAO.findTasksFromUser(auth.getName()));
		return "index";
	}
	
	@PostMapping("add")
	public String addTask(@Valid Task task, BindingResult result, Model model ) {
		if (result.hasErrors()) {
			return "add-task";
		}
		task.setCreatedAt(new Date());
		task.setLastUpdatedAt(new Date());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		task.setUserName(auth.getName());	
		
		this.TaskDAO.save(task);
		
		return "redirect:list";
	}
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") Long taskid, Model model) {
		Task task = this.TaskDAO.findById(taskid)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Task Id: "+ taskid));
		model.addAttribute("task", task);
		return "update-task";
	}
	
	@GetMapping("delete/{id}")
	public String deleteTask(@PathVariable ("id") Long taskid, Model model) {
		
		Task task = this.TaskDAO.findById(taskid)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Task Id: "+ taskid));
		this.TaskDAO.delete(task);
		
		model.addAttribute("tasks", this.TaskDAO.findAll());
		return "index";
		
	}
	
	@PostMapping("update/{id}")
	public String updateTask(@PathVariable("id") Long taskid, @Valid Task task, BindingResult result, Model model) {
		if(result.hasErrors()) {
			task.setId(taskid);
			return "update-task";
		}
		task.setLastUpdatedAt(new Date());

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (userName.equals(task.getUserName())) {
			System.out.println("************************** Same User");
			TaskDAO.save(task);
		}
		
		model.addAttribute("tasks", this.TaskDAO.findTasksFromUser(userName));
		
		return "index";
	}
}
