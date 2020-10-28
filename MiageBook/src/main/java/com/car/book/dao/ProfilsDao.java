package com.car.book.dao;

import java.util.List;

import com.car.book.beans.Profils;;

public interface ProfilsDao {
	
	void ajouter(Profils profils) throws DaoException;
	void modifier(Profils profils) throws DaoException;
	void supprimer(Profils profils) throws DaoException;
	List<Profils> lister() throws DaoException;
	Profils select(String pseudo) throws DaoException;
	

}
