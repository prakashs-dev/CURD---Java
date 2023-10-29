package com.frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Frame_Window {

	private JFrame frmStudentRecords;
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtAge;
	private JTextField txtCity;
	private JTable table;

	static Connection con = null;
	static PreparedStatement pstmt;
	static Statement st;
	static ResultSet rs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame_Window window = new Frame_Window();
					window.frmStudentRecords.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame_Window() {
		initialize();
		try {
			DbConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		loadTable();
	}

//	DataBase Connection
	public void DbConnect() throws Exception {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "admin");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	Load table data
	public void loadTable() {
		try {
			pstmt = con.prepareStatement("Select * From Student Order by ID ASC");
			rs = pstmt.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	Clear from after submit
	public void clear() {
		txtId.setText("");
		txtName.setText("");
		txtAge.setText("");
		txtCity.setText("");
	}

	static int number(String s) {
		return Integer.parseInt(s);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStudentRecords = new JFrame();
		frmStudentRecords.getContentPane().setForeground(Color.PINK);
		frmStudentRecords.setTitle("Student Records");
		frmStudentRecords.getContentPane().setBackground(new Color(255, 255, 255));
		frmStudentRecords.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Student Records");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblNewLabel.setBounds(453, 10, 194, 69);
		frmStudentRecords.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.setBackground(new Color(255, 255, 128));
		panel.setBounds(10, 95, 464, 408);
		frmStudentRecords.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Student ID");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_1.setBounds(23, 42, 83, 50);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Student Name");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(23, 102, 111, 50);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Student Age");
		lblNewLabel_1_2.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_1_2.setBounds(23, 162, 111, 50);
		panel.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel("Student City");
		lblNewLabel_1_3.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_1_3.setBounds(23, 231, 111, 50);
		panel.add(lblNewLabel_1_3);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtId.setBounds(140, 55, 267, 31);
		panel.add(txtId);
		txtId.setColumns(10);

		txtName = new JTextField();
		txtName.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtName.setColumns(10);
		txtName.setBounds(140, 115, 267, 31);
		txtName.requestFocus();
		panel.add(txtName);

		txtAge = new JTextField();
		txtAge.setToolTipText("number");
		txtAge.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtAge.setColumns(10);
		txtAge.setBounds(140, 175, 267, 31);
		panel.add(txtAge);

		txtCity = new JTextField();
		txtCity.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtCity.setColumns(10);
		txtCity.setBounds(140, 244, 267, 31);
		panel.add(txtCity);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtId.getText();
				String name = txtName.getText();
				String age = txtAge.getText();
				String city = txtCity.getText();

				if (name == null || name.isEmpty() || name.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Name");
					txtName.requestFocus();
					return;
				}
				if (age == null || age.isEmpty() || age.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Age");
					txtAge.requestFocus();
					return;
				}
				if (city == null || city.isEmpty() || city.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter City");
					txtCity.requestFocus();
					return;
				}
				if (id.isEmpty()) {
					try {
						pstmt = con.prepareStatement(
								"Insert into Student(ID, Name, Age, City)Values(student_id.NEXTVAL, ?, ?, ?)");
						pstmt.setString(1, name);
						pstmt.setInt(2, number(age));
						pstmt.setString(3, city);
						pstmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Successfully Inserted");
						clear();
						loadTable();
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		btnNewButton.setBackground(new Color(0, 255, 0));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton.setBounds(41, 327, 93, 31);
		panel.add(btnNewButton);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtId.getText();
				if (!id.isEmpty()) {
					int isSelect = JOptionPane.showConfirmDialog(null, "Are you Sure ? You want to delete", "Delete",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(isSelect == JOptionPane.YES_OPTION) {
						try {
							pstmt = con.prepareStatement("Delete from Student Where ID = ?");
							pstmt.setInt(1, number(id));
							pstmt.executeQuery();
							JOptionPane.showMessageDialog(null, "Successfully Deleted");
							clear();
							loadTable();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
					}
				}
			}
		});
		btnDelete.setBackground(new Color(255, 0, 0));
		btnDelete.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnDelete.setBounds(324, 327, 93, 31);
		panel.add(btnDelete);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtId.getText();
				String name = txtName.getText();
				String age = txtAge.getText();
				String city = txtCity.getText();

				if (name == null || name.isEmpty() || name.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Name");
					txtName.requestFocus();
					return;
				}
				if (age == null || age.isEmpty() || age.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Age");
					txtAge.requestFocus();
					return;
				}
				if (city == null || city.isEmpty() || city.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter City");
					txtCity.requestFocus();
					return;
				}

				if (!id.isEmpty()) {
					try {
						pstmt = con.prepareStatement("Update Student set Name = ?, Age = ?, City = ? Where ID = ?");
						pstmt.setString(1, name);
						pstmt.setInt(2, number(age));
						pstmt.setString(3, city);
						pstmt.setInt(4, number(id));
						pstmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Successfully Updated");
						clear();
						loadTable();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnUpdate.setBackground(new Color(192, 192, 192));
		btnUpdate.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnUpdate.setBounds(185, 327, 93, 31);
		panel.add(btnUpdate);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("");
		scrollPane.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setBounds(487, 96, 629, 407);
		frmStudentRecords.getContentPane().add(scrollPane);

		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				TableModel tm = table.getModel();
				txtId.setText(tm.getValueAt(index, 0).toString());
				txtName.setText(tm.getValueAt(index, 1).toString());
				txtAge.setText(tm.getValueAt(index, 2).toString());
				txtCity.setText(tm.getValueAt(index, 3).toString());
			}
		});
		table.setBorder(new EmptyBorder(0, 0, 0, 0));
		table.setBackground(new Color(255, 255, 255));
		table.setFont(new Font("SansSerif", Font.PLAIN, 14));
		table.setRowHeight(23);
		table.setForeground(Color.BLACK);
		scrollPane.setViewportView(table);
		frmStudentRecords.setBounds(100, 100, 1140, 590);
		frmStudentRecords.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
