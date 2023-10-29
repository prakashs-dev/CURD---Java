package com.frame;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class DbUtils {
	
	public static TableModel resultSetToTableModel(ResultSet rs) {
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int noOfColumns = metaData.getColumnCount();
			Vector colNames = new Vector();
			
//			get Column names
			for (int column = 0; column < noOfColumns; column++) {
				colNames.addElement(metaData.getColumnLabel(column + 1));
			}
			
//			get all rows
			Vector rows = new Vector();
			while (rs.next()) {
				Vector newRow = new Vector();
				for (int i = 1; i <= noOfColumns; i++) {
					newRow.addElement(rs.getObject(i));
				}
				rows.addElement(newRow);
			}
			
			return new DefaultTableModel(rows,colNames);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
