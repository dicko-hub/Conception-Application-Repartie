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
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProfilsDao profilsDao;
	 
	 public void init() throws ServletException {
	        DaoFactory daoFactory = DaoFactory.getInstance();
	        this.profilsDao = daoFactory.getProfilsDao();
	    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean noExist = true, noException= true;
		Profils profils = new Profils();
		profils.setPseudo(request.getParameter("pseudo"));
		profils.setPassword(request.getParameter("password"));
		profils.setOnLigne("yes");
		LocalDateTime lastTime = LocalDateTime.now();
		profils.setLastTime(lastTime);
		Profils base =null;
		
		try {
			
			base = profilsDao.select(profils.getPseudo());
			if(base!=null && (base.getPassword().equals(profils.getPassword()))) {
				noExist=false;
				profilsDao.modifier(profils);
			}
				
		} catch (DaoException e) {
			noException = false;
		}
		
		if(noException && !noExist) {
			HttpSession session = request.getSession();
			session.setAttribute("moi", base);
			RequestDispatcher disp = request.getRequestDispatcher("/home");
			disp.forward(request, response); 
		}else {
			request.setAttribute("message","Veuillez reprendre");
			RequestDispatcher disp = request.getRequestDispatcher("/login.jsp");
			disp.forward(request, response); 
		}
	}

}
