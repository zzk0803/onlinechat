package zzk.webproject.cgi;

import zzk.webproject.air.ChatEndpoint;
import zzk.webproject.cgi.vo.LongTextResponseVO;
import zzk.webproject.service.ChatMessageService;
import zzk.webproject.service.Services;
import zzk.webproject.util.SimpleJsonFormatter;
import zzk.webproject.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@WebServlet(name = "LongTextServlet", urlPatterns = "/longtext")
@MultipartConfig
public class LongTextServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("text");

        HttpSession httpSession = request.getSession();
        String username = (String) httpSession.getAttribute("username");

        LongTextResponseVO responseVO = new LongTextResponseVO();
        responseVO.setMethod("post");
        if (Objects.nonNull(username) && StringUtil.nonBlank(text)) {
            ChatMessageService chatMessageService = Services.getChatMessageService();
            String uuid = chatMessageService.save(username, text);
            responseVO.setContent(uuid);
            responseVO.setResult("success");
            ChatEndpoint.broadcastReferenceMessage(username, "reference", uuid);
        } else {
            responseVO.setResult("failed");
        }

        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(SimpleJsonFormatter.toJsonString(responseVO));
        writer.flush();
        writer.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String message = "";
        ChatMessageService chatMessageService = Services.getChatMessageService();
        if (StringUtil.nonBlank(uuid)
                &&
                chatMessageService.isExist(uuid)
        ) {
            message = chatMessageService.getMessage(uuid);
        }
        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();
        writer.print(message);
        writer.flush();
        writer.close();
    }
}