<%--
  Created by IntelliJ IDEA.
  User: zzk08
  Date: 2018/10/9
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("account.jsp");
    }
%>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="pragma" content="no-cache">
        <title>网页在线聊天</title>
        <link rel="stylesheet" href="assets/index.css">
    </head>
    <body>
        <div id="centered-box">
            <div id="left-full-height">
                <ul id="online-accounts">
                </ul>
            </div>
            <div id="right-full-height">
                <div id="right-up-is-8">
                    <!--region 聊天信息-->
                    <ul id="received">
                    </ul>
                    <!--endregion-->
                </div>
                <div id="right-lower-is-2">
                    <div id="message-zone">
                        <label for="msg"></label>
                        <textarea name="msg" id="msg"></textarea>
                    </div>
                    <div id="bottom-btn-group">
                        <button id="send">发送</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>
        window.wp_context_path = "<%=application.getContextPath()%>";
        window.my_username = "<%=session.getAttribute("username")%>";
        window.bool_dict = {
            "true": true,
            "True": true,
            "TRUE": true,
            "false": false,
            "False": false,
            "FALSE": false
        }
    </script>
    <script src="assets/wp.main.js" charset="UTF-8"></script>
    <script src="assets/wp.air.js" charset="UTF-8"></script>
    <script src="assets/wp.message.js" charset="UTF-8"></script>
    <script src="assets/wp.ajax.js" charset="UTF-8"></script>
    <script>
        window.onload = function () {
            wp.main.init();
            wp.air.init();
            wp.message.init();
            wp.ajax.init();
        };
    </script>
</html>
