<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.io.PrintWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"></jsp:include>
<!DOCTYPE html>

<!-- css for products page -->
<link rel="stylesheet" href="css/products.css" />
<!-- products.js -->
<script src="js/products.js"></script>

			<div>
				<h1 id="title">Our Products</h1>
			</div>
			<hr>
			<div id="pc-icons">		
				<img src="img/mouse.jpg" />
				<img src="img/keyboard.jpg" />
				<img src="img/monitor.jpg" />
				<img src="img/case.jpg" />
			</div>
			<hr>
			<div id="products-table-wrapper">
				<table id="products-table">
					<tr>
				    	<th>Product ID (PID)</th>
				    	<th>Item</th>
				    	<th>Category</th>
				    	<th>Brand</th>
				    	<th>Price</th>
					</tr>
                               
                                     
