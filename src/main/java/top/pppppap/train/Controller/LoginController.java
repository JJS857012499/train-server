package top.pppppap.train.Controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.pppppap.train.client.Login;
import top.pppppap.train.client.Query;
import top.pppppap.train.dto.JsonData;
import top.pppppap.train.service.QueryService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author pppppap
 * @since 2018-06-27 下午 16:17
 */
@Controller
public class LoginController {
    @RequestMapping("/getcheckimg")
    @CrossOrigin
    public void getImg(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        response.setHeader("programa", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        HttpSession session = request.getSession();
        CloseableHttpClient connection;
        if (session.getAttribute("connection") == null) {
            connection = HttpClients.createDefault();
            session.setAttribute("connection", connection);
        } else {
            connection = (CloseableHttpClient) session.getAttribute("connection");
        }
        Login login = new Login(connection);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            login.getCheckImg(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ResponseBody
    @CrossOrigin
    @RequestMapping("/trainlogin")
    public JsonData Login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        CloseableHttpClient connection = (CloseableHttpClient) session.getAttribute("connection");
        Map<String, String> checkImgParams = new HashMap<>();
        checkImgParams.put("answer", request.getParameter("answer"));
        checkImgParams.put("login_site", "E");
        checkImgParams.put("rand", "sjrand");
        Login login = new Login(connection);
        JsonData data = new JsonData();
        if (!login.checkImg(checkImgParams)) {
            data.setCode(1);
            data.setMsg("验证码错误");
            return data;
        }

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("username", request.getParameter("username"));
        loginParams.put("password", request.getParameter("password"));
        loginParams.put("appid", "otn");
        if (login.login(loginParams)) {
            data.setCode(0);
            data.setMsg("登录成功");
            Query query=new Query(connection);
            query.check();
        } else {
            data.setCode(1);
            data.setMsg("密码错误");
        }
        return data;
    }
}
