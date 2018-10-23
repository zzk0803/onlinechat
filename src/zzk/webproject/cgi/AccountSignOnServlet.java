package zzk.webproject.cgi;

import zzk.webproject.service.AccountService;
import zzk.webproject.service.Services;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AccountSignOnServlet", urlPatterns = "/account/signon")
public class AccountSignOnServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("tackling");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AccountService accountService = Services.getAccountServiceInstance();
        boolean login = accountService.login(username, password);
        if (login) {
            System.out.println("OK");
            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute("id", httpSession.getId());
            httpSession.setAttribute("username", username);
        }
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }
}
;