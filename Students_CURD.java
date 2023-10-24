package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Students_CURD {

	static Connection con;
	static PreparedStatement pstmt;
	static Statement st;
	static ResultSet rs;
	static int id;
	static int age;
	static String name;
	static int size;
	static int i = 1;
	static int record_count = 0;
	static String query;

	public static void ReadAll(Scanner sc) {

		try {
			query = "Select * From Students Order by ID ASC";
			st = con.createStatement();
			rs = st.executeQuery(query);
			System.err.println("Id" + "|\t|" + "Name" + "|\t|" + "Age" + "|\t|" + "Date|");
			while (rs.next()) {
				System.out.println(
						rs.getInt(1) + "|\t|" + rs.getString(2) + "|\t|" + rs.getInt(3) + "|\t|" + rs.getDate(4));
				record_count++;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		System.err.println("All Records to be Readed");
	}

	public static void Read(Scanner sc) {
		System.err.print("Please enter the number of records you want to read: ");
		size = sc.nextInt();
		while (i <= size) {
			try {
				System.out.println("Enter " + i + " Student Id for Read:");
				id = sc.nextInt();
				boolean isPresent = IsPresent(id);
				if (isPresent) {
					query = "Select Id, Name, Age, Join_Date From Students where id = " + id;
					st = con.createStatement();
					rs = st.executeQuery(query);
					while (rs.next()) {
						System.out.println(rs.getInt("ID") + "|\t|" + rs.getString("NAME") + "|\t|" + rs.getInt("AGE")
								+ "|\t|" + rs.getDate("JOIN_DATE"));
						record_count++;
					}
				} else {
					System.out.println("The specified ID is not found in the Database");
					sc.close();
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			i++;
		}
		System.err.println(record_count + " Records to be Readed");
	}

	public static void Update(Scanner sc) throws Exception {
		System.err.print("Please enter the number of records you want to update: ");
		size = sc.nextInt();
		while (i <= size) {
			try {
				System.out.println("Enter " + i + " Student Id for Find:");
				id = sc.nextInt();
				System.out.println("Enter " + i + " Student Age for Update:");
				age = sc.nextInt();
				System.out.println("Enter " + i + " Student Name for Update:");
				name = sc.next();
				boolean isPresent = IsPresent(id);
				pstmt = con.prepareStatement("Update Students set name = ?, age = ?, Join_Date = SYSDATE Where id = ?");
				if (isPresent) {
					pstmt.setString(1, name);
					pstmt.setInt(2, age);
					pstmt.setInt(3, id);
				} else {
					System.out.println("The specified ID is not found in the Database");
					sc.close();
				}
				pstmt.executeUpdate();
				record_count++;
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			i++;
		}
		System.err.println(record_count + " Records to be Updated");
	}

	public static void Delete(Scanner sc) {
		System.err.print("Please enter the number of records you want to delete: ");
		size = sc.nextInt();
		while (i <= size) {
			try {
				System.out.println("Enter " + i + " Student Id for Delete:");
				id = sc.nextInt();
				boolean isPresent = IsPresent(id);
				pstmt = con.prepareStatement("Delete from Students where id = ?");
				if (isPresent) {
					pstmt.setInt(1, id);
				} else {
					System.out.println("The specified ID is not found in the Database");
					sc.close();
				}
				pstmt.executeUpdate();
				record_count++;
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			i++;
		}
		System.err.println(record_count + " Records to be Deleted");
	}

	public static boolean IsPresent(int id) throws Exception {
		int isNum = 0;
		query = "SELECT Id FROM Students WHERE Id = " + id;
		st = con.createStatement();
		rs = st.executeQuery(query);
		while (rs.next()) {
			isNum = rs.getInt("ID");
		}
		return isNum == id;
	}

	public static void Insert(Scanner sc) {

		System.err.print("Please enter the number of records you want to insert: ");
		size = sc.nextInt();
		while (i <= size) {
			try {
				System.out.println("Enter " + i + " Student Id for Insert:");
				id = sc.nextInt();
				System.out.println("Enter " + i + " Student Age for Insert:");
				age = sc.nextInt();
				System.out.println("Enter " + i + " Student Name for Insert:");
				name = sc.next();
				pstmt = con.prepareStatement("Insert into Students(Id, Name, Age, Join_Date)values(?,?,?,SYSDATE)");
				boolean isPresent = IsPresent(id);
				if(isPresent) {
					System.err.println("Please check if the ID is available");
					sc.close();
				}else {
					pstmt.setInt(1, id);
					pstmt.setString(2, name.trim());
					pstmt.setInt(3, age);
				}
				pstmt.executeUpdate();
				record_count++;
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			i++;
		}
		System.err.println(record_count + " Records to be Inserted");
	}

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("1.Insert");
		System.out.println("2.Update");
		System.out.println("3.Delete");
		System.out.println("4.Read");
		System.out.println("5.Read All Students");
		System.out.print("Please enter the code for the desired operation: ");
		int oprtion = sc.nextInt();

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "admin");
			switch (oprtion) {
			case 1:
				Insert(sc);
				break;
			case 2:
				Update(sc);
				break;
			case 3:
				Delete(sc);
				break;
			case 4:
				Read(sc);
				break;
			case 5:
				ReadAll(sc);
				break;
			default:
				System.out.println("Press Correct operation code");
				break;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (st != null) {
					st.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				System.err.println("Error closing database resources: " + e.getMessage());
			}
		}

	}
}
