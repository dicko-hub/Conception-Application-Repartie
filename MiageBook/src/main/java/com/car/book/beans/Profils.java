package com.car.book.beans;

import java.sql.Date;
import java.time.LocalDateTime;

public class Profils {
	
	private int id;
	private String pseudo;
	private String password;
	private String email;
	private String nom;
	private String prenom;
	private String onLigne;
	private LocalDateTime  lastTime;
	private boolean amis ;
	

	public Profils(String pseudo, String password, String email, String nom, String prenom) {
		super();
		this.pseudo = pseudo;
		this.password = password;
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
	}


	public Profils() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	public String getPseudo() {
		return pseudo;
	}


	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getOnLigne() {
		return onLigne;
	}


	public void setOnLigne(String onLigne) {
		this.onLigne = onLigne;
	}
	
	public LocalDateTime getLastTime() {
		return lastTime;
	}


	public void setLastTime(LocalDateTime lastTime) {
		this.lastTime = lastTime;
	}
	
	public boolean isAmis() {
		return amis;
	}


	public void setAmis(boolean amis) {
		this.amis = amis;
	}
	
	
	
}
