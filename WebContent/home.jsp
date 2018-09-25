<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/assets/lib/bulma.css" type="text/css"/>
</head>
<body>
<%
	String username=(String)session.getAttribute("username");
	if(username==null || "".equals(username)){
		Integer count=(Integer)application.getAttribute("count");
		if(count==null){
			application.setAttribute("count",Integer.valueOf(1));
		}else{
			count+=1;
			application.setAttribute("count",count);
		}
		response.sendRedirect("account.jsp");
	}else{
		String logout=request.getParameter("logout");
		if(logout!=null && "logout".equals("logout")){
			session.invalidate();
			response.sendRedirect("account.jsp");
		};
	}
%>
</body>
<div class="container">
	<h1 class="title">Hello,<%=session.getAttribute("username")%></h1>
	<form action="home.jsp">
		<input type="hidden" name="logout" value="logout"/>
		<div class="field">
			<div class="control">
				<button class="is-dangerous" type="submit">Logout</button> 
			</div>
		</div>
	</form>
</div>
</html>