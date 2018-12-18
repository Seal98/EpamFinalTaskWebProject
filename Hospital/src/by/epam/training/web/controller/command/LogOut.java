package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOut implements Command {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher rd = request.getRequestDispatcher(Command.mainPageJSP);
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}
}
