<%-- 
    Document   : error
    Created on : 07-nov-2013, 9:57:53
    Author     : mascport
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../includes.html" %>
        <title>Error</title>
    </head>
    <body><%
        String err = session.getAttribute("error") == null ? "S'ha produit un error" : (String)session.getAttribute("error");
            session.removeAttribute("error"); %>
        <h1>Ups!</h1>
        
        <h3><%=err%></h3>
        <img src="../img/error.gif" alt="Imatge d'error" />
        
        <h5>Tornar a <a href="../index.jsp" class="link">inici</a>.</h5>
    </body>
</html>