package by.epam.training.web.controller.command.authorizer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.web.bean.User;
import by.epam.training.web.exception.ServiceException;

public interface UserAuthorizer {
	
	public void signIn(HttpServletRequest request, HttpServletResponse response, User existingUser) throws ServletException, IOException, ServiceException;
	
}
