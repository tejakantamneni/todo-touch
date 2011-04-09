package com.teja.todo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.teja.todo.model.TodoItem;
import com.teja.todo.utils.PMF;

public class JDOTest {
	
	private static final Log LOG = LogFactory.getLog(JDOTest.class);
	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test(expected = JDOObjectNotFoundException.class)
	public void testGetTodoByIdShouldThrowExceptionIfNotExists() {
		PersistenceManager persistenceManager = PMF.get().getPersistenceManager();
		persistenceManager.getObjectById(TodoItem.class, "some-id");
	}
	
	@Test
	public void testShouldReturnObjectAfterSave(){
		Date date = new Date();
		String todoText = "Sample TODO Item.";
		User owner = new User("test@test.com", "lame.com");
		TodoItem item = new TodoItem();
		item.setDueDate(date);
		item.setTodoText(todoText);
		item.setOwner(owner);

		PersistenceManager persistenceManager = PMF.get().getPersistenceManager();
		TodoItem todoItem = persistenceManager.makePersistent(item);
		LOG.debug(todoItem.getKey());
		assertNotNull(todoItem.getKey());
		
		TodoItem item2 = persistenceManager.getObjectById(TodoItem.class, todoItem.getKey());
		assertEquals(owner, item2.getOwner());
		assertEquals(date, item2.getDueDate());
		assertEquals(todoText, item2.getTodoText());
		
	}

}
