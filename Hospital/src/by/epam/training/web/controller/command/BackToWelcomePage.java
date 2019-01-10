package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BackToWelcomePage implements Command {
    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(Command.mainPageJSP);
		request.setAttribute(Command.answerAttribute, null);
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}

}
