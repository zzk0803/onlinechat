package test;

import java.util.ArrayList;
import java.util.List;

import bean.User;

public class Test {
	public static void main(String[] args) {
		List<User> userList = new ArrayList<>();
		for(int i=1;i<=10;i++) {
			User user1 = new User();
			user1.setuName("张三"+i);
			user1.setuPass("123456");
			user1.setAge(18+i);
			user1.setHeight(175+i);
			userList.add(user1);
		}
		System.out.println(userList.size());
		for (User user : userList) {
			System.out.println(user.toString());
		}
	}
}
