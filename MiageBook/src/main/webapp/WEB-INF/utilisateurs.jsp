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
<title>les utilisateurs</title>
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
	      <li><a href="status">Ajouter un status</a></li>
	      <li><a href="feed">Voir mes feeds</a></li>
	    </ul>
	  </div>
	</nav>
	<c:if test="${not empty requestScope.messageUser}">
	<p>${requestScope.messageUser}</p>
	</c:if>
	<div class="panel panel-default">
      		<div class="panel-heading" >Liste des utilisateurs</div>
	      		<c:forEach var="user" items="${requestScope.users}"> 
		      		 <div class="panel-body">Pseudo : ${user.pseudo} est un ami(e) : ${user.amis}<br>
		      		 						 En ligne : ${user.onLigne} <br>
		      			                     Connecte il ya : ${user.lastTime} 
		      		 <c:if test="${!user.amis}">
				      <a href="gestion-amis?idAmis=${user.id}&status=${user.amis}&page=utilisateur">Ajouter</a></p>
				      </c:if>
				      <c:if test="${user.amis}">
				      <a href="gestion-amis?idAmis=${user.id}&status=${user.amis}&page=utilisateur">Retirer</a></p>
				      </c:if>
		      		 </div>
			      </c:forEach>
      	</div>
</body>
</html>