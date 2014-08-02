package com.example.newapp;

import java.util.List;

import android.app.Application;

import com.facebook.model.GraphUser;

public class SmartMapApplication extends Application {
	private List<GraphUser> selectedUsers;
	public List<GraphUser> getSelectedUsers() {
	    return selectedUsers;
	}

	public void setSelectedUsers(List<GraphUser> users) {
	    selectedUsers = users;
	}
	
	public GraphUser getUser() {
		return user;
	}

	public void setUser(GraphUser user) {
		this.user = user;
	}

	private GraphUser user;
	
}
