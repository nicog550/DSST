
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.HashMap"%>
<%@page import="hotel1beans.AccessDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hotel1 - Reserves</title>
        <link type="text/css" rel="stylesheet" href="reservar.css">
        <%@include file="../includes.html" %>
        <script type="text/javascript" src="reservar.js"></script>
    </head>
    <body>
        <jsp:useBean id="db" class="hotel1beans.AccessDB" scope="request" />
        <%@include file="../header.jsp" %>
        <% //Si no arribam aquí per petició POST redirigim a la home
        String inici, fi, email, nac, dni, tip;
        inici = fi = email = nac = dni = tip = "";
        int places = 0;
        HashMap tipus;
        tipus = new HashMap();
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            response.sendRedirect("../home/home.jsp");
        } else {
            inici = request.getParameter("inici");
            fi = request.getParameter("fi");
            places = Integer.parseInt(request.getParameter("num"));
            tipus = db.getTipusUsuaris();
            email = (String)session.getAttribute("email");
            nac = (String)session.getAttribute("nacionalitat");
            dni = (String)session.getAttribute("dni");
            tip = (String)session.getAttribute("tipus");
        }%>
        <div class="main" style="text-align: left;">
            <div>
                <form action="../CrearReserva" method="post" id="reservarForm" class="cercador left" style="margin-bottom: 20px;">
                    <fieldset id="dates" class="rightColumn">
                        <div>
                            <label>Data d'entrada</label><br />
                            <input type="text" disabled="disabled" class="right" value="<%=inici%>" /><br />
                            <input type="hidden" name="dataIni" id="dataIni" value="<%=inici%>" />
                        </div>
                        <div>
                            <label>Data de sortida</label><br />
                            <input type="text" disabled="disabled" class="right" value="<%=fi%>" /><br />
                            <input type="hidden" name="dataFi" id="dataFi" value="<%=fi%>" />
                        </div>
                    </fieldset>
                    <fieldset class="left">
                        <legend>Informació dels usuaris</legend>
                        <div><%
                            Iterator<String> iteradorTipus;
                            for (int i = 0; i < places; i++) { %>
                                <div id="hoste-<%=i%>" class="host">
                                    <span>HOSTE <%=i + 1%>:</span>
                                    <div class="row">
                                        <div class="inlineBlock">
                                            <label for="nom-<%=i%>">Nom</label>
                                            <input type="text" id="nom-<%=i%>" name="nom-<%=i%>" style="width: 250px;"
                                                   value="<%=(i == 0 && nom != null) ? nom : ""%>"/>
                                        </div>
                                        <div class="inlineBlock">
                                            <label for="mail-<%=i%>">Email</label>
                                            <input type="email" id="mail-<%=i%>" name="mail-<%=i%>" style="width: 200px;"
                                                   value="<%=(i == 0 && email != null) ? email : ""%>"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="inlineBlock">
                                            <label for="nac-<%=i%>">Nacionalitat</label>
                                            <select id="nac-<%=i%>" name="nac-<%=i%>">
                                                <option value="0">-- Triau una --</option><%
                                                iteradorPais = paisos.keySet().iterator();
                                                String codi, selected;
                                                while (iteradorPais.hasNext()) {
                                                    codi = iteradorPais.next();
                                                    selected = (i == 0 && nac != null && nac.equals(codi)) ? " selected=\"selected\"" : "";%>
                                                    <option value="<%=codi%>"<%=selected%>><%=paisos.get(codi)%></option><%
                                                } %>
                                            </select>
                                        </div>
                                        <div class="inlineBlock">
                                            <label for="dni-<%=i%>">DNI</label>
                                            <input type="text" maxlength="9" id="dni-<%=i%>" name="dni-<%=i%>"
                                                   value="<%=(i == 0 && dni != null) ? dni : ""%>"/>
                                        </div>
                                        <div class="inlineBlock">
                                            <label for="tip-<%=i%>">Tipus</label>
                                            <select id="tip-<%=i%>" name="tip-<%=i%>">
                                                <option value="0">-- Triau-ne un --</option><%
                                                iteradorTipus = tipus.keySet().iterator();
                                                Object idTipus;
                                                while (iteradorTipus.hasNext()) {
                                                    idTipus = iteradorTipus.next();
                                                    selected = (i == 0 && tip != null && tip.equals(idTipus)) ? " selected=\"selected\"" : "";%>
                                                    <option value="<%=idTipus%>"<%=selected%>><%=tipus.get(idTipus)%></option><%
                                                } %>
                                            </select>
                                        </div>
                                    </div><%
                                    if (i != places - 1) { %>
                                        <hr /><%
                                    } %>
                                </div><%
                            } %>
                        </div><%
                        if (places > 1) { %>
                            <div class="center">
                                <a type="button" id="seguent" class="right">Següent hoste</a>
                            </div><%
                        } %>
                    </fieldset>
                    <fieldset id="submitSet" class="rightColumn">
                        <div class="center">
                            <button type="submit" class="submitBtn">Reservar</button>
                        </div>
                    </fieldset>
                    <div>
                        <input type="hidden" id="placesVal" name="placesVal" value="<%=places%>" />
                        <button id="loginChange" class="nds"></button>
                    </div>
                </form> 
            </div>
        </div>
        <!-- Modal que mostra l'error d'AJAX -->
        <div id="ajaxErrModal" class="reveal-modal large">
            <h2 class="error">Ups!</h2>
            <p class="lead">Sembla que hi ha hagut un error comprovant la disponibilitat</p>
            <p>Tornau a intentar-ho més tard</p>
            <a class="close-reveal-modal">&#215;</a>
	</div>
        <!-- Modal que mostra el missatge de no disponibilitat -->
        <div id="noDispModal" class="reveal-modal large">
            <h2>No hi ha disponibilitat en les dates que heu triat</h2>
            <p class="lead">Podeu provar per unes dates diferents</p>
            <a class="close-reveal-modal">&#215;</a>
	</div>
        <!-- Modal que mostra el missatge de disponibilitat trobada -->
        <div id="siDispModal" class="reveal-modal large">
            <h2>S'ha trobat disponibilitat!</h2>
            <p class="lead">En <span id="timeout">5</span> <span id="segons">segons</span> us durem a la pàgina de reserves</p>
            <a class="close-reveal-modal">&#215;</a>
	</div>

        <%  //String user = (String) session.getAttribute("usuario");
            //String passwd = (String) session.getAttribute("clave");
            //if ((user == null) || (passwd == null)) {
            //    response.sendRedirect("identificacion.jsp");
            //}
        %>

        <%
            /*Integer nivell = (Integer) session.getAttribute("nivel");
            if (nivell != null) {
                out.print("El nivel de permiso es: " + nivell);
            }*/
        %>
        <%//@include file="../footer.jsp" %>
    </body>
</html>
