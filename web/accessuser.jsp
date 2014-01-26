<%-- 
    Document   : accessuser
    Created on : 24-oct-2013, 12:07:06
    Author     : mascport
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>identificación</title>
    </head>
    <body>
        <jsp:useBean id="UT" class="sailingbeans.Utils" scope="request" />
        <%
            UT.entradaUsuarioClave(request, response, session);
        %>
    </body>
</html>
