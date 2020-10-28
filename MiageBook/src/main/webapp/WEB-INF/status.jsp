<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/inscription.css" />
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/inscription.js"></script>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<script src="bootstrap/js/bootstrap.min.js"></script>
<title>Ajouter un status</title>
</head>
<body>
	<nav class="navbar navbar-inverse">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="#">MiageBook</a>
	    </div>
	    <ul class="nav navbar-nav">
	      <li><a href="home">Home</a></li>
	      <li><a href="profils">Mon Profil</a></li>
	      <li><a href="utilisateurs">Liste des utilisateurs</a></li>
	      <li><a href="feed">Voir mes feeds</a></li>
	    </ul>
	  </div>
	</nav>
	<div class="container">
	  <form action="status" method="post">
		<c:if test="${not empty requestScope.message }">
		<p style="color: red">${requestScope.message}</p>
		</c:if>
		<h4>Enregistrement d'un nouveau status</h4>
	    <div class="form-group">
	      <label for="titre">Pseudo:</label>
	      <input type="text" class="form-control" id="titre" placeholder="Entrer votre titre" name="titre">
	    </div>
	    <div class="form-group">
	      <label for="texte">Password:</label>
	      <textarea type="text" class="form-control" id="texte" placeholder="Entrer votre texte" name="texte"></textarea>
	    </div>
	    <button type="submit" class="btn btn-default">Submit</button>
	  </form>
	</div>
</body>
</html>