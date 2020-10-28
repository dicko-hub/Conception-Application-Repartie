package com.car.book.beans;

import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDateTime;

public class Status {
	
	private int id;
	private String titre;
	private String texte;
	private Blob image;
	private LocalDateTime date;
	private int proprietaire;
	private String pseudo;
	

	public Status() {
		
	}
	
	public Status(String titre, String texte, Blob image, LocalDateTime date, int proprietaire) {
		super();
		this.titre = titre;
		this.texte = texte;
		this.image = image;
		this.date = date;
		this.proprietaire = proprietaire;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public Status(String titre, String texte,LocalDateTime date, int proprietaire) {
		super();
		this.titre = titre;
		this.texte = texte;
		this.date = date;
		this.proprietaire = proprietaire;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public int getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(int proprietaire) {
		this.proprietaire = proprietaire;
	}
	
	
	
	

}
