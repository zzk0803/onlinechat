<%-- 
    Document   : account
    Created on : 2018-10-23, 9:40:24
    Author     : uestc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="pragma" content="no-cache">
        <title>登录</title>
        <link rel="stylesheet" href="assets/account.css">
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
</html>
