<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Backoffice - Preus</title>
        <link type="text/css" rel="stylesheet" href="backoffice.css">
        <%@include file="../includes.html" %>
        <script type="text/javascript" src="preus.js"></script>
    </head>
    <body>
        <jsp:useBean id="bdb" class="hotel1beans.BackofficeDB" scope="request" /><%
            HashMap preus = bdb.getPreus(); %>
        <%@include file="../header.jsp" %>
        <div class="main">
            <h1 class="left">Preus</h1>
            <table style="width: 100%;">
                <thead>
                    <tr>
                        <th><span>Places</span></th>
                        <th><span>Preu</span></th>
                        <th><span>Editar</span></th>
                    </tr>
                </thead>
                <tbody><%
                    Iterator<String> it = preus.keySet().iterator();
                    Object idPr;
                    String preu;
                    while (it.hasNext()) {
                        idPr = it.next();
                        preu = (String)preus.get(idPr); %>
                        <tr id="fila-<%=idPr%>">
                            <td><span>Habitacions de <%=idPr%> places</span></td>
                            <td><span id="preu-<%=idPr%>"><%=preu%></span><span> &euro;</span></td>
                            <td><button id="edit-<%=idPr%>" class="editBtn"></button></td>
                        </tr><%
                    } %>
                </tbody>
            </table>
        </div>
        <!-- Formulari d'edició de preus -->
        <div id="editPreu" class="reveal-modal medium">
            <h2>Editar preu</h2>
            <div>
                <div class="row">
                    <div class="inlineBlock">
                        <label for="idEdit">Places</label>
                        <input type="text" id="idEdit" disabled="disabled" style="width: 50px;" />
                    </div>
                    <div class="inlineBlock">
                        <label for="preuEdit">Preu</label>
                        <input type="text" id="preuEdit" style="width: 80px;" /><span>&euro;</span>
                    </div>
                </div>
            </div>
            <div class="center">
                <button id="editPreuSubmit" class="submitBtn">Guardar</button>
            </div>
            <a class="close-reveal-modal">&#215;</a>
        </div>
    </body>
</html>
