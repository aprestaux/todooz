package fr.todooz.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
                                   throws ServletException, IOException {
	   String name = request.getParameter("name");
	   
	   if (name == null) {
           name = (String) request.getSession(true).getAttribute("name");
        }

//	   PrintWriter writer = response.getWriter();
//
//       writer.write("<html><head></head><body>Hello " + name + " !</body></html>");
       
       request.setAttribute("name", request.getParameter("name"));

       request.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(request, response);
	   
//	   request.getRequestDispatcher("index.html").forward(request, response);
   }
}
