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
import com.car.book.dao.AmisDao;
import com.car.book.dao.DaoException;
import com.car.book.dao.DaoFactory;
import com.car.book.dao.ProfilsDao;

/**
 * Servlet implementation class ProfilsServlet
 */
public class ProfilsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProfilsDao profilsDao;
	private AmisDao amisDao;
	 
	public void init() throws ServletException {
	        DaoFactory daoFactory = DaoFactory.getInstance();
	        this.profilsDao = daoFactory.getProfilsDao();
	        this.amisDao = daoFactory.getAmisDao();
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
		ArrayList<Profils> users = null;
		try {
			users = (ArrayList<Profils>) profilsDao.lister();
			Profils moi=(Profils) session.getAttribute("moi");
			for(Profils user : users) {
				if((amisDao.select(moi, user)!=null)){
					user.setAmis(true);
				}	
			}
				
		} catch (DaoException e) {
			noException=false;
		}
		
		if(noException) {
			request.setAttribute("users",users );
			RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/profils.jsp");
			disp.forward(request, response); 
		}else {
			request.setAttribute("messageUser","Un probleme est survenu sur la base de donnée");
			RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/home.jsp");
			disp.forward(request, response); 
		}
	}
  }
}
