<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录页面</title>
<style type="text/css">
html{
	width: 100%;
}
body{
	height: 99%;
	width: 99%;
	margin: auto;
}
.register{
	width: 30%;
	height: 30%;
	margin-left: 35%;
	margin-top: 15%;
	float:left;
}
table{
	width: 80%;
	height:20%;
	padding-top: 20%;
}
.td1{
	text-align: right;
}
</style>
</head>
<body>
	<div class = "register">
	<form action="">
		<table>
			<tr>
				<td colspan="2">
				<%
					String msg = (String)request.getAttribute("msg");
					if(msg != null){
						out.print(msg);
					}
				%>
				</td>
			</tr>
			<tr>
				<td class="td1">用户名：</td>
				<td><input type="text" name="uName"></td>
			</tr>
			<tr>
				<td class="td1">密码：</td>
				<td><input type="password" name="uPass"></td>
			</tr>
			<tr>
				<td class="td1"><a href="register.jsp"><input type="button" value="注册"></a></td>
				<td><input type="submit" value="登录"></td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>