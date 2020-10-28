package com.car.book.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.car.book.beans.Status;

public class StatusDaoImpl implements StatusDao {
	
	 private DaoFactory daoFactory;

	    StatusDaoImpl(DaoFactory daoFactory) {
	        this.daoFactory = daoFactory;
	    }

		public void ajouter(Status status) throws DaoException {
			
			Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            if(status.getImage()!=null) {
	            	preparedStatement = connexion.prepareStatement("INSERT INTO status (titre, texte, image, date, proprietaire) "
		            		+ "VALUES(?,?,?,?,?)");
	            	
	            	preparedStatement.setString(1, status.getTitre());
	 	            preparedStatement.setString(2, status.getTexte());
	 	            preparedStatement.setBlob(3, status.getImage());
	 	            preparedStatement.setTimestamp(4, Timestamp.valueOf(status.getDate()));
	 	            preparedStatement.setInt(5,status.getProprietaire());

	 	            preparedStatement.executeUpdate();
	            } else {
	            	
	            	preparedStatement = connexion.prepareStatement("INSERT INTO status (titre, texte, date, proprietaire) "
		            		+ "VALUES(?,?,?,?)");
	            	
	            	preparedStatement.setString(1, status.getTitre());
	 	            preparedStatement.setString(2, status.getTexte());
	 	            preparedStatement.setTimestamp(3, Timestamp.valueOf(status.getDate()));
	 	            preparedStatement.setInt(4,status.getProprietaire());

	 	            preparedStatement.executeUpdate();
	            }
	            
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

		public void modifier(Status status) throws DaoException {
			// TODO Auto-generated method stub
			
		}

		public void supprimer(Status status) throws DaoException {
			

			Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = connexion.prepareStatement("delete from status where "
	            		+ "proprietaire = ? and date= ?");
	            preparedStatement.setInt(1, status.getProprietaire());
	            preparedStatement.setTimestamp(2, Timestamp.valueOf(status.getDate()));
	            
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

		public List<Status> listerMy(int profils) throws DaoException {
			
			List<Status> statusList = new ArrayList<Status>();
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultat = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = connexion.prepareStatement("SELECT id,titre, texte, image, date, proprietaire " + 
	            		"from status where proprietaire= ? ORDER BY date DESC LIMIT 10");
	            
	            preparedStatement.setInt(1,profils);
	            
	            resultat = preparedStatement.executeQuery();

	            while (resultat.next()) {
	            	int id = resultat.getInt("id");
	               String titre= resultat.getString("titre");
	               Blob image = resultat.getBlob("image");
	               Timestamp date = resultat.getTimestamp("date");
	               int proprietaireId = resultat.getInt("proprietaire");
	               String texte = resultat.getString("texte");
	               
	                Status status = new Status();
	                status.setId(id);
	                status.setTitre(titre);
	                status.setDate(date.toLocalDateTime());
	                status.setProprietaire(proprietaireId);
	                status.setTexte(texte);
	                if(image!=null) status.setImage(image);
	                
	                statusList.add(status);
	            }
	        } catch (SQLException e) {
	            throw new DaoException("Impossible de communiquer avec la base de données "+e.getMessage());
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
	        return statusList;
		}
		
		public Status select(int id) throws DaoException {
			 Connection connexion = null;
		        PreparedStatement preparedStatement = null;
		        ResultSet resultat = null;
		        Status status= null;

		        try {
		            connexion = daoFactory.getConnection();
		            preparedStatement = connexion.prepareStatement("SELECT id,titre, texte, date, proprietaire FROM status where id= ?");
		            preparedStatement.setInt(1, id);
		            
		            resultat = preparedStatement.executeQuery();

		            while (resultat.next()) {
		            	int statusId =resultat.getInt("id");
		                String titre = resultat.getString("titre");
		                String texte = resultat.getString("texte");
		               int proprietaire = resultat.getInt("proprietaire");
		                Timestamp date = resultat.getTimestamp("date");

		                status = new Status();
		                status.setId(statusId);
		                status.setTitre(titre);
		                status.setTexte(texte);
		                status.setProprietaire(proprietaire);
		                status.setDate(date.toLocalDateTime());
		            
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
		        return status;
			}

		public List<Status> listerFriends(int profils) throws DaoException {

			List<Status> statusList = new ArrayList<Status>();
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultat = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = connexion.prepareStatement(" select id,titre,texte,image ,date,proprietaire,"
	            		+ " (select pseudo from profils where id = proprietaire) as pseudo "
	            		+ "from status where proprietaire in (select amis from amis where profils=?) or proprietaire = ?"
	            		+ " order by date desc limit 10 ");
	            
	            preparedStatement.setInt(1,profils);
	            preparedStatement.setInt(2,profils);
	            
	            resultat = preparedStatement.executeQuery();

	            while (resultat.next()) {
	            	int id = resultat.getInt("id");
	               String titre= resultat.getString("titre");
	               Blob image = resultat.getBlob("image");
	               Timestamp date = resultat.getTimestamp("date");
	               int proprietaireId = resultat.getInt("proprietaire");
	               String texte = resultat.getString("texte");
	               String pseudo = resultat.getString("pseudo");
	               
	                Status status = new Status();
	                status.setId(id);
	                status.setTitre(titre);
	                status.setDate(date.toLocalDateTime());
	                status.setProprietaire(proprietaireId);
	                status.setTexte(texte);
	                if(image!=null) status.setImage(image);
	                status.setPseudo(pseudo);
	                statusList.add(status);
	            }
	        } catch (SQLException e) {
	            throw new DaoException("Impossible de communiquer avec la base de données "+e.getMessage());
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
	        return statusList;
		}
		
		
		}

