
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hotel1 - Reserva confirmada</title>
        <%@include file="../includes.html" %>
    </head>
    <body>
        <%@include file="../header.jsp" %>
        <div class="main" style="text-align: left;">
            <div>
                <h1>S'ha realitzat amb èxit la reserva amb un import de <%=session.getAttribute("preu")%>&euro;</h1>
            </div>
        </div>
    </body>
</html>
