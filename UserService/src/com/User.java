package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

public class User {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gb", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String login(String userName, String password) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " select `userName`,`password` from user where userName=? and password=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			// binding values
			preparedStmt.setString(1, userName);
			preparedStmt.setString(2,password);
			//preparedStmt.setString(2, Base64.getEncoder().encodeToString(password.getBytes()));
			
			System.out.println(preparedStmt);
			System.out.println(userName);
			System.out.println(Base64.getEncoder().encodeToString(password.getBytes()));
			
			ResultSet rs =   preparedStmt.executeQuery();

			if(rs.next()) {
				con.close();
				if(userName.equals("admin")) {
					return "You are logged in";
				}
				return "welcome "+userName+ "  !! to the GadgetBadget website";
				
			}
			else  {
				con.close();
				if(userName.equals("")) {
					return "username cannot be empty";
				}
				else if(password.equals("")){
					return "password cannot be empty";
				}
				else {
					return "username or password ";
				}
			}
			
			
		} catch (Exception e) {
			output = "Error while";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String readUsers() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Id</th><th>User name</th><th>password</th><th>email</th><th>type</th>"+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from user";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String UserId = Integer.toString(rs.getInt("UserId"));
				String userName = rs.getString("userName");
				String password = rs.getString("password");
				String email = rs.getString("email");
				String type = rs.getString("type");
				// Add into the html table
				 output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + UserId
						 + "'>" + UserId + "</td>";
				output += "<td>" + userName + "</td>";
				output += "<td>" + password + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + type + "</td>"; 
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-itemid='" + UserId + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-itemid='" + UserId + "'></td></tr>"; 
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String insertUsers(String name, String pwd ,String email,String type) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			/*String query1 ="select UserId from user where userName =? and password=?";
			PreparedStatement preparedStmt1 = con.prepareStatement(query1);
			preparedStmt1.setString(1, name);
			preparedStmt1.setString(2,pwd);
			System.out.println();
			ResultSet rs =   preparedStmt1.executeQuery();

			if(rs.next()) {
				con.close();
				
				return "username or password is already taken.Use different one";
				
			}
			else  {*/
				
				// create a prepared statement
				String query = " insert into user(`UserId`,`userName`,`password`,`email`,`type`)"
						+ " values (?, ?, ?,?,?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, pwd);
				preparedStmt.setString(4, email);
				preparedStmt.setString(5, type);
				// execute the statement
				preparedStmt.execute();
				con.close();
				String newUsers = readUsers();
				output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}";
			//}
			
			
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the user.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String updateUser(String userID,String name, String pwd,String email,String type) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE user SET userName=?,password=? ,email=?,type=? WHERE UserId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, pwd);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, type);
			preparedStmt.setInt(5, Integer.parseInt(userID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newUsers = readUsers();
			output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the user.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String deleteUser(String userID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from user where UserId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(userID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newUsers = readUsers();
			output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the user.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	

}
