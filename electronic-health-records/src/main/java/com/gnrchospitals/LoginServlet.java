package com.gnrchospitals;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private LoginService service = new LoginService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String user = request.getParameter("user") == null ? "" : request.getParameter("user");
		String password = request.getParameter("password") == null ? "" : request.getParameter("password");
		String location = request.getParameter("location") == null ? "" : request.getParameter("location");

		System.out.println("User Name : " + user);
		System.out.println("Password : " + password);
		System.out.println("Location : " + location);

		boolean isValid = service.isLoginValid(location, user, password);
		
		System.out.println("User Valid : " + isValid);

		if (isValid) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			// setting session to expiry in 30 minutes
			session.setMaxInactiveInterval(30 * 60);
			Cookie userName = new Cookie("user", user);
			response.addCookie(userName);
			// Get the encoded URL string
			String encodeURL = response.encodeRedirectURL("/WEB-INF/views/dashboard.jsp");
			// response.sendRedirect(encodeURL);
			request.getRequestDispatcher(encodeURL).forward(request, response);

		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp");
			request.setAttribute("msg", "Invalid Credentials! (user or password or location)");
			// request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request,
			// response);
			rd.include(request, response);

		}

	}

}