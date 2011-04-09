package com.teja.todo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.teja.todo.model.TodoItem;
import com.teja.todo.model.TodoStatus;
import com.teja.todo.service.TodoService;
import com.teja.todo.service.impl.TodoServiceImpl;
import com.teja.todo.utils.PMF;

@SuppressWarnings("unused")
public class TodoServiceTest{
	
	private static final Log LOG = LogFactory.getLog(TodoServiceTest.class);
	
	TodoService service;
	PersistenceManager persistenceManager;
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		service = new TodoServiceImpl();
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testSaveOrUpdateTodoItem() {
		User user = new User("test@test.com", "lame-domain");
		String todoText = "some-text";
		Date dueDate = new Date();
		TodoItem item = new TodoItem(user, todoText, dueDate, TodoStatus.DUE.toString());
		TodoItem updateTodoItem = service.createOrUpdateTodoItem(item);
		assertNotNull(updateTodoItem);
		assertEquals(todoText, updateTodoItem.getTodoText());
		String todoText2 = "changed-text";
		updateTodoItem.setTodoText(todoText2);
		TodoItem createOrUpdateTodoItem = service.createOrUpdateTodoItem(updateTodoItem);
		assertEquals(todoText2, createOrUpdateTodoItem.getTodoText());
	}

	@Test
	public void testGetAllItems() {
		User user = new User("test@test.com", "lame-domain");
		String todoText = "some-text";
		Date dueDate = new Date();
		service.createOrUpdateTodoItem(new TodoItem(user, todoText, dueDate, TodoStatus.DUE.toString()));
		service.createOrUpdateTodoItem(new TodoItem(user, todoText, dueDate, TodoStatus.DONE.toString()));
		List<TodoItem> allItems = service.getAllItems();
		assertEquals(2, allItems.size());
	}

	@Test
	public void testGetAllDueItemsByDate() {
		User user = new User("test@test.com", "lame-domain");
		String todoText = "some-text";
		Date dueDate = new Date();
		service.createOrUpdateTodoItem(new TodoItem(user, todoText, DateUtils.addMinutes(dueDate, -20), TodoStatus.DUE.toString()));
		service.createOrUpdateTodoItem(new TodoItem(user, todoText, DateUtils.addMinutes(dueDate, -40), TodoStatus.DONE.toString()));
		List<TodoItem> allItems = service.getDueItemsByDate(dueDate);
		assertEquals(2, allItems.size());
	}
}
