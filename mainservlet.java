package com.ss.info;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;



public class test1 extends HttpServlet{
	
	
	int value=0;
	private static Logger demologger=LogManager.getLogger(test1.class.getName());
	
	String UPDATE_USERS = "update employee set username='?',password='?' where emp_id="+value;
	  ArrayList<employee> heads = new ArrayList<employee>();
	String LOGIN="select * from employee e inner join department d where e.dept_id=d.dept_id;";
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
	
		
		Gson gson=new Gson();
        Scanner sc= new Scanner(System.in);
		String str= sc.nextLine(); 
    user us=gson.fromJson(str,user.class); 
		
String username=us.name;
String password =us.password;

String dbusername="";
String dbpassword="";
int flag=0;

try (Connection con = getconnection();
			PreparedStatement preparedStatement = con.prepareStatement(LOGIN)) {
	    // Statement stmt = con.createStatement();
	
	    ResultSet rs = preparedStatement.executeQuery(LOGIN);
	    PrintWriter out=response.getWriter();
	    while(rs.next()){
	    	dbusername=rs.getString("username");
	    	dbpassword=rs.getString("password");
	    if ((username.equals(dbusername)) && (password.equals(dbpassword))) {
	    	//String name  = rs.getString("emp_name");
			//out.println("ID: " + name + "<br>");
			out.print("Hey you have successfully logged in wait for to update service");
			int dept  = rs.getInt("dept_id");
			int a=104;
			if(dept==a)
			{
				System.out.print("you are a admin");
				String l=admin();
				out.print(l);
				
				flag=1;
			
				break;
				
				
			}
			else {
				employee head=new employee();
				System.out.print("you are a normal employee");
				//String name=rs.getString("emp_name");
				//String user=rs.getString("username");
				//String passw=rs.getString("password");
				int emp_id=rs.getInt("emp_id");
				value=rs.getInt(1);
				head.setEmp_id(rs.getInt(1));
				 
				    head.setEmp_name(rs.getString(2));
				    head.setDept_id(rs.getInt(3));
				    head.setEmp_address(rs.getString(4));
				    head.setDept_name(rs.getString(5));
				    head.setUsername(rs.getString(6));
				    head.setPassword(rs.getString(7));
				    heads.add(head);
				    String json = new Gson().toJson(heads);   
				    out.print(json);
				 
	
				    
				    
			
				
				//out.println("name: " + name + "<br>");
				//out.println("username is: " + user + "<br>");
				//out.println("password is: " + passw + "<br>");
				//out.println("department name is: " + deptname + "<br>");
				
			}
			flag=1;
			break;
			
}
	    
	 
	    }
	 
	    if(flag==0)
	    	out.print("Please enter correct username and password to continue...");
	   
}
catch(Exception e) {
	   System.out.print(e);
	
	    
	  
}
if(flag==1)
{
	HttpSession session=request.getSession();
	session.setAttribute("employee id", value);
	RequestDispatcher ds = request.getRequestDispatcher("update");
	ds.include(request, response);
}
}
	

	public String admin()
	{
		  ArrayList<employee> hh = new ArrayList<employee>();
		 String js = null;
		 try {
			   Connection con=getconnection();
			   PreparedStatement preparedStatement1 = con.prepareStatement(LOGIN);
				ResultSet rs1 = preparedStatement1.executeQuery(LOGIN);
								while(rs1.next()) {
					employee h=new employee();
					 h.setEmp_name(rs1.getString(2));
					 h.setDept_id(rs1.getInt(3));
					 h.setEmp_id(rs1.getInt(1));
					 h.setCount(rs1.getInt(10));
					 h.setDept_name(rs1.getString(5));
				     hh.add(h);
				
				  }
				 js = new Gson().toJson(hh); 
		 }
			   catch(Exception ew)
			   {
				   System.out.print(ew);
			   }
		 return js;
		 
}
	public void update(int value)
	
	{
		Gson gson=new Gson();
		Scanner sc= new Scanner(System.in);
		String str= sc.nextLine(); 
		user u=gson.fromJson(str,user.class);
		String username=u.name;
		String password=u.password;
		try {
			   Connection con=getconnection();
			   PreparedStatement preparedStatement1 = con.prepareStatement(UPDATE_USERS);
				preparedStatement1.executeQuery(UPDATE_USERS);
				System.out.print("update executing......");
		
	}
		catch(Exception e)
		{
			System.out.print(e);
		}
}
}
