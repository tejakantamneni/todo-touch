package com.teja.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.gson.Gson;
import com.teja.todo.model.TodoItem;
import com.teja.todo.model.TodoStatus;
import com.teja.todo.service.TodoService;
import com.teja.todo.service.impl.TodoServiceImpl;

@SuppressWarnings("serial")
public class GetItemsServlet extends HttpServlet {
	
	TodoService todoService = new TodoServiceImpl();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		//List<TodoItem> items = todoService.getAllItems();

		List<TodoItem> items = new ArrayList<TodoItem>();
		
		User user = new User("test@test.com", "lame-domain");
		String todoText = "some-text";
		Date dueDate = new Date();
		TodoItem item = new TodoItem(user, todoText, dueDate, TodoStatus.DUE.toString());

		items.add(item);
		
		Gson gson = new Gson();
		String json = gson.toJson(items);
		resp.getWriter().print(json);
		
	}
}
