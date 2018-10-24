<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>聊天窗口</title>
<style>
	html{
		height: 100%;
	}
	body{
		width: 100%;
		height: 100%;
		margin: auto;
		background-color: pink;
	}
	#chat_window{
		width: 60%;
		height: 80%;
		margin-left:20%;
		margin-top:5%;
		float:left;
	}
	#left{
		width: 20%;
		height: 100%;
		float:left;
		background-color: yellow;
	}
	#top{
		width: 80%;
		height: 10%;
		float:left;
		background-color: red;
	}
	#content{
		width: 80%;
		height: 90%;
		float:left;
		background-color: white;
	}
	.group_info{
		height: 10%;
		display: table;
		background-color: red;
	}
	.group_img{
		width: 50px;
		height:50px;
		background-image: url("img/mi.png");
		
	}
	.group_name{
		display: table-cell;
		vertical-align: middle;
	}
	.group_user{
		height: 85%;
		overflow:hidden;
	}
	.user{
		margin-top:5px;
		height: 10%;
		display: table;
	}
	.user_img{
		width: 50px;
		height:50px;
		background-image: url("img/mi.png");
		border-radius: 25px;
	}
	.user_name{
		width:60%;
		display: table-cell;
		vertical-align: middle;
	}
	.chat_info{
		height: 75%;
	}
	.send_msg{
		height: 25%;
	}
	#msg{
		height: 70%;
		width: 99%;
		resize:none;
	}
	.user_chat_left{
		margin-top:5px;
		width:100%;
		display: table;
	}
	.user_chat_left .user_info{
		width:10%;
		float:left;
	}
	.user_chat_left .chat_img{
		width: 50px;
		height:50px;
		background-image: url("img/mi.png");
		border-radius: 25px;
	}
	.user_chat_left .chat_name{
		width: 50px;
		text-align: center;
	}
	.user_chat_left .chat_msg{
		width:90%;
		display:table-cell;
		vertical-align: middle;
	}
	.user_chat_right{
		margin-top:5px;
		width:100%;
		display: table;
	}
	.user_chat_right .user_info{
		width:10%;
		float:left;
	}
	.user_chat_right .chat_img{
		margin-left:10px;
		width: 50px;
		height:50px;
		background-image: url("img/mi.png");
		border-radius: 25px;
	}
	.user_chat_right .chat_name{
		width: 50px;
		text-align: center;
		margin-left:10px;
	}
	.user_chat_right .chat_msg{
		width:90%;
		display:table-cell;
		vertical-align: middle;
		text-align: right;
	}
</style>
</head>
<body>
	<div id="chat_window">
		<div id="left">
			<div class="group_info">
				<div class="group_img">
				</div>
				<div class="group_name">
					电子科技大学聊天群
				</div>
			</div>
			群成员
			<div class="group_user">
				<div class="user">
					<div class="user_img">
					</div>
					<div class="user_name">
						用户1
					</div>
				</div>
				<div class="user">
					<div class="user_img">
					</div>
					<div class="user_name">
						用户二
					</div>
				</div>
			</div>
		</div>
		<div id="top"></div>
		<div id="content">
			<div class="chat_info">
				<div class="user_chat_left">
					<div class="user_info">
						<div class="chat_img"></div>
						<div class="chat_name">用户1</div>
					</div>
					<div class="chat_msg">我今天吃早饭啦</div>
				</div>
				<div class="user_chat_right">
					<div class="chat_msg">我今天吃午饭啦</div>
					
					<div class="user_info">
						<div class="chat_img"></div>
						<div class="chat_name">用户2</div>
					</div>
				</div>
				<div class="user_chat_left">
					<div class="user_info">
						<div class="chat_img"></div>
						<div class="chat_name">用户3</div>
					</div>
					<div class="chat_msg">我今天吃晚饭啦</div>
				</div>
			</div>
			<div class="send_msg">
				<textarea id="msg" rows="" cols=""></textarea>
				<table>
					<tr>
						<td><input type="submit" value="发送" /></td>
						<td><input type="reset" value="重置" /></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>