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

            #account-box *.field div.btn-group {
                width: 85%;
                margin: 0 auto;
            }

            #account-box *.field button {
                margin: 0 5px;
                padding: 5px 35px;
                border: none;
                font-weight: bolder;
            }

            #account-box *.field button#register-btn {
                float: left;
                background-color: mediumaquamarine;
            }

            #account-box *.field button#login-btn {
                float: right;
                background-color: goldenrod;
            }
        </style>
    </head>
    <body>
        <div id="account-box">
            <form action="account" method="post">
                <div class="field">
                    <label for="username">用户名：</label>
                    <input type="text" id="username" name="username"/>
                </div>
                <div class="field">
                    <label for="password">密码：</label>
                    <input type="password" id="password" name="password"/>
                </div>
                <div class="field">
                    <div class="btn-group">
                        <button id="register-btn" type="submit" name="account-action" value="signin">注册</button>
                        <button id="login-btn" type="submit" name="account-action" value="signon">登录</button>
                    </div>
                </div>
            </form>
        </div>
    </body>
    <script>

    </script>
</html>
