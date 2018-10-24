package zzk.webproject.cgi;

import zzk.webproject.service.AccountService;
import zzk.webproject.service.Services;
import zzk.webproject.util.OnlineUserUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "AccountSignOnServlet", urlPatterns = "/account/signon")
public class AccountSignOnServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AccountSignOnServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AccountService accountService = Services.getAccountServiceInstance();
        if (accountService.login(username, password)) {
            HttpSession httpSession = request.getSession(true);
            httpSession.setMaxInactiveInterval(60);
            httpSession.setAttribute("id", httpSession.getId());
            httpSession.setAttribute("username", username);
            OnlineUserUtil.register(username, httpSession);
        }
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }
}
;