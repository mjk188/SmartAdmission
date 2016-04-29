package smartAdmiss;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StudentDetail
 */
@WebServlet("/StudentDetail")
public class StudentDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public StudentDetail() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("hello");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String fname = request.getParameter("q4_name[first]");
		String lname = request.getParameter("q4_name[last]");
		String name=fname+" "+lname;
		String month = request.getParameter("q6_dateOf[month]");
		String year = request.getParameter("q6_dateOf[year]");
		String day = request.getParameter("q6_dateOf[day]");
		String dob = day+"/"+month+"/"+year;
		String sex = request.getParameter("q5_sex");
		String contact = request.getParameter("q7_contactNumber");
		String appliedfor = request.getParameter("q11_applyFor");
		String bemarks = request.getParameter("q10_btechbeMarks");
		String gatescore = request.getParameter("q12_gateScore");
		String xmarks = request.getParameter("q8_xthMarks8");
		String xiimarks = request.getParameter("q9_xiiMarks");
		
		System.out.println(name + "  "+ dob + "  "+  sex+" "+ contact +" "+ appliedfor +" "+ bemarks+" "+gatescore+" "+ xmarks+" "+ xiimarks);
		
		AddIndividual a=new AddIndividual();
		 System.out.println(a);
		try {
     		 System.out.println("chal ja re pehla");
			a.addStudent(name,dob,sex,contact,appliedfor,bemarks,gatescore,xmarks,xiimarks);
			request.setAttribute("name",name);
			request.setAttribute("lname",lname );
			request.setAttribute("course",appliedfor);
//response.sendRedirect("Success.html");
			 ServletContext sc = getServletContext();
			
RequestDispatcher rd = sc.getRequestDispatcher("/Success.jsp");
rd.forward(request, response);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(name.isEmpty()||Addr.isEmpty()||age.isEmpty()||Qual.isEmpty()||Persent.isEmpty())
//		{
//			RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
//			out.println("<font color=red>Please fill all the fields</font>");
//			rd.include(request, response);
//		}
//		else
//		{
      	 
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
