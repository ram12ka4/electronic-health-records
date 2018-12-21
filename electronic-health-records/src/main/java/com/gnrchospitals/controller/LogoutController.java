package com.gnrchospitals.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;;

@WebServlet(urlPatterns = "/logout.do")
public class LogoutController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID")) {
					System.out.println("JSESSIONID=" + cookie.getValue());
				}

				/*cookie.setMaxAge(0);
				response.addCookie(cookie);*/
			}

			HttpSession session = request.getSession(false);
		
			
			//System.out.println("Logout User Session = " + session.getAttribute("user"));
			
			
			if (session != null) {
				session.invalidate();
				
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Cache-Control", "no-store");
				response.setHeader("Pragma", "no-cache");
				response.setDateHeader("Expires", 0);
			}

			response.sendRedirect("login.do");

		}

	}

}
