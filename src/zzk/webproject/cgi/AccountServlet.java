package zzk.webproject.cgi;

import zzk.webproject.service.AccountService;
import zzk.webproject.service.Services;
import zzk.webproject.util.OnlineUserUtil;
import zzk.webproject.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AccountServlet", urlPatterns = "/account")
public class AccountServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AccountService.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String accountAction = request.getParameter("account-action");
        AccountService accountService = Services.getAccountServiceInstance();
        String remoteAddr = request.getRemoteAddr();

        //用户名和密码不能为空
        if (StringUtil.isBlank(username) || StringUtil.isBlank(password)) {
            response.sendRedirect(request.getContextPath() + "/account.jsp");
        }

        switch (accountAction) {
            case "signin":
                if (accountService.register(username, password)) {
                    logger.log(Level.INFO, String.format("远程主机%s成功的注册了用户名%s", remoteAddr, username));
                    response.sendRedirect(request.getContextPath() + "/account.jsp");
                } else {
                    logger.log(Level.INFO, String.format("远程主机%s试图注册用户名%s，但失败了", remoteAddr, username));
                }
                break;

            case "signon":
                if (accountService.login(username, password)) {
                    HttpSession httpSession = request.getSession(true);
                    httpSession.setAttribute("id", httpSession.getId());
                    httpSession.setAttribute("username", username);
                    logger.log(Level.INFO, String.format("远程主机%s使用账户%s登录了", remoteAddr, username));
                    OnlineUserUtil.register(username, httpSession);
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/account.jsp");
                }
                break;

            default:
                logger.log(Level.INFO, String.format("前端action参数不合法:%s", accountAction));
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }
}