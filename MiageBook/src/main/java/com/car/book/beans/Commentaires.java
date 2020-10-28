package com.car.book.beans;

import java.sql.Date;
import java.time.LocalDateTime;

public class Commentaires {
	
	private int id;
	private int status;
	private int proprietaire;
	private String texte;
	private LocalDateTime date;
	private String pseudo;
	

	public Commentaires() {
		
	}
	
	public Commentaires(int status, int proprietaire, String texte, LocalDateTime date) {
		super();
		this.status = status;
		this.proprietaire = proprietaire;
		this.texte = texte;
		this.date = date;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getProprietaire() {
		return proprietaire;
	}
	public void setProprietaire(int proprietaire) {
		this.proprietaire = proprietaire;
	}
	public String getTexte() {
		return texte;
	}
	public void setTexte(String texte) {
		this.texte = texte;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	
}
