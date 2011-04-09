package com.teja.todo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.lang3.time.DateUtils;

import com.teja.todo.model.TodoItem;
import com.teja.todo.service.TodoService;
import com.teja.todo.utils.PMF;

@SuppressWarnings("unchecked")
public class TodoServiceImpl implements TodoService {
	PersistenceManager persistenceManager;

	public TodoServiceImpl() {
		persistenceManager = PMF.get().getPersistenceManager();
	}

	@Override
	public TodoItem createOrUpdateTodoItem(TodoItem item) {
		return persistenceManager.makePersistent(item);
	}

	@Override
	public List<TodoItem> getAllItems() {
		Query getAllItemsQuery = persistenceManager.newQuery(TodoItem.class);
		return (List<TodoItem>)getAllItemsQuery.execute();
	}

	@Override
	public List<TodoItem> getDueItemsByDate(Date date) {
		Query getAllItemsQuery = persistenceManager.newQuery(TodoItem.class, "dueDate <= compare_date");
		getAllItemsQuery.declareImports("import java.util.Date");
		getAllItemsQuery.declareParameters("Date compare_date");
		getAllItemsQuery.setOrdering("dueDate descending");
		return (List<TodoItem>)getAllItemsQuery.execute(date);
	}

	@Override
	public List<TodoItem> getDueItems(int mins) {
		Date current = new Date();
		current = DateUtils.addMinutes(current, mins);
		return getDueItemsByDate(current);
	}

	@Override
	public List<TodoItem> updateStatus(List<TodoItem> items, String status) {
		for (TodoItem todoItem : items) {
			todoItem.setStatus(status);
		}
		return updateItems(items);
	}

	@Override
	public List<TodoItem> updateItems(List<TodoItem> items) {
		List<TodoItem> reItems = new ArrayList<TodoItem>();
		for (TodoItem todoItem : items) {
			reItems.add(createOrUpdateTodoItem(todoItem));
		}
		return reItems;
	}

	
}
