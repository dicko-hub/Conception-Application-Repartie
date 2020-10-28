package com.car.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.car.book.beans.Amis;
import com.car.book.beans.Profils;

public class AmisDaoImpl implements AmisDao {
	
	 private DaoFactory daoFactory;

	public AmisDaoImpl(DaoFactory daoFactory) {
	        this.daoFactory = daoFactory;
	    }

	public void ajouter(Amis amis) throws DaoException {

		Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO amis (profils, amis) VALUES(?, ?)");
            preparedStatement.setInt(1, amis.getProfils());
            preparedStatement.setInt(2, amis.getAmis());

            preparedStatement.executeUpdate();
            connexion.commit();
        } catch (SQLException e) {
            try {
                if (connexion != null) {
                    connexion.rollback();
                }
            } catch (SQLException e2) {
            }
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
		
	}

	public void supprimer(Amis amis) throws DaoException {

		Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("delete from amis where profils = ? and amis= ?");
            preparedStatement.setInt(1, amis.getProfils());
            preparedStatement.setInt(2, amis.getAmis());

            preparedStatement.executeUpdate();
            connexion.commit();
        } catch (SQLException e) {
            try {
                if (connexion != null) {
                    connexion.rollback();
                }
            } catch (SQLException e2) {
            }
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
		
	}

	public List<Amis> lister(int profils) throws DaoException {

		List<Amis> amisList = new ArrayList<Amis>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT profils, amis from amis where profils="+profils+"");

            while (resultat.next()) {
               int profilsId = resultat.getInt("profils");
               int amisId = resultat.getInt("amis");
               
                Amis amis = new Amis(profilsId,amisId);
               
                amisList.add(amis);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
        return amisList;
	}

	public Amis select(Profils pro1, Profils pro2) throws DaoException {


		 Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultat = null;
	        Amis amis= null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = connexion.prepareStatement("SELECT profils, amis FROM amis where profils= ? and amis =?");
	            preparedStatement.setInt(1, pro1.getId());
	            preparedStatement.setInt(2, pro2.getId());
	            
	            resultat = preparedStatement.executeQuery();

	            while (resultat.next()) {
	            	int profilsId =resultat.getInt("profils");
	                int amisId =resultat.getInt("amis");

	                amis= new Amis();
	                amis.setProfils(profilsId);
	                amis.setAmis(amisId);
	            
	            }
	        } catch (SQLException e) {
	            throw new DaoException("Impossible de communiquer avec la base de données " +e.getMessage());
	        }
	        finally {
	            try {
	                if (connexion != null) {
	                    connexion.close();  
	                }
	            } catch (SQLException e) {
	                throw new DaoException("Impossible de communiquer avec la base de données");
	            }
	        }
	        return amis;
	}

}
