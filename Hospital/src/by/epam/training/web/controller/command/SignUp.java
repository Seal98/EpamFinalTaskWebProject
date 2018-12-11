package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUp implements Command {

	//private static final Logger logger = LogManager.getLogger("emptyFilePath");
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//logger.error("TEST");
		RequestDispatcher rd = request.getRequestDispatcher("jsp/signUp.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
