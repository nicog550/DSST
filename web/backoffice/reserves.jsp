<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Backoffice - Reserves</title>
        <link type="text/css" rel="stylesheet" href="backoffice.css">
        <%@include file="../includes.html" %>
        <script type="text/javascript" src="reserves.js"></script>
    </head>
    <body>
        <jsp:useBean id="bdb" class="hotel1beans.BackofficeDB" scope="request" /><%
            HashMap reserves = bdb.getReserves();
            HashMap estats = bdb.getEstatsReserva(); %>
        <%@include file="../header.jsp" %>
        <div class="main">
            <h1 class="left">Reserves</h1>
            <table style="width: 100%;">
                <thead>
                    <tr>
                        <th><span>Id</span></th>
                        <th><span>Usuari</span></th>
                        <th><span>Habitació</span></th>
                        <th><span>Inici</span></th>
                        <th><span>Fi</span></th>
                        <th><span>Estat</span></th>
                        <th><span>Canviar<br />estat</span></th>
                    </tr>
                </thead>
                <tbody><%
                    Iterator<String> it = reserves.keySet().iterator();
                    Object idRes;
                    String[] reserva;
                    while (it.hasNext()) {
                        idRes = it.next();
                        reserva = (String[])reserves.get(idRes); %>
                        <tr id="fila-<%=idRes%>">
                            <td id="id-<%=idRes%>"><span><%=idRes%></span></td>
                            <td><span><%=reserva[0]%></span></td>
                            <td><span><%=reserva[1]%></span></td>
                            <td><span><%=reserva[2]%></span></td>
                            <td><span><%=reserva[3]%></span></td>
                            <td>
                                <span><%=reserva[4]%></span>
                                <span id="estat-<%=idRes%>" class="nds"><%=reserva[5]%></span>
                            </td>
                            <td><button id="edit-<%=idRes%>" class="editBtn"></button></td>
                        </tr><%
                    } %>
                </tbody>
            </table>
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
        <!-- Formulari d'edició de reserves -->
        <div id="editRes" class="reveal-modal medium">
            <h2>Editar reserva</h2>
            <div><%
                Iterator<String> iteradorEst; %>
                <div class="row">
                    <div class="inlineBlock">
                        <label for="idEdit">Id</label>
                        <input type="text" id="idEdit" disabled="disabled" style="width: 50px;" />
                    </div>
                    <div class="inlineBlock">
                        <label for="estEdit">Estat</label>
                        <select id="estEdit">
                            <option value="0">-- Triau-ne un --</option><%
                            iteradorEst = estats.keySet().iterator();
                            Object idTipus;
                            while (iteradorEst.hasNext()) {
                                idTipus = iteradorEst.next(); %>
                                <option value="<%=idTipus%>" id="editEst-<%=idTipus%>"><%=estats.get(idTipus)%></option><%
                            } %>
                        </select>
                    </div>
                </div>
            </div>
            <div class="center">
                <button id="editResSubmit" class="submitBtn">Guardar</button>
            </div>
            <a class="close-reveal-modal">&#215;</a>
        </div>
    </body>
</html>
