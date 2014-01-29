<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Backoffice - Usuaris</title>
        <link type="text/css" rel="stylesheet" href="backoffice.css">
        <%@include file="../includes.html" %>
        <script type="text/javascript" src="usuaris.js"></script>
    </head>
    <body>
        <jsp:useBean id="bdb" class="hotel1beans.BackofficeDB" scope="request" /><%
            HashMap usuaris = bdb.getUsuaris(); %>
        <%@include file="../header.jsp" %>
        <div class="main">
            <h1 class="left">Usuaris</h1>
            <table style="width: 100%;">
                <thead>
                    <tr>
                        <th><span>Id</span></th>
                        <th><span>Nom</span></th>
                        <th><span>Email</span></th>
                        <th><span>Nacionalitat</span></th>
                        <th><span>DNI</span></th>
                        <th><span>Tipus</span></th>
                        <th><span>Editar</span></th>
                        <th><span>Eliminar</span></th>
                    </tr>
                </thead>
                <tbody><%
                    Iterator<String> it = usuaris.keySet().iterator();
                    Object usrId;
                    String[] usuari;
                    while (it.hasNext()) {
                        usrId = it.next();
                        usuari = (String[])usuaris.get(usrId); %>
                        <tr id="fila-<%=usrId%>">
                            <td class="textLeft"><span><%=usrId%></span></td>
                            <td id="nom-<%=usrId%>" class="textLeft"><span><%=usuari[0]%></span></td>
                            <td id="mail-<%=usrId%>" class="textLeft"><span><%=usuari[1]%></span></td>
                            <td id="nac-<%=usrId%>"><span><%=usuari[2]%></span></td>
                            <td id="dni-<%=usrId%>"><span><%=usuari[3]%></span></td>
                            <td id="tip-<%=usrId%>"><span><%=usuari[4]%></span></td>
                            <td><button id="edit-<%=usrId%>" class="editBtn"></button></td>
                            <td><button id="delete-<%=usrId%>" class="deleteBtn"></button></td>
                        </tr><%
                    } %>
                </tbody>
            </table>
        </div>
    </body>
</html>
