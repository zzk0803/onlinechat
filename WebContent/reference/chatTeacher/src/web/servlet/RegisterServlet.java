package web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("进入了get方法");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 验证jsp页面提交的数据是否为空的相关处理逻辑
		String uName = request.getParameter("uName");
		if(uName == null || "".equals(uName)) {
			request.setAttribute("msg", "注册失败，用户名不能为空");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
		String uPass = request.getParameter("uPass");
		if(uPass == null || "".equals(uPass)) {
			request.setAttribute("msg", "注册失败，密码不能为空");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
		String agestr = request.getParameter("age");
		if(agestr == null || "".equals(agestr)) {
			request.setAttribute("msg", "注册失败，年龄不能为空");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
		String heightstr = request.getParameter("height");
		if(heightstr == null || "".equals(heightstr)) {
			request.setAttribute("msg", "注册失败，身高不能为空");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
		int age = Integer.parseInt(agestr);
		int height = Integer.parseInt(heightstr);
		
		// 验证通过后，将提交的数据放在新的对象里面
		User user = new User();
		user.setuName(uName);
		user.setuPass(uPass);
		user.setAge(age);
		user.setHeight(height);
		
		// 获取到jsp的application内置对象
		ServletContext sc = getServletConfig().getServletContext();
		//将内置对象里面的registerUsers注册列表获取出来
		List<User> users = (List<User>) sc.getAttribute("registerUsers");
		//判断获取的注册列表是否为空
		if(users != null) {//不为空的情况
			int num = 0; //记录是否有相同的用户名注册
			for (User user2 : users) {
				if(user2.getuName().equals(user.getuName())) {
					num += 1;
				}
			}
			if(num == 0) {
				users.add(user);
				request.setAttribute("msg", "注册成功，请登录");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}else {
				request.setAttribute("msg", "注册失败，用户名已经被占用");
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
		}else {//注册列表为空的情况
			List<User> users2 = new ArrayList<>();
			users2.add(user);
			sc.setAttribute("registerUsers", users2);
			request.setAttribute("msg", "注册成功，请登录");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
