
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body><%
        //Redirigim a la home
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", "home/home.jsp"); %>
    </body>
</html>
