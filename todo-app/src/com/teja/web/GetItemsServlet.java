package com.teja.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.teja.todo.model.TodoItem;
import com.teja.todo.service.TodoService;
import com.teja.todo.service.impl.TodoServiceImpl;

@SuppressWarnings("serial")
public class GetItemsServlet extends HttpServlet {
	
	TodoService todoService = new TodoServiceImpl();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");
		List<TodoItem> items = todoService.getAllItems();
		Gson gson = new Gson();
		String json = gson.toJson(items);
		resp.getWriter().print(json);
		
	}
}
