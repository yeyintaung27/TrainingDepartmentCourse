package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Course;
import model.SelectedCourseDao;
import model.SendEmail;

public class RegisterSessionServlet extends HttpServlet {

	@SuppressWarnings("static-access")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String register_name = req.getParameter("name");
		String register_email = req.getParameter("email");
		String register_address = req.getParameter("address");
		String register_course = req.getParameter("course");
	
		System.out.println(" The Register Name is " + register_name);
		System.out.println(" The Register Email is " + register_email);
		System.out.println(" The Register Address is " + register_address);
		System.out.println(" The Register Course is " + register_course);

		SelectedCourseDao courseReg = new SelectedCourseDao();

		// Call the Method to save the register course
		Course reg_course = courseReg.saveRegisterCourse(register_name, register_course, register_email, register_address);

		
		// Send Email To User
		SendEmail mm=new SendEmail();
		String sub="Information About the Registered Session";
				
		if(register_course.equals("Microsoft Word (Morning)") || 
		   register_course.equals("Excel (Morning)") || 
		   register_course.equals("PowerPoint (Morning)") || 
		   register_course.equals("Techical skills (Morning)") || 
		   register_course.equals("Soft skills (Morning)")){
			
			String message="Hello! This is from training department. Thank you for registering to attend " +
					"the course. Your selected course is "+ register_course+ 
					". Since you choose to attend morning session, your schedule is from 7:00AM to 9:00AM " +
					"in the morning.";
			 mm.send("cholwin.elevatemm@gmail.com","CHOelevate6",register_email,sub,message); 
		}
		else{
			String message="Hello! This is from training department. Thank you for registering to attend " +
					"the course. Your selected course is "+ register_course+ 
					". Since you choose to attend evening session, your schedule is from 4:00PM to 6:00PM " +
					"in the evening.";
			 mm.send("cholwin.elevatemm@gmail.com","CHOelevate6",register_email,sub,message); 
		}
	
		req.setAttribute("register_course", reg_course);

		// Dispatch the JSP
		RequestDispatcher rd = req.getRequestDispatcher("thankyou.jsp");
		rd.forward(req, resp);
		
	}
}
