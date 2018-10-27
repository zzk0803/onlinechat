<%-- 
    Document   : account
    Created on : 2018-10-23, 9:40:24
    Author     : uestc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>登录</title>
        <style>
            body {
                background-color: black;
            }

            #account-box {
                width: 320px;
                background-color: dodgerblue;
                border: 1px solid white;
                padding: 30px;
                position: absolute;
                left: 40%;
                top: 42%;
            }

            #account-box *.field {
                margin: 10px;
                width: 80%;
            }

            #account-box *.field input {
                width: 100%;
                padding: 5px;
            }

            #account-box *.field label {
                font-weight: bolder;
            }

            #account-box *.field #login-btn {
                display: block;
                margin: 0 auto;
                padding: 5px 35px;
                border: none;
                background-color: mediumaquamarine;
                font-weight: bolder;
            }
        </style>
    </head>
    <body>
        <div id="account-box">
            <form action="account/signon" method="post">
                <div class="field">
                    <label for="username">用户名：</label>
                    <input type="text" id="username" name="username"/>
                </div>
                <div class="field">
                    <label for="password">密码：</label>
                    <input type="password" id="password" name="password"/>
                </div>
                <div class="field">
                    <button id="login-btn" type="submit">登录</button>
                </div>
            </form>
        </div>
    </body>
    <script>

    </script>
</html>
