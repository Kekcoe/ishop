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
        String loginFromClient = req.getParameter("login");
        String accessKeyFromClient = req.getHeader("ACCESS_KEY");
        String passwordFromClient = req.getParameter("password");
        String clientIp = getClientIp(req);
        try{
            validate(clientIp,accessKeyFromClient,loginFromClient,passwordFromClient);
            resp.getWriter().println("OK");
        }catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().println("FAILED");
        }
    }

    protected void validate (String clientIp, String accessKey, String loginFromClient, String passwordFromClient){
        StringBuilder errors = new StringBuilder();
        if(ip.equals(clientIp)){
            System.out.println("Login via ip: " +  clientIp);
            return;
        } else {
            errors.append(String.format("Invalid ip: %s\n", clientIp));
        }
        if(this.accessKey.equals(accessKey)){
            System.out.println("Login via accessKey: " +  accessKey);
            return;
        }else {
            errors.append(String.format("Invalid accessKey: %s\n", accessKey));
        }
        if(login.equals(loginFromClient) && password.equals(passwordFromClient)){
            System.out.println("Login via login and password: "+ login + "/" +  password);
            return;
        } else {
            errors.append(String.format("Invalid login and (or) password: %s/%s\n", loginFromClient, passwordFromClient));
        }
        throw  new IllegalStateException(errors.toString());
    }


    protected String getClientIp(HttpServletRequest req){
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        } else {
            ipAddress = ipAddress.contains(",") ? ipAddress.split(",")[0] : ipAddress;
        }
        return ipAddress;
    }

}
