package com.gnrchospitals.controller;

import java.io.IOException;
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
import com.gnrchospitals.dao.LoginDao;
import com.gnrchospitals.daoimpl.DatabaseDaoImpl;
import com.gnrchospitals.daoimpl.LoginDaoImpl;

@WebServlet(urlPatterns = "/login.do")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private LoginDao loginDao = new LoginDaoImpl();

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

		try {

			// Fetch login date and time
			DateFormat df = new SimpleDateFormat("d-M-yyyy h:m a");
			Date date = new Date();
			String today = df.format(date);

			// Fetch ip address
			// InetAddress localhost = InetAddress.getLocalHost();
			String ipAddress = request.getRemoteAddr();

			System.out.println("User ID : " + id);
			System.out.println("Password : " + password);
			System.out.println("Location : " + location);
			System.out.println("Login Time : " + today);
			System.out.println("Login From : " + ipAddress);

			DatabaseDao databaseDo = new DatabaseDaoImpl();
			boolean loginDatabaseValidate = databaseDo.findByLocation(location);
			System.out.println("Login Database Validation " + loginDatabaseValidate);

			String userValidation = loginDao.validateUser(id, password);
			System.out.println("User Validation : " + userValidation.substring(1));

			if (userValidation.contains("1")) {

				HttpSession oldSession = request.getSession(false);
				if (oldSession != null) {
					oldSession.invalidate();
				}

				HttpSession newSession = request.getSession(true);
				newSession.setAttribute("user", id);
				newSession.setAttribute("userName", userValidation.substring(1));
				newSession.setAttribute("loginTime", today);
				newSession.setAttribute("loginFrom", ipAddress);
				newSession.setAttribute("location", location);

				// setting session to expiry in 30 minutes
				newSession.setMaxInactiveInterval(30 * 60);
				Cookie userName = new Cookie("user", id);
				userName.setHttpOnly(true);
				userName.setSecure(true);
				response.addCookie(userName);
				// Get the encoded URL string
				String encodeURL = response.encodeRedirectURL("patient.portal");
				response.sendRedirect(encodeURL);

			} else {
				request.setAttribute("msg", "Invalid Credentials! (user or password or location)");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp");
				rd.include(request, response);

			}

		} catch (Exception ex) {
			sendErrorReirect(request, response, "/WEB-INF/views/error.jsp", ex);
		}

	}

	protected void sendErrorReirect(HttpServletRequest request, HttpServletResponse response, String errroPageURL,
			Throwable e) throws ServletException, IOException {
		request.setAttribute("javax.servlet.jsp.jspException", e);
		getServletConfig().getServletContext().getRequestDispatcher(errroPageURL).forward(request, response);

	}

}