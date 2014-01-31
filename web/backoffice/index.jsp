
<%@page import="hotel1beans.AccessDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index</title>
    </head>
    <body><%
        String tipus = (String)session.getAttribute("tipus");
        //Si la sessió està buida o l'usuari no és de tipus administrador, redirigim a la pàgina de login
        if (tipus == null || !tipus.equals(AccessDB.TIPUS_ADMIN)) response.sendRedirect("login.jsp");
        //Si l'usuari és administrador, redirigim a la pàgina d'usuaris, per defecte
        else response.sendRedirect("reserves.jsp");
        %>
    </body>
</html>
