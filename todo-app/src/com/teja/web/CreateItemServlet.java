package com.teja.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
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
public class CreateItemServlet extends HttpServlet {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	TodoService service = new TodoServiceImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json");

		String todoText = req.getParameter("details");
		String dueDate = req.getParameter("dueDate");

		TodoItem item = new TodoItem();
		try {
			item.setDueDate(format.parse(dueDate));
		} catch (ParseException e) {
			item.setDueDate(new Date());
		}
		item.setTodoText(todoText);
		item.setStatus(TodoStatus.DUE.name());
		User owner = new User("teja.kantamneni@gmail.com", "google.com");
		item.setOwner(owner);
		service.createOrUpdateTodoItem(item);
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "true");
		String json = gson.toJson(map);
		System.out.println(json);
		resp.getWriter().print(json);

	}
}
