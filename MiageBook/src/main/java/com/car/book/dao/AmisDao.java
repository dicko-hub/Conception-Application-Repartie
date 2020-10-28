package com.car.book.dao;


import java.util.List;

import com.car.book.beans.Amis;
import com.car.book.beans.Profils;

public interface AmisDao {
	
	void ajouter(Amis amis) throws DaoException;
	void supprimer(Amis amis) throws DaoException;
	List<Amis> lister(int profils) throws DaoException;
	Amis select(Profils pro1, Profils pro2) throws DaoException;

}
