package com.car.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.car.book.beans.Profils;
import com.car.book.dao.DaoFactory;

public class ProfilsDaoImpl implements ProfilsDao{
	
	 private DaoFactory daoFactory;

	    ProfilsDaoImpl(DaoFactory daoFactory) {
	        this.daoFactory = daoFactory;
	    }

		public void ajouter(Profils profils) throws DaoException {
			
			Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = connexion.prepareStatement("INSERT INTO profils (pseudo,password,email,"
	            		+ "nom,prenom, onLigne, lastTime) VALUES(?,?,?,?,?,?,?)");
	            preparedStatement.setString(1, profils.getPseudo());
	            preparedStatement.setString(2, profils.getPassword());
	            preparedStatement.setString(3, profils.getEmail());
	            preparedStatement.setString(4, profils.getNom());
	            preparedStatement.setString(5, profils.getPrenom());
	            preparedStatement.setString(6, profils.getOnLigne());
	            preparedStatement.setTimestamp(7, Timestamp.valueOf(profils.getLastTime()));
	            
	            preparedStatement.executeUpdate();
	            connexion.commit();
	        } catch (SQLException e) {
	            try {
	                if (connexion != null) {
	                    connexion.rollback();
	                }
	            } catch (SQLException e2) {
	            	throw new DaoException("Impossible de communiquer avec la base de données");
	            }
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

		public void modifier(Profils profils) throws DaoException {
			
			Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = connexion.prepareStatement("update profils set onLigne=?, lastTime=? where pseudo=?");
	            preparedStatement.setString(1, profils.getOnLigne());
	            preparedStatement.setTimestamp(2, Timestamp.valueOf(profils.getLastTime()));
	            preparedStatement.setString(3, profils.getPseudo());
	            
	            preparedStatement.executeUpdate();
	            connexion.commit();
	        } catch (SQLException e) {
	            try {
	                if (connexion != null) {
	                    connexion.rollback();
	                }
	            } catch (SQLException e2) {
	            	throw new DaoException("Impossible de communiquer avec la base de données");
	            }
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

		public void supprimer(Profils profils) throws DaoException {
			Connection connexion = null;
	        PreparedStatement preparedStatement = null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = connexion.prepareStatement("delete from profils where pseudo= ?");
	            preparedStatement.setString(1, profils.getPseudo());
	            
	            preparedStatement.executeUpdate();
	            connexion.commit();
	        } catch (SQLException e) {
	            try {
	                if (connexion != null) {
	                    connexion.rollback();
	                }
	            } catch (SQLException e2) {
	            	throw new DaoException("Impossible de communiquer avec la base de données");
	            }
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

		public List<Profils> lister() throws DaoException {
			
			List<Profils> profilsList = new ArrayList<Profils>();
	        Connection connexion = null;
	        Statement statement = null;
	        ResultSet resultat = null;

	        try {
	            connexion = daoFactory.getConnection();
	            statement = connexion.createStatement();
	            resultat = statement.executeQuery("SELECT id,pseudo, nom, prenom, password,onLigne, lastTime FROM profils");

	            while (resultat.next()) {
	            	int id =resultat.getInt("id");
	                String nom = resultat.getString("nom");
	                String prenom = resultat.getString("prenom");
	                String pseudo = resultat.getString("pseudo");
	                String password = resultat.getString("password");
	                Timestamp date = resultat.getTimestamp("lastTime");
	                String onLigne = resultat.getString("onLigne");

	                Profils profils = new Profils();
	                profils.setId(id);
	                profils.setNom(nom);
	                profils.setPseudo(pseudo);
	                profils.setPassword(password);
	                profils.setPrenom(prenom);
	                profils.setOnLigne(onLigne);
	                profils.setLastTime(date.toLocalDateTime());
	                profilsList.add(profils);
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
	        return profilsList;
		}

		public Profils select(String cible ) throws DaoException {
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultat = null;
	        Profils profils= null;

	        try {
	            connexion = daoFactory.getConnection();
	            preparedStatement = connexion.prepareStatement("SELECT id,pseudo, nom, prenom, password, lastTime, onLigne "
	            		+ "FROM profils where pseudo= ?");
	            preparedStatement.setString(1, cible);
	            
	            resultat = preparedStatement.executeQuery();

	            while (resultat.next()) {
	            	int id =resultat.getInt("id");
	                String nom = resultat.getString("nom");
	                String prenom = resultat.getString("prenom");
	                String pseudo = resultat.getString("pseudo");
	                String password = resultat.getString("password");
	                Timestamp date = resultat.getTimestamp("lastTime");
	                String onLigne = resultat.getString("onLigne");

	                profils = new Profils();
	                profils.setId(id);
	                profils.setNom(nom);
	                profils.setPseudo(pseudo);
	                profils.setPassword(password);
	                profils.setPrenom(prenom);
	                profils.setOnLigne(onLigne);
	                profils.setLastTime(date.toLocalDateTime());
	            
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
	        return profils;
		}
		

}
