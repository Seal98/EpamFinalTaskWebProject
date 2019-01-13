package by.epam.training.web.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.exception.ServiceException;

public class ChangeLocale implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("Here1");
		request.getSession(true).setAttribute("locale", request.getParameter("requestParameter"));
		Object localeAttribute = request.getSession(true).getAttribute("currentPage");
		String currentPage = localeAttribute != null ? localeAttribute.toString() : "index.jsp";
		System.out.println("Current page: " + currentPage);
		if(currentPage.compareTo("index.jsp") == 0) {
			response.sendRedirect("index.jsp");
			System.out.println(1);
		} else if(currentPage.compareTo("patientPage") == 0 || currentPage.compareTo("therapistPage") == 0 || currentPage.compareTo("executorPage") == 0) {
			request.getSession(true).setAttribute("changeLocaleParameter", "SIGN_IN");
			request.getRequestDispatcher("authorization").forward(request, response);
			System.out.println(2);
		} else if(currentPage.compareTo("signUp") == 0) {
			response.sendRedirect("signUp");
			System.out.println(3);
		}

	}
}
