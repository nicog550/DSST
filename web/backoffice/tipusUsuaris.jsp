<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Backoffice - Tipus d'usuaris</title>
        <link type="text/css" rel="stylesheet" href="backoffice.css">
        <%@include file="../includes.html" %>
        <script type="text/javascript" src="tipusUsuaris.js"></script>
    </head>
    <body>
        <jsp:useBean id="bdb" class="hotel1beans.BackofficeDB" scope="request" />
        <jsp:useBean id="adb" class="hotel1beans.AccessDB" scope="request" /><%
            HashMap tipus = adb.getTipusUsuaris(); %>
        <%@include file="../header.jsp" %>
        <div class="main">
            <h1 class="left">Tipus d'usuaris</h1>
            <table style="width: 100%;">
                <thead>
                    <tr>
                        <th><span>Id</span></th>
                        <th><span>Nom</span></th>
                        <th><span>Eliminar</span></th>
                    </tr>
                </thead>
                <tbody><%
                    Iterator<String> it = tipus.keySet().iterator();
                    Object idTip;
                    String tip;
                    while (it.hasNext()) {
                        idTip = it.next();
                        tip = (String)tipus.get(idTip); %>
                        <tr id="fila-<%=idTip%>">
                            <td id="id-<%=idTip%>"><span><%=idTip%></span></td>
                            <td id="nom-<%=idTip%>"><span><%=tip%></span></td>
                            <td><button id="delete-<%=idTip%>" class="deleteBtn"></button></td>
                        </tr><%
                    } %>
                </tbody>
            </table>
            <button href="#" id="addTipus" class="right add submitBtn" style="margin-right: 110px;">Afegir tipus</button>
        </div>
        <!-- Formulari d'edició d'usuaris -->
        <div id="addTip" class="reveal-modal medium">
            <h2>Afegir tipus</h2>
            <div class="row">
                <div class="inlineBlock">
                    <label for="nomEdit">Nom</label>
                    <input type="text" id="nomAdd" style="width: 250px;" />
                </div>
            </div>
            <div class="center">
                <button id="addTipSubmit" class="submitBtn">Guardar</button>
            </div>
            <a class="close-reveal-modal">&#215;</a>
        </div>
    </body>
</html>
