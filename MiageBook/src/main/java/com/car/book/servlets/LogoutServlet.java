package com.car.book.servlets;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.book.beans.Profils;
import com.car.book.dao.DaoException;
import com.car.book.dao.DaoFactory;
import com.car.book.dao.ProfilsDao;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProfilsDao profilsDao;
	 
	 public void init() throws ServletException {
	        DaoFactory daoFactory = DaoFactory.getInstance();
	        this.profilsDao = daoFactory.getProfilsDao();
	    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("moi") == null) {
			RequestDispatcher disp = request.getRequestDispatcher("login.jsp");
			disp.forward(request, response); 
		}else {
			
		boolean  noException= true;
		Profils profils = (Profils) session.getAttribute("moi");
		profils.setOnLigne("no");
		LocalDateTime lastTime = LocalDateTime.now();
		profils.setLastTime(lastTime);
		session.invalidate();
		
		try {
			profilsDao.modifier(profils);
		} catch (DaoException e) {
			noException = false;
		}
		
		if(noException) {
			RequestDispatcher disp = request.getRequestDispatcher("/login.jsp");
			disp.forward(request, response); 
		}else {
			request.setAttribute("message","Cet pseudo exit deja ");
			RequestDispatcher disp = request.getRequestDispatcher("home");
			disp.forward(request, response); 
		}
		
	 }
   }
}
