package com.ss.info;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
public class update extends HttpServlet{
	int value;
	String password;
	 String username;
	
	Logger demologger=LogManager.getLogger(test1.class.getName());

	String UPDATE="update employee set username="+username+",password="+password+" where emp_id=101";
	public Connection getconnection()
	{
		   Connection con=null;
	  try
	  {
	 	 Class.forName("com.mysql.jdbc.Driver");
	      con=DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","password");
	  }
	catch(Exception e)
	  {
		 System.out.print(e);

	}
	  return con;
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException  { 
		HttpSession session=request.getSession();
		int id=(int)session.getAttribute("employee id");
	    PrintWriter out=response.getWriter();
	    out.print("<br>update service called<br>");
	    System.out.print(id);
        value=id;
        try {
        	Gson gson=new Gson();
    		Scanner sc= new Scanner(System.in);
    		String str= sc.nextLine();
    		user u=gson.fromJson(str,user.class);
           username=u.name;
    		password=u.password;
			   Connection con=getconnection();
			   PreparedStatement preparedStatement = con.prepareStatement(UPDATE);
			  // preparedStatement.setString(1,username);
			   //preparedStatement.setString(2,password);
				preparedStatement.executeUpdate(UPDATE);
				System.out.print("update executing......");
				demologger.info("updating sql..");
		
	}
		catch(Exception e)
		{
			System.out.print(e);
		}
		
		
	}
	}
