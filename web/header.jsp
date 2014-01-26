<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<body>
    <jsp:useBean id="bbdd" class="hotel1beans.AccessDB" scope="request" /><%
            HashMap paisos = bbdd.getPaisos(); %>
    <div class="header">
        <div class="main">
            <a href="../home/home.jsp">
                <img src="../img/logo.png" class="left" alt="Logo de l'hotel"/>
            </a>
            <div class="right">
                <h4 class="gris pointer"><%
                    String nom = (String)session.getAttribute("nom");
                    if (nom != null) { %>
                        <a href="#" id="nomVal" class="gris"><%=nom%><div class="arrow arrowDown"></div></a><br />
                        <a href="../logout" id="logoutBtn" class="gris" style="display: none;">Tancar sessió</a><%
                    } else { %>
                        <a data-reveal-id="loginModal" id="loginBtn">Iniciar sessió</a>
                        <span class="separator defCurs">·</span>
                        <a data-reveal-id="signupModal" id="signupBtn">Registrar-se</a><%
                    } %>
                </h4>
            </div>
        </div>
    </div>
    <!-- Popup de login -->
    <div id="loginModal" class="reveal-modal medium center">
        <h2>Iniciar sessió</h2>
        <form id="loginForm" action="/login" method="post" class="inlineBlock" style="text-align: right; width: 290px;">
            <fieldset>
                <div>
                    <label for="loginMail">Email</label>
                    <input type="email" id="loginMail" />
                </div>
                <div>
                    <label for="loginPass">Contrasenya</label>
                    <input type="password" id="loginPass" />
                </div>
                <div>
                    <button type="submit">Iniciar sessió</button>
                </div>
            </fieldset>
        </form>
        <a class="close-reveal-modal">&#215;</a>
    </div>
    <!-- Popup de registre -->
    <div id="signupModal" class="reveal-modal medium center">
        <h2>Iniciar sessió</h2>
        <form id="signupForm" action="/register" method="post" class="inlineBlock" style="text-align: right; width: 340px;">
            <fieldset>
                <div>
                    <label for="signupNom">Nom</label>
                    <input type="text" id="signupNom" />
                </div>
                <div>
                    <label for="signupMail">Email</label>
                    <input type="email" id="signupMail" />
                </div>
                <div>
                    <label for="signupPass">Contrasenya</label>
                    <input type="password" id="signupPass" />
                </div>
                <div>
                    <label for="signupPassRep">Repetir contrasenya</label>
                    <input type="password" id="signupPassRep" />
                </div>
                <div>
                    <label for="signupNac">Nacionalitat</label>
                    <select id="signupNac">
                        <option value="0">-- Triau una --</option><%
                        Iterator<String> iteradorPais;
                        iteradorPais = paisos.keySet().iterator();
                        String codiPais;
                        while (iteradorPais.hasNext()) {
                            codiPais = iteradorPais.next(); %>
                            <option value="<%=codiPais%>"><%=paisos.get(codiPais)%></option><%
                        } %>
                    </select>
                </div>
                <div>
                    <button type="submit">Registrar-se</button>
                </div>
            </fieldset>
        </form>
        <a class="close-reveal-modal">&#215;</a>
    </div>
    <!-- Popup d'error -->
    <div id="errorModal" class="reveal-modal medium center">
        <h2 class="error">Ha ocorregut un error</h2>
        <a class="close-reveal-modal">&#215;</a>
    </div>
    <!-- Popup d'error -->
    <div id="credencialsKoModal" class="reveal-modal medium center">
        <h2 class="error">Les credencials introduïdes són incorrectes</h2>
        <a class="close-reveal-modal">&#215;</a>
    </div>
</body>
