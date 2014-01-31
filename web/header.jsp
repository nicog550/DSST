<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="hotel1beans.AccessDB"%>
<body>
    <jsp:useBean id="bbdd" class="hotel1beans.AccessDB" scope="request" /><%
            HashMap paisos = bbdd.getPaisos();
            boolean isBackoffice = request.getRequestURI().contains("backoffice");
            boolean isLogin = request.getRequestURI().contains("login");
            if (isBackoffice) {
                String __tipus = (String)session.getAttribute("tipus");
            //Si la sessió està buida o l'usuari no és de tipus administrador, redirigim a la pàgina de login
            if ((__tipus == null || !__tipus.equals(AccessDB.TIPUS_ADMIN)) && !isLogin)
                response.sendRedirect("login.jsp");
        }%>
    <header>
        <div class="main">
            <a href="../home/home.jsp">
                <img src="../img/logo.png" class="left" alt="Logo de l'hotel"/>
            </a>
            <div class="right">
                <h4 id="login-signup" class="gris pointer" style="min-width: 100px;"><%
                    String nom = (String)session.getAttribute("nom");
                    if (nom != null) { %>
                        <a href="#" id="nomVal" class="gris right"><%=nom%><div class="arrow arrowDown"></div></a><br />
                        <a href="../logout" id="logoutBtn" class="gris" style="display: none;">Tancar sessió</a><%
                    } else { 
                        if (!(isBackoffice && isLogin)) { %>
                            <a data-reveal-id="loginModal" id="loginBtn">Iniciar sessió</a><%
                        }
                        if (!isBackoffice) { %>
                            <span class="separator defCurs">·</span>
                            <a data-reveal-id="signupModal" id="signupBtn">Registrar-se</a><%
                        }
                    } %>
                </h4>
            </div>
        </div><%
        //Mostram en el backoffice els botons de navegació entre pàgines
        if (isBackoffice && !isLogin) { %>
            <div class="main backBtns">
                <a type="button" href="reserves.jsp">Reserves</a>
                <a type="button" href="estatsReserva.jsp">Estats de reserves</a>
                <a type="button" href="usuaris.jsp">Usuaris</a>
                <a type="button" href="tipusUsuaris.jsp">Tipus d'usuaris</a>
                <a type="button" href="descomptes.jsp">Descomptes</a>
                <a type="button" href="habitacions.jsp">Habitacions</a>
            </div><%
        } %>
    </header>
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
        <h2>Registrar-se</h2>
        <form id="signupForm" action="/registre" method="post" class="inlineBlock" style="text-align: right; width: 340px;">
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
                    <label for="signupPassDni">DNI</label>
                    <input type="text" maxlength="9" id="signupDni" />
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
