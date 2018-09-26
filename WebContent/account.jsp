<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Insert title here</title>
        <link rel="stylesheet" type="text/css" href="assets/lib/bulma.css"/>
    </head>
    <body>
        <%
            String username = (String) request.getParameter("username");
            String password = (String) request.getParameter("password");
            if (username != null && !"".equals(username)) {
                session.setAttribute("username", username);
                response.sendRedirect("home.jsp");
            }
        %>
        <div class="container">
            <div id="account-form">
                <form action="account.jsp">
                    <div class="field">
                        <label class="label">
                            Username:
                        </label>
                        <div class="control">
                            <input name="username" class="input"/>
                        </div>
                    </div>

                    <div class="field">
                        <label class="label">
                            Password:
                        </label>
                        <div class="control">
                            <input name="username" class="input" type="password"/>
                        </div>
                    </div>

                    <div class="field">
                        <div class="control">
                            <button class="button is-primary" type="submit">Login</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>