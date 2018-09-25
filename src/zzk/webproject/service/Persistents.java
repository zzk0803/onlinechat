package zzk.webproject.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class Persistents {
	private static final String DB_URL="jdbc:mysql://localhost:5060/webproject";
	private static final String DB_DRIVER_NAME="org.mysql.driver.Driver";
	private static final String DB_USERNAME="zhangzhike";
	private static final String DB_PASSWORD="123456";
	
	private static final ThreadLocal<Connection> DB_CONNECTION_THREADLOCAL=new ThreadLocal<>();
	
	
	private Persistents() {

	}
	
	public static Connection getOne() {
		Connection connection=DB_CONNECTION_THREADLOCAL.get();
		if(connection==null) {
			Class.forName(DB_DRIVER_NAME);
		}
	}
	
}
