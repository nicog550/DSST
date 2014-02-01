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
            HashMap usuaris = bdb.getUsuaris();
            HashMap tipus = bdb.getTipusUsuaris(); %>
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
                            <td id="nacTxt">
                                <span><%=usuari[2]%></span>
                                <span id="nac-<%=usrId%>" class="nds"><%=usuari[5]%></span>
                            </td>
                            <td id="dni-<%=usrId%>"><span><%=usuari[3]%></span></td>
                            <td id="tipTxt">
                                <span><%=usuari[4]%></span>
                                <span id="tip-<%=usrId%>" class="nds"><%=usuari[6]%></span>
                            </td>
                            <td><button id="edit-<%=usrId%>" class="editBtn"></button></td>
                            <td><button id="delete-<%=usrId%>" class="deleteBtn"></button></td>
                        </tr><%
                    } %>
                </tbody>
            </table>
        </div>
        <!-- Formulari d'edició d'usuaris -->
        <div id="editUsr" class="reveal-modal large">
            <h2>Editar usuari</h2>
            <div><%
                Iterator<String> iteradorTipus; %>
                <div class="row">
                    <input id="idEdit" type="hidden" />
                    <div class="inlineBlock">
                        <label for="nomEdit">Nom</label>
                        <input type="text" id="nomEdit" style="width: 250px;" />
                    </div>
                    <div class="inlineBlock">
                        <label for="mailEdit>">Email</label>
                        <input type="email" id="mailEdit" style="width: 200px;" />
                    </div>
                </div>
                <div class="row">
                    <div class="inlineBlock">
                        <label for="nacEdit">Nacionalitat</label>
                        <select id="nacEdit">
                            <option value="0">-- Triau una --</option><%
                            iteradorPais = paisos.keySet().iterator();
                            String codi;
                            while (iteradorPais.hasNext()) {
                                codi = iteradorPais.next(); %>
                                <option value="<%=codi%>"><%=paisos.get(codi)%></option><%
                            } %>
                        </select>
                    </div>
                    <div class="inlineBlock">
                        <label for="dniEdit">DNI</label>
                        <input type="text" maxlength="9" id="dniEdit" />
                    </div>
                    <div class="inlineBlock">
                        <label for="tipEdit">Tipus</label>
                        <select id="tipEdit">
                            <option value="0">-- Triau-ne un --</option><%
                            iteradorTipus = tipus.keySet().iterator();
                            Object idTipus;
                            while (iteradorTipus.hasNext()) {
                                idTipus = iteradorTipus.next(); %>
                                <option value="<%=idTipus%>"><%=((String[])tipus.get(idTipus))[0]%></option><%
                            } %>
                        </select>
                    </div>
                </div>
            </div>
            <div class="center">
                <button id="editUsrSubmit" class="submitBtn">Guardar</button>
            </div>
            <a class="close-reveal-modal">&#215;</a>
        </div>
    </body>
</html>
