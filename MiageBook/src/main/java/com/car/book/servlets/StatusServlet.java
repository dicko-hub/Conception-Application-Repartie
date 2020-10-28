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
import com.car.book.beans.Status;
import com.car.book.dao.DaoException;
import com.car.book.dao.DaoFactory;
import com.car.book.dao.StatusDao;

/**
 * Servlet implementation class StatusServlet
 */
public class StatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StatusDao statusDao;
	 
	 public void init() throws ServletException {
	        DaoFactory daoFactory = DaoFactory.getInstance();
	        this.statusDao = daoFactory.getStatusDao();
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
			
				RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/status.jsp");
				disp.forward(request, response); 
			
			}
		}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		boolean  noException= true;
		Status status = new Status();
		status.setTitre(request.getParameter("titre"));
		status.setTexte(request.getParameter("texte"));
		LocalDateTime lastTime = LocalDateTime.now();
		status.setDate(lastTime);
		int monId= ((Profils) session.getAttribute("moi")).getId();
		status.setProprietaire(monId);
		
		try {
			
				statusDao.ajouter(status);
		} catch (DaoException e) {
			noException = false;
		}
		
		if(!noException ) 
			request.setAttribute("message","Cet pseudo exit deja ");
		
			RequestDispatcher disp = request.getRequestDispatcher("/home");
			disp.forward(request, response); 
		
			
		}

}
