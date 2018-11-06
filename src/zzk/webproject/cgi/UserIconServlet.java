package zzk.webproject.cgi;

import zzk.webproject.util.OnlineUserUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

@WebServlet(name = "UserIconServlet", urlPatterns = "/usericon")
public class UserIconServlet extends HttpServlet {
    private static final int IMG_WIDTH = 45;
    private static final int IMG_HEIGHT = 45;
    private static final Random RANDOM_INSTANCE = new Random();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        HttpSession targetSession = OnlineUserUtil.getSession(username);
        if (Objects.isNull(targetSession)) {
            return;
        }
        String printString = Character.toString(username.charAt(0));

        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Color c = new Color(getRGBColorInteger(), getRGBColorInteger(), getRGBColorInteger());
        g.setColor(c);
        g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("楷体", Font.PLAIN, 42));
        FontMetrics fontMetrics = g.getFontMetrics();
        int x_pos = (IMG_WIDTH - fontMetrics.stringWidth(printString)) / 2;
        int y_pos = ((IMG_HEIGHT - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();
        g.drawString(printString, x_pos, y_pos);

        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    private int getRGBColorInteger() {
        return RANDOM_INSTANCE.nextInt(256);
    }
}
