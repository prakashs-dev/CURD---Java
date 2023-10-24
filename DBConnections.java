package com.jdbc;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnections {

	public static void main(String[] args) {
//	<----------------	Read Query   --------------------------->
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "admin");
			Statement st = con.createStatement();
			String qry = "Select * from students";
			ResultSet rs = st.executeQuery(qry);
			int id = 0;
			while (rs.next()) {
//				System.out.println( );
//				System.out.println("log");
				id = rs.getInt(1);
			}
			if(id != 0) {
				System.out.println("good");
			}else {
				System.out.println("bad");
			}
			rs.close();
			con.close();
			st.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		
		
//		<-------------- Insert Query ---------------->
		
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "admin");
//			PreparedStatement st = con.prepareStatement("insert into course(cid, cname)values(?,?)");
//			st.setInt(1, 1);
//			st.setString(2, "ML");
//			int i = st.executeUpdate();  
//			System.err.println(i+" records inserted"); 
//			Statement stmt = con.createStatement();
//			String qry = "Select * from course";
//			ResultSet rs = stmt.executeQuery(qry);
//			while (rs.next()) {
//				System.out.println(rs.getInt(1) + " " + rs.getString(2));
//			}
//			rs.close();
//			con.close();
//			st.close();
//			stmt.close();
//		} catch (Exception e) {
//			System.err.println(e);
//		}

//		<-----------  Delete Query ---------------------->
		
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "admin");
//			PreparedStatement st = con.prepareStatement("delete from course where cid = ?");
//			st.setInt(1, 1000);
//			int i = st.executeUpdate();  
//			System.err.println(i+" records inserted"); 
//			Statement stmt = con.createStatement();
//			String qry = "Select * from course";
//			ResultSet rs = stmt.executeQuery(qry);
//			System.out.println();
//			while (rs.next()) {
//				System.out.println(rs.getInt(1)+ "\t" +rs.getString(2));
//			}
//			rs.close();
//			con.close();
//			st.close();
//			stmt.close();
//		} catch (Exception e) {
//			System.err.println(e);
//		}
		
		
//		<---------- Update Query -------------->
		
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "admin");
//			PreparedStatement st = con.prepareStatement("update course set cname = ? where cid = ?");
//			st.setInt(2, 90);
//			st.setString(1, "JavaScript");
//			int i = st.executeUpdate();  
//			System.err.println(i+" records inserted"); 
//			Statement stmt = con.createStatement();
//			String qry = "Select * from course";
//			ResultSet rs = stmt.executeQuery(qry);
//			System.out.println();
//			while (rs.next()) {
//				System.out.println(rs.getInt(1)+ "\t" +rs.getString(2));
//			}
//			rs.close();
//			con.close();
//			st.close();
//			stmt.close();
//		} catch (Exception e) {
//			System.err.println(e);
//		}
	}
}
