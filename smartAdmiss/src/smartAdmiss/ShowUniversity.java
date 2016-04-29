package smartAdmiss;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowUniversity
 */
public class ShowUniversity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowUniversity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name").trim();
		String lname=request.getParameter("lname").trim();
		name=name+" "+lname;
		String course = request.getParameter("course");
		System.out.println("The name is"+name+" "+lname +"------------"+"the Course"+course );
	
		 StringBuilder sb = UniversityMatching.getUniv(name,course);//new StringBuilder("a *** b *** c" );
		    
		    String[] myArray = sb.toString().split("\\*\\*\\*");
		   for(int i=0;i<myArray.length;i++)
		   {
			   System.out.println(myArray[i]);
		   }
        response.setContentType("text/html");
        out.println("<html><head><title>University");
        out.println("</title></head><body>You are eligible for these university");
        out.println("<table >");
        for(int i=0;i<myArray.length;i++)
        {
        	out.println("<tr><td>");
        	out.println(myArray[i]);
        	out.println("</td></tr>");
        }
		out.println("</body>");
		out.println("</html>");
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
