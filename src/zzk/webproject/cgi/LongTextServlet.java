package zzk.webproject.cgi;

import zzk.webproject.cgi.vo.LongTextResponseVO;
import zzk.webproject.service.ChatMessageService;
import zzk.webproject.service.Services;
import zzk.webproject.util.SimpleJsonFormatter;
import zzk.webproject.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "LongTextServlet", urlPatterns = "/longtext")
@MultipartConfig
public class LongTextServlet extends HttpServlet {
    public static final Logger logger = Logger.getLogger(LongTextServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("text");

        HttpSession httpSession = request.getSession();
        String username = (String) httpSession.getAttribute("username");

        LongTextResponseVO responseVO = new LongTextResponseVO();
        responseVO.setMethod("post");
        if (Objects.nonNull(username) && StringUtil.nonBlank(text)) {
            ChatMessageService chatMessageService = Services.getChatMessageService();
            String uuid = UUID.randomUUID().toString();
            chatMessageService.mapLongText(uuid, text);
            responseVO.setContent(uuid);
            responseVO.setResult("success");
            logger.log(Level.INFO, String.format("用户%s通过servlet提交了一条长文本，文本长度%d", username, text.length()));
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
        if (StringUtil.nonBlank(uuid)) {
            message = chatMessageService.getLongText(uuid);
        }
        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();
        writer.print(message);
        writer.flush();
        writer.close();
    }
}
