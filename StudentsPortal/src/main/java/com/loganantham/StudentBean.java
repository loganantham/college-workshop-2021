package com.loganantham;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.loganantham.student.model.Student;

@ManagedBean(name="student")
@SessionScoped
public class StudentBean implements Serializable{
 
	//resource injection
	@Resource(name="jdbc/studentsportal")
	private DataSource ds;
	
	//if resource inject is not support, you still can get it manually.
	/*public CustomerBean(){
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/mkyongdb");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}*/
	
	//connect to DB and get customer list
	public List<Student> getStudentList() throws SQLException{
		
		if(ds==null)
			throw new SQLException("Can't get data source");
		
		//get database connection
		Connection con = ds.getConnection();
		
		if(con==null)
			throw new SQLException("Can't get database connection");
		
		PreparedStatement ps 
			= con.prepareStatement(
				"select roll_no, name, address, created_date from student"); 
		
		//get customer data from database
		ResultSet result =  ps.executeQuery();
		
		List<Student> list = new ArrayList<Student>();
		
		while(result.next()){
			Student cust = new Student();
			
			cust.setRollNo(result.getLong("roll_no"));
			cust.setName(result.getString("name"));
			cust.setAddress(result.getString("address"));
			cust.setCreated_date(result.getDate("created_date"));
			
			//store all data into a List
			list.add(cust);
		}
			
		return list;
	}
}
