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
<title>details status</title>
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
	      <li><a href="status">Ajouter un status</a></li>
	      <li><a href="feed">Voir mes feeds</a></li>
	    </ul>
	  </div>
	</nav>

	<h3>Information sur le status</h3>
	  <div class="panel panel-default">
		  <c:if test="${not empty requestScope.status.pseudo}">
		  	<div class="panel-heading">Pseudo   :${requestScope.status.pseudo}</div>
		  </c:if>
		  <c:if test="${empty requestScope.status.pseudo}">
		  	<div class="panel-heading">Pseudo : ${sessionScope.moi.pseudo}</div>
		  </c:if>
		    <div class="panel-body">Titre    : ${requestScope.status.titre} <br> 
		    						Date     : ${requestScope.status.date} <br>
		    						Texte    : ${requestScope.status.texte}</div>
	  </div>	
	
	    <div class="panel panel-default">
      		<div class="panel-heading" >Les commentaires</div>
	      		<c:forEach var="post" items="${requestScope.commentaires}">
		      		 <div class="panel-body">Pseudo : ${post.pseudo}<br>
		      		 						 Texte : ${post.texte} <br>
		      			                     Date : ${post.date}
		      		 </div>
			      </c:forEach>
      	</div>
      <div class="container">
  <form action="gestion-status" method="post">
    <div class="form-group">
      <textarea type="text" class="form-control" id="texte" placeholder="Commentez-ici" name="texte"></textarea>
      <input type="hidden" name="statusId" value="${requestScope.status.id}">
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
  </form>
</div>
</body>
</html>