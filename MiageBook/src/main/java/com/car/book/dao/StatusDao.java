package com.car.book.dao;

import java.util.List;

import com.car.book.beans.Status;

public interface StatusDao {
	
	void ajouter(Status status) throws DaoException;
	void modifier(Status status) throws DaoException;
	void supprimer(Status status) throws DaoException;
	List<Status> listerFriends(int profils) throws DaoException;
	List<Status> listerMy(int profils) throws DaoException;
	Status select(int status) throws DaoException;

}
