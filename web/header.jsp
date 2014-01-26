<body>
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
                        <a data-reveal-id="loginModal" id="loginBtn">Iniciar sessió</a><%
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
                    <button type="submit">Enviar</button>
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
