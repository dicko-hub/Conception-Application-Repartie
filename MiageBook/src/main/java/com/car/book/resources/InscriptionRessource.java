package com.car.book.resources;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import com.car.book.beans.Profils;
import com.car.book.dao.DaoException;
import com.car.book.dao.DaoFactory;
import com.car.book.dao.ProfilsDao;


@Path("/inscription")
public class InscriptionRessource {
	
	DaoFactory daoFactory = DaoFactory.getInstance();
	private ProfilsDao profilsDao = daoFactory.getProfilsDao();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String  verification (@Context UriInfo info) {
		boolean noException=true, noExist =true;
		String pseudo = info.getQueryParameters().getFirst("pseudo");
		
		try {
			
			Profils base = profilsDao.select(pseudo);
			if(base!=null)
				noExist=false;
			
		} catch (DaoException e) {
			noException = false;
		}
		
		if(noException && noExist) 
			return "yes";
		else 
			return "no";
	}
}
