<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
.carousel-inner>.item>img, .carousel-inner>.item>a>img {
	width: 100%;
	margin: auto;
	height: 180px;
}
</style>
<!-- <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script> -->
</head>
<body>
	<div id="myCarousel" class="carousel slide" data-ride="carousel">

		<ol class="carousel-indicators">
			<!-- <li data-target="#myCarousel" data-slide-to="0" class="active"></li> -->
			<!-- <li data-target="#myCarousel" data-slide-to="1"></li>
			<li data-target="#myCarousel" data-slide-to="2"></li> -->
		</ol>

		<div class="carousel-inner" role="listbox">
			<%-- <div class="item active">
				<img src="<c:url value="images/truckimage.png" />" alt="Java4" />
			</div> --%>
		</div>

		<!-- <a class="left carousel-control" href="#myCarousel" role="button"
			data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"
			aria-hidden="true"></span> <span class="sr-only">Previous</span>
		</a> <a class="right carousel-control" href="#myCarousel" role="button"
			data-slide="next"> <span
			class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a> -->
	</div>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.css" >
	<script  src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js" ></script>
	<nav class="navbar navbar-light" style="background-color: #e3f2fd;">
	<div class="container-fluid">
		<!-- <div class="navbar-header">
			<a class="navbar-brand" href="home">JIQA</a>
		</div> -->
		<ul class="nav navbar-nav">
			<li class="active"><a href="home">Home</a></li>
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#">Resources<span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="showdriver">Drivers</a></li>
					<li><a href="showvendor">Vendors</a></li>
					<li><a href="showtruck">Trucks</a></li>
					<li><a href="showtrailer">Trailers</a></li>
				</ul></li>
				<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#">Lists<span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="showdivision">Division</a></li>
					<li><a href="showterminal">Terminal</a></li>
					<li><a href="showcategory">Category</a></li>
					<li><a href="showservice">Service</a></li>
				</ul></li>
			<li><a href="showuser">Users</a></li>
			<li><a href="showshipper">Locations</a></li>
			<li><a href="showvmc">VMC</a></li>
			<li><a href="showissue">Issue</a></li>
			<li><a href="showpo">PO</a></li>
			<li><a href="logout">Logout</a></li>
			<!-- <li><a href="showlocation">Locations</a></li> -->
		</ul>
	</div>
	</nav>
</body>
</html>