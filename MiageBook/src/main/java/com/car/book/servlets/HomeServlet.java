package com.car.book.servlets;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends HttpServlet {
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
				
			boolean noException = true;
			ArrayList<Status> status = null;
			
			try {
				
				Profils moi =(Profils) session.getAttribute("moi");	
				
				//recherche des utilisateurs amis avec le profils connectee et recuperation des stations 
				status = (ArrayList<Status>) statusDao.listerFriends(moi.getId());
				
					
			} catch (DaoException e) {
				noException=false;
			}
			
			if(!noException) 
				request.setAttribute("messageUser","Un probleme est survenu sur la base de donnée");
			
				request.setAttribute("status",status );
				RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/home.jsp");
				disp.forward(request, response); 
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		boolean noException = true;
		ArrayList<Status> status = null;
		
		try {
			
			Profils moi =(Profils) session.getAttribute("moi");	
			
			//recherche des utilisateurs amis avec le profils connectee et recuperation des stations 
			status = (ArrayList<Status>) statusDao.listerFriends(moi.getId());
			
			
		} catch (DaoException e) {
			noException=false;
		}
		
		if(!noException) 
			request.setAttribute("messageUser","Un probleme est survenu sur la base de donnée");
		
			request.setAttribute("status",status );
			RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/home.jsp");
			disp.forward(request, response); 
	}

}
