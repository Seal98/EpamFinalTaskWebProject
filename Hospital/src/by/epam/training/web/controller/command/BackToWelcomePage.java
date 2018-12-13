package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BackToWelcomePage implements Command {

    private static Logger logger = LogManager.getLogger(BackToWelcomePage.class);
    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd = request.getRequestDispatcher(Command.mainPageJSP);
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			logger.info(e.getMessage());
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}

}
