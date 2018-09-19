package com.gnrchospitals.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gnrchospitals.dao.DatabaseDao;
import com.gnrchospitals.dao.UserDao;
import com.gnrchospitals.daoimpl.DatabaseDaoImpl;
import com.gnrchospitals.daoimpl.UserDaoImpl;
import com.gnrchospitals.dto.User;

@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String id = request.getParameter("user") == null ? "" : request.getParameter("user");
		String password = request.getParameter("password") == null ? "" : request.getParameter("password");
		String location = request.getParameter("location") == null ? "" : request.getParameter("location");

		System.out.println("User ID : " + id);
		System.out.println("Password : " + password);
		System.out.println("Location : " + location);

		DatabaseDao databaseDo = new DatabaseDaoImpl();
		boolean loginDatabaseValidate = databaseDo.findByLocation(location);
		
		System.out.println("Login Database Validation " + loginDatabaseValidate);

		User userBean = new User();
		userBean.setId(id);
		userBean.setPassword(password);

		UserDao userdao = new UserDaoImpl();
		boolean userValidate = userdao.authenticateUser(userBean);

		System.out.println("User Valid : " + userValidate);
		System.out.println("User Name : " + userBean.getUsername());
		

		if (userValidate) {
			HttpSession session = request.getSession();
			session.setAttribute("user", id);
			session.setAttribute("username", userBean);
			// setting session to expiry in 30 minutes
			session.setMaxInactiveInterval(30 * 60);
			Cookie userName = new Cookie("user", id);
			response.addCookie(userName);
			// Get the encoded URL string
			//String encodeURL = response.encodeRedirectURL("/WEB-INF/views/dashboard.jsp");
			String encodeURL = response.encodeRedirectURL("/patient.portal");
			 response.sendRedirect(encodeURL);
			//request.getRequestDispatcher(encodeURL).forward(request, response);

		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp");
			request.setAttribute("msg", "Invalid Credentials! (user or password or location)");
			// request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request,
			// response);
			rd.include(request, response);

		}

	}

}