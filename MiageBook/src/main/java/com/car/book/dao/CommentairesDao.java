package com.car.book.dao;

import java.util.List;

import com.car.book.beans.Commentaires;


public interface CommentairesDao {
	
	void ajouter(Commentaires commentaire) throws DaoException;
	void modifier(Commentaires commentaire) throws DaoException;
	void supprimer(Commentaires commentaire) throws DaoException;
	List<Commentaires> lister(int status) throws DaoException;

}
