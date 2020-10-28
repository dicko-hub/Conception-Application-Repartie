package com.car.book.beans;


import java.io.*;
import java.sql.*;

import com.car.book.dao.DaoException;
import com.car.book.dao.DaoFactory;
import com.car.book.dao.ProfilsDao;

import oracle.jdbc.driver.OracleDriver;

public class Profession{
//URL de connexion
private String url = "jdbc:mysql://localhost:3306/book";
//Nom du user
private String user = "root";
//Mot de passe de l'utilisateur
private String passwd = "";
//Objet Connection
private static Connection connect;
 
//Constructeur priv?
private Profession(){
  try {
    connect = DriverManager.getConnection(url, user, passwd); 
    connect.setAutoCommit(false);
  } catch (SQLException e) {
    e.printStackTrace();
  }
}
 
//M?thode qui va nous retourner notre instance et la cr?er si elle n'existe pas
 public static Connection getInstance(){
  if(connect == null){
    new Profession();
  }
  return connect;   
}   
 
public static void main(String[] args) throws Exception{

//insertion d'une insert dans la base oracle
// Requête en utilisant un Statement
Connection c = getInstance() ; 
Connection connexion = null;

/**
 * public static void main(String[] args) throws DaoException {
			
			Profils profils = new Profils();
			profils.setNom("Dicko");
			profils.setPrenom("Seydou Salia");
			profils.setEmail("dicko@gmail.com");
			profils.setPseudo("dicko");
			profils.setPassword("dicko");
			
			DaoFactory daoFactory = DaoFactory.getInstance();
	        ProfilsDao profilsDao = daoFactory.getProfilsDao();
	        profilsDao.ajouter(profils);
			
		}
 */

String requete="INSERT INTO profils (pseudo,password,email,nom,prenom) VALUES (?,?,?,?,?)";
PreparedStatement preparedStatement = c.prepareStatement(requete);
preparedStatement.setString(1, "dicko");
preparedStatement.setString(2, "seydou");
preparedStatement.setString(3, "dicko@gmail.com");
preparedStatement.setString(4, "dicko");
preparedStatement.setString(5, "dicko");
preparedStatement.executeUpdate();
							    preparedStatement.close();
							    c.commit();
							    
							 		}	
	
} 
