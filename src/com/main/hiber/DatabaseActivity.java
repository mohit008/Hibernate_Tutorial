package com.main.hiber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DatabaseActivity {


	public static final String divider = "----------------x----------------";
	public static int loginAttempt = 0;
	Statement statement = null;
	Connection connection = null;
	ResultSet resultSet ;
	ResultSetMetaData resultSetMetaData;


	public DatabaseActivity(){
		if(SetupConnection()){
			callForCommand();
		}
		
	}

	
	/**
	 * use command line to execute query
	 */
	private void callForCommand() {
		log("Enter user and pass to insert record");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			// read from console
			String user =reader.readLine();
			reader.readLine();
			String pass = reader.readLine();

			if(!user.equals("")&& !pass.equals("")){
				String query = "insert into user values(,'"+user+"','"+pass+"');";				
				try {
					statement = (Statement) connection.createStatement();
					int resultSet = statement.executeUpdate(query);			
					
				} catch (SQLException e) {
					e.printStackTrace();
				}			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}

	/**
	 * setup data base connection
	 * @return
	 */
	public boolean SetupConnection(){
		try {
			try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_database","root","root");
			if(connection.getMetaData()!=null){
				loginAttempt = 0;
				System.out.println("Database connected : " + connection.getMetaData().toString());
				System.out.println(divider);
				System.out.println(connection.getMetaData().getUserName());
				System.out.println(divider);
				return true;
			}  
		} catch (SQLException e) {		
			try {
				
				// reattempting
				log("Reattempting in 4sec");
				Thread.sleep(4000);
				if(loginAttempt < 5){
					log("Reconneting with attempt : " +loginAttempt+"");									
				}else{
					loginAttempt = 0;
					int exit = JOptionPane.showConfirmDialog(null, "Cannot connect to server please try again later", "Error", JOptionPane.YES_NO_OPTION);
					if(exit == JOptionPane.NO_OPTION){
						System.exit(0);
					}
				}
				SetupConnection();	
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}			
		} 
		return false;
	}

	
	/**
	 * log to console
	 * @param msg
	 */
	public void log(String msg) {
		System.out.println(msg);
	}

	/**
	 * close data base connection
	 */
	public void closeDataBaseConnection() {
		try {
			if(connection.isClosed()){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}





}
