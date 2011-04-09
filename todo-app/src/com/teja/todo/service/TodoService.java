package com.teja.todo.service;

import java.util.Date;
import java.util.List;

import com.teja.todo.model.TodoItem;

public interface TodoService {

	TodoItem createOrUpdateTodoItem(TodoItem item);
	
	List<TodoItem> getAllItems();
	
	List<TodoItem> getDueItemsByDate(Date date);
	
	List<TodoItem> getDueItems(int mins);
	
	List<TodoItem> updateStatus(List<TodoItem> items, String status);
	
	List<TodoItem> updateItems(List<TodoItem> items);
	
}
