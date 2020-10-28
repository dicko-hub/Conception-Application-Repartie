package com.car.book.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.book.beans.Commentaires;
import com.car.book.beans.Profils;
import com.car.book.beans.Status;
import com.car.book.dao.CommentairesDao;
import com.car.book.dao.DaoException;
import com.car.book.dao.DaoFactory;
import com.car.book.dao.ProfilsDao;
import com.car.book.dao.StatusDao;

/**
 * Servlet implementation class GestionStatusServlet
 */
public class GestionStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StatusDao statusDao;
	private ProfilsDao profilsDao;
	private CommentairesDao commentairesDao;
	 
	 public void init() throws ServletException {
	        DaoFactory daoFactory = DaoFactory.getInstance();
	        this.statusDao = daoFactory.getStatusDao();
	        this.profilsDao = daoFactory.getProfilsDao();
	        this.commentairesDao = daoFactory.getCommentairesDao();
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
			
			if(request.getParameter("statusId")==null || request.getParameter("statusId")==null ) {
				RequestDispatcher disp = request.getRequestDispatcher("/home");
				disp.forward(request, response); 
			}
			
		boolean noException = true;
		int statusId =Integer.parseInt(request.getParameter("statusId"));
		String pseudo = request.getParameter("statusPseudo");
		
		Status status = null;
		ArrayList<Commentaires> commentaire = null;
		ArrayList<Profils> users = null;
		try {
			
			// recupereation de touts les utilisateurs
			users = (ArrayList<Profils>) profilsDao.lister();
	
			status = statusDao.select(statusId);
			status.setPseudo(pseudo);
			commentaire = (ArrayList<Commentaires>) commentairesDao.lister(statusId);
			
			//recuperation des pseudos de ceux a qui appartiennent les commentaires
			for(Profils user : users) {
				for(Commentaires post : commentaire) {
					if(user.getId()==post.getProprietaire())
						post.setPseudo(user.getPseudo());
				}
			}
				
		} catch (DaoException e) {
			noException=false;
		}
		
		if(!noException) 
			request.setAttribute("messageUser","Un probleme est survenu sur la base de donnée");
		
			request.setAttribute("status",status );
			request.setAttribute("commentaires", commentaire);
			RequestDispatcher disp = request.getRequestDispatcher("WEB-INF/detail-status.jsp");
			disp.forward(request, response); 
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		boolean  noException= true;
		Profils moi =(Profils) session.getAttribute("moi");
		
		Commentaires commentaire = new Commentaires();
		commentaire.setTexte(request.getParameter("texte"));
		LocalDateTime lastTime = LocalDateTime.now();
		commentaire.setDate(lastTime);
		int monId= moi.getId();
		commentaire.setProprietaire(monId);
		commentaire.setStatus(Integer.parseInt(request.getParameter("statusId")));
		
		try {
			
				commentairesDao.ajouter(commentaire);
		} catch (DaoException e) {
			noException = false;
		}
		
		if(!noException ) 
			request.setAttribute("message","il ya un probleme cote serveur  ");
		
			doGet(request, response);
		
			
	}

}
