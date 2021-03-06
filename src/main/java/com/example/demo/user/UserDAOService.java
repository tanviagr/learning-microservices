package com.example.demo.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDAOService {
	private static List<User> users = new ArrayList<User>();
	private static int userCount = 3;
	static {
		users.add(new User(1, "Adam", new Date()));
		users.add(new User(2, "Beth", new Date()));
		users.add(new User(3, "Cynthia", new Date()));
	}
	
	public List<User> findAll() {
		return users;
	}
	
	public User saveUser(User user) {
		// whenever you add a new user, the primary key (ID) is calculated by the backend.
		if (user.getId() == null) user.setId(++userCount);
		users.add(user);
		return user;
	}
	
	public User findOne(int id) {
		for (User user : users) {
			if (user.getId() == id) return user;
		}
		return null;
	}
	
	public User deleteOne(int id) {
		//don't use the for loop, because you dont want to modify the list when iterating over it.
		Iterator<User> it = users.iterator();
		while (it.hasNext()) {
			User user = it.next();
			if (user.getId() == id) {
				it.remove();
				return user;
			}
		}
		return null;
	}
	
}
