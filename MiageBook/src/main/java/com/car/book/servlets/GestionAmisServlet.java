package com.car.book.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.book.beans.Amis;
import com.car.book.beans.Profils;
import com.car.book.dao.AmisDao;
import com.car.book.dao.DaoException;
import com.car.book.dao.DaoFactory;


/**
 * Servlet implementation class GestionAmisServlet
 */
public class GestionAmisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AmisDao amisDao;
	 
	public void init() throws ServletException {
	        DaoFactory daoFactory = DaoFactory.getInstance();
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
		String page="";
		try {
			Profils moi=(Profils) session.getAttribute("moi");
			int idAmis =Integer.parseInt(request.getParameter("idAmis"));
			page = request.getParameter("page");
			boolean status = Boolean.parseBoolean(request.getParameter("status"));
			
			Amis amis = new Amis();
			amis.setProfils(moi.getId());
			amis.setAmis(idAmis);
			
			Amis verso = new Amis();
			verso.setProfils(idAmis);
			verso.setAmis(moi.getId());
			
			if(status) {
				amisDao.supprimer(amis);
				amisDao.supprimer(verso);
			}else {
				
				amisDao.ajouter(amis);
				amisDao.ajouter(verso);
			}
			
		} catch (DaoException e) {
			noException=false;
		}
		
		if(noException) {
				if(page.equals("profils")) {
					RequestDispatcher disp = request.getRequestDispatcher("/profils");
					disp.forward(request, response); 
				}else {
					RequestDispatcher disp = request.getRequestDispatcher("/utilisateurs");
					disp.forward(request, response); 
				}
		}else {
			request.setAttribute("messageUser","Un probleme est survenu sur la base de donnée");
			RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/utilisateurs.jsp");
			disp.forward(request, response); 
		}
	}
   }
}
