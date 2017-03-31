package com.main.hiber;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserDetail {
	@Id
	String user;
	String pass;
	int id;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserDetail() {
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
	@Override
	public String toString() {
		return "UserDetail [user=" + user + ", pass=" + pass + ", getUser()=" + getUser() + ", getPass()=" + getPass()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	

}
