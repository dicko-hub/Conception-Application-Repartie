package com.car.book.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private String url;
    private String username;
    private String password;

    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {

        }

        DaoFactory instance = new DaoFactory("jdbc:mysql://localhost:3306/book", "root", "");
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection connexion = DriverManager.getConnection(url, username, password);
        connexion.setAutoCommit(false);
        return connexion; 
    }

    
    // Récupération du Dao utilisateurs
    public ProfilsDao getProfilsDao() {
        return new ProfilsDaoImpl(this);
    }
    
    // Récupération du Dao utilisateurs
    public StatusDao getStatusDao() {
        return new StatusDaoImpl(this);
    }
    
    // Récupération du Dao utilisateurs
    public CommentairesDao getCommentairesDao() {
        return new CommentairesDaoImpl(this);
    }
    
    // Récupération du Dao utilisateurs
    public AmisDao getAmisDao() {
        return new AmisDaoImpl(this);
    }
}