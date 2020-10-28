package com.car.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.car.book.beans.Commentaires;

public class CommentairesDaoImpl implements CommentairesDao {
	
	 private DaoFactory daoFactory;

	    CommentairesDaoImpl(DaoFactory daoFactory) {
	        this.daoFactory = daoFactory;
	    }

		public void ajouter(Commentaires commentaire) throws DaoException {
			Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = connexion.prepareStatement("INSERT INTO commentaires (status, date, proprietaire, texte) "
	            		+ "VALUES(?,?,?,?)");
	            preparedStatement.setInt(1, commentaire.getStatus());
	            preparedStatement.setTimestamp(2,Timestamp.valueOf(commentaire.getDate()));
	            preparedStatement.setInt(3, commentaire.getProprietaire());
	            preparedStatement.setString(4, commentaire.getTexte());

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

		public void modifier(Commentaires commentaire) throws DaoException {
			// TODO Auto-generated method stub
			
		}

		public void supprimer(Commentaires commentaire) throws DaoException {
			

			Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = connexion.prepareStatement("delete from commentaires where "
	            		+ "proprietaire = ? and status= ? and date= ?");
	            preparedStatement.setInt(1, commentaire.getProprietaire());
	            preparedStatement.setInt(2, commentaire.getStatus());
	            preparedStatement.setTimestamp(3, Timestamp.valueOf(commentaire.getDate()));
	            
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

		public List<Commentaires> lister(int status) throws DaoException {
			
			List<Commentaires> commentairesList = new ArrayList<Commentaires>();
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultat = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = connexion.prepareStatement("SELECT id,status, date, proprietaire, texte from commentaires" +
	            		" where status=? ORDER BY date DESC LIMIT 10");
	            preparedStatement.setInt(1, status); 
	            
	            resultat = preparedStatement.executeQuery();

	            while (resultat.next()) {
	            	int commentaireId = resultat.getInt("id");
	               int statusId= resultat.getInt("status");
	               Timestamp date = resultat.getTimestamp("date");
	               int proprietaireId = resultat.getInt("proprietaire");
	               String texte = resultat.getString("texte");
	               
	                Commentaires commentaire = new Commentaires(statusId, proprietaireId, texte, date.toLocalDateTime());
	                commentaire.setId(commentaireId);
	               
	                commentairesList.add(commentaire);
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
	        return commentairesList;
		}

}
