<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Backoffice - Login</title>
        <%@include file="../includes.html" %>
    </head>
    <body>
        <%@include file="../header.jsp" %>
        <div class="main" style="top: 100px; width: 300px;">
            <form action="../backLogin" method="post">
                <fieldset style="text-align: right;">
                    <legend>Iniciar sessió</legend>
                    <div>
                        <label for="mail">Email</label>
                        <input type="email" id="mail" name="mail"/><br />
                    </div>
                    <div>
                        <label for="pass">Contrasenya</label>
                        <input type="password" id="pass" name="pass"/>
                    </div>
                    <div>
                        <button type="submit">Enviar</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </body>
</html>
