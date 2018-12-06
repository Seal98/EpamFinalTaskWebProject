package by.epam.training.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.controller.command.Command;
import by.epam.training.web.controller.command.CommandProvider;

/**
 * Servlet implementation class LoginTest
 */
public class WebController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final CommandProvider provider = new CommandProvider(); 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Command command;
		String commandParameter = request.getParameter("requestParameter");
		System.out.println(commandParameter);
		command = provider.getCommand(commandParameter);
		command.execute(request, response);
	}
}
