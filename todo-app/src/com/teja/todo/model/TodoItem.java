package com.teja.todo.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

@PersistenceCapable
public class TodoItem {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
    private User owner;

    @Persistent
    private String todoText;

    @Persistent
    private Date dueDate;
    
    @Persistent
    private String status; 
    
	public TodoItem() {
		super();
	}
	
	public TodoItem(User owner, String todoText, Date dueDate, String status) {
		super();
		this.owner = owner;
		this.todoText = todoText;
		this.dueDate = dueDate;
		this.status = status;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getTodoText() {
		return todoText;
	}

	public void setTodoText(String todoText) {
		this.todoText = todoText;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date date) {
		this.dueDate = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
