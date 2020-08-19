package com.deloitte.ToDoList;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class HomeResource {
	@GetMapping("/")
	public String home(HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		session.setAttribute("username", auth.getName());
		return "redirect:/tasks/list";
	}

}
