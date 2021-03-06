package by.epam.training.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.web.controller.command.Command;
import by.epam.training.web.controller.command.CommandProvider;
import by.epam.training.web.exception.ServiceException;

/**
 * Servlet implementation class LoginTest
 */
public class WebController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final CommandProvider provider = new CommandProvider(); 
    private static Logger logger = LogManager.getLogger(WebController.class);
       
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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Command command;
		String commandParameter = request.getParameter(Command.requestParameter);
		if(request.getSession(true).getAttribute(Command.changeLocaleParameter) != null) {
			String changeLocaleParameter = request.getSession(true).getAttribute(Command.changeLocaleParameter).toString();
			request.getSession(true).removeAttribute(Command.changeLocaleParameter);
			commandParameter = changeLocaleParameter.isEmpty() ? commandParameter : changeLocaleParameter;
		}
		command = provider.getCommand(commandParameter);
		try {
			command.execute(request, response);
		} catch (ServiceException e) {
			logger.error(e);
		}
	}
}
