<%-- 
    Document   : account
    Created on : 2018-10-23, 9:40:24
    Author     : uestc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            body{
                background-color: #1b6d85
            }

            
            .account-form{
                margin: 20px auto;
                width: 300px;
                height: 300px;
                border: 1px solid wheat;
                background-color: #66afe9
            }
            
            .account-form .field{
                margin: 10px;
                padding: 7px;
            }
        </style>
    </head>
    <body>
        <div class="box-wrapper">
            <div class="account-form">
                <form action="account/signon" method="post">
                    <div class="field">
                        <label>
                            用户名： 
                            <input name="username" type="text"required/>
                        </label>
                    </div>
                    <div class="field">
                        <label>
                            密  码： 
                            <input name="password" type="password"required/>
                        </label>
                    </div>
                    <div class="field">
                        <label> 
                            <button type="submit">登 录</button>
                        </label>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
