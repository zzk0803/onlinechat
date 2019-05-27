package zzk.webproject.cgi;

import zzk.webproject.air.Roster;

import javax.imageio.ImageIO;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        HttpSession targetSession = Roster.getSession(username);
        boolean notGray = analysisUserSessionState(targetSession);
        String printString = getFirstCharInUserName(username);
        ImageIO.write(
                generateUserIcon(notGray, printString),
                "jpg",
                response.getOutputStream()
        );
    }

    private boolean analysisUserSessionState(HttpSession targetSession) {
        return Objects.isNull(targetSession);
    }

    private String getFirstCharInUserName(String username) {
        return Character.toString(username.charAt(0));
    }

    private BufferedImage generateUserIcon(boolean useGray, String patternString) {
        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Color c = useGray ? new Color(getRGBColorInteger(), getRGBColorInteger(), getRGBColorInteger()) : Color.GRAY;
        g.setColor(c);
        g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("楷体", Font.PLAIN, 42));
        FontMetrics fontMetrics = g.getFontMetrics();
        int x_pos = (IMG_WIDTH - fontMetrics.stringWidth(patternString)) / 2;
        int y_pos = ((IMG_HEIGHT - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();
        g.drawString(patternString, x_pos, y_pos);
        return image;
    }

    private int getRGBColorInteger() {
        return RANDOM_INSTANCE.nextInt(256);
    }
}
