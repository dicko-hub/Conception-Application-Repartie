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
<title>Mes feeds</title>
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
	    </ul>
	  </div>
	</nav>
	<c:if test="${not empty requestScope.messageUser}">
	<p>${requestScope.messageUser}</p>
	</c:if>
	<div class="container">
	  <div class="row">
	  	<c:if test="${ not empty requestScope.status}">
		<h3>Mes status</h3>
		<c:forEach var="post" items="${requestScope.status}"> 
	     	<div class="col-sm-4">
	      	<h4>${post.titre}</h4>
	     	<p>Mise en ligne le ${post.date} <br>
	      	<a href="gestion-status?statusId=${post.id}">ici-details</a></p>
	    	</div>
	    </c:forEach>
		</c:if>
	  </div>
	</div> 
	
</body>
</html>