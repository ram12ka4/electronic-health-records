package com.gnrchospitals.servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		
		// Fetch login date and time
		DateFormat df = new SimpleDateFormat("d-M-yyyy h:m a");
		Date date =new Date();
		String today = df.format(date);
		
		// Fetch ip address
		InetAddress localhost = InetAddress.getLocalHost();
		String ipAddress = localhost.getHostAddress();
		

		System.out.println("User ID : " + id);
		System.out.println("Password : " + password);
		System.out.println("Location : " + location);
		System.out.println("Login Time : " + today);
		System.out.println("Login From : " + ipAddress);

		DatabaseDao databaseDo = new DatabaseDaoImpl();
		boolean loginDatabaseValidate = databaseDo.findByLocation(location);
		
		System.out.println("Login Database Validation " + loginDatabaseValidate);

		User userBean = new User();
		userBean.setId(id);
		userBean.setPassword(password);

		UserDao userdao = new UserDaoImpl();
		boolean userValidation = userdao.authenticateUser(userBean);

		System.out.println("User Valid : " + userValidation);
		System.out.println("User Name : " + userBean.getUsername());
		

		if (userValidation) {
			HttpSession session = request.getSession();
			session.setAttribute("user", id);
			session.setAttribute("userName", userBean.getUsername());
			session.setAttribute("loginTime", today);
			session.setAttribute("loginFrom", ipAddress);
			session.setAttribute("location", location);
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