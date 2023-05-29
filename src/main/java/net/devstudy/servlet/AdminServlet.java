package net.devstudy.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/admin",initParams = {
        @WebInitParam(name="IP", value="0:0:0:0:0:0:2:1"),
        @WebInitParam(name="ACCESS_KEY", value="12345"),
        @WebInitParam(name="LOGIN", value="admin"),
        @WebInitParam(name="PASSWORD", value="password")
})
public class AdminServlet extends HttpServlet {
    private String ip;
    private String accessKey;
    private String login;
    private String password;

    @Override
    public void init() throws ServletException {
        ip = getServletConfig().getInitParameter("IP");
        accessKey = getServletConfig().getInitParameter("ACCESS_KEY");
        login = getServletConfig().getInitParameter("LOGIN");
        password = getServletConfig().getInitParameter("PASSWORD");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter()){
            if(req.getAttribute("error") != null) {
                out.println("<h5 style='color:red'>"+req.getAttribute("error")+"</h5>");
            }
            String clientIp = getClientIp(req);
            out.println(clientIp);
            if (ip.endsWith(clientIp)){
                System.out.println("Login via ip: " +  clientIp);
                out.println("OK");
            }
            out.println(accessKey);
            out.println(login);
            out.println(password);
            out.println(ip);
            out.println("<form action='/login' method='post'>");
            out.println("<input name='login' placeholder='Login'>");
            out.println("<input name='password' placeholder='Password' type='password'>");
            out.println("<input type='submit' value='Login'>");
            out.println("</form>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if(isValid(req, login, password)) {
            resp.sendRedirect("/my-private-page");
        } else {
            doGet(req, resp);
        }
    }

    private boolean isValid(HttpServletRequest req, String login, String password) {
        if(login == null || login.trim().isEmpty()) {
            req.setAttribute("error", "Login is required");
            return false;
        }
        if(password == null || password.trim().isEmpty()) {
            req.setAttribute("error", "Password is required");
            return false;
        }
        return true;
    }

    String getClientIp(HttpServletRequest req){
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        } else {
            ipAddress = ipAddress.contains(",") ? ipAddress.split(",")[0] : ipAddress;
        }
        return ipAddress;
    }

}
