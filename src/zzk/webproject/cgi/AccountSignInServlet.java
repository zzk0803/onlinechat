//package zzk.webproject.cgi;
//
//import zzk.webproject.service.AccountService;
//import zzk.webproject.service.Services;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet(name = "AccountSignInServlet", value = "/account/signin")
//public class AccountSignInServlet extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        AccountService accountService = Services.getAccountServiceInstance();
//        boolean register = accountService.register(username, password);
//        if (register) {
//            response.sendRedirect("/account/login");
//        } else {
//
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//}
