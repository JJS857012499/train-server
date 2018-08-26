package top.pppppap.train.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pppppap.train.client.Query;
import top.pppppap.train.dto.JsonData;
import top.pppppap.train.service.QueryService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * TODO
 *
 * @author pppppap
 * @since 2018-07-04 上午 10:19
 */

@RestController
public class QueryController {
    @Autowired
    private QueryService queryService;

    @RequestMapping("/query")
    public JsonData queryStation(HttpServletRequest request) {
        String startStation = request.getParameter("startStation");
        String endStation = request.getParameter("endStation");
        String time = request.getParameter("time");
        JsonData data = new JsonData();
        List<JSONObject> jsonDataList = queryService.qeuryStation(startStation, endStation, time);
        data.setData(jsonDataList);
        if (jsonDataList == null || jsonDataList.isEmpty()) {
            data.setMsg("无查询结果");
            data.setCode(4);
        } else {
            data.setMsg("查询成功");
            data.setCode(1);
        }
        return data;
    }

    @RequestMapping("/query2")
    public JsonData query(HttpServletRequest request) {
        CloseableHttpClient connection = (CloseableHttpClient) request.getSession().getAttribute("connection");
        JsonData data = new JsonData();
        data.setData(queryService.getPassengerDTOs(connection));
        return data;
    }

    @RequestMapping("/submitOrder")
    public JsonData bookTicket(HttpServletRequest request) {
        String secretStr = request.getParameter("secretStr");
        String trainDate = request.getParameter("train_date");
        String backTrainDate = request.getParameter("back_train_date");
        String query_from_station_name = request.getParameter("query_from_station_name");
        String query_to_station_name = request.getParameter("query_to_station_name");
        CloseableHttpClient connection = (CloseableHttpClient) request.getSession().getAttribute("connection");
        queryService.submitOrder(connection, secretStr, trainDate, backTrainDate, query_from_station_name, query_to_station_name);
        JsonData data = new JsonData();
        data.setData(queryService.getPassengerDTOs(connection));
        System.out.println(data.getData());
        return data;
    }

    @RequestMapping("checkUser")
    public JSONObject checkUser(HttpSession session) {
        CloseableHttpClient connection = (CloseableHttpClient) session.getAttribute("connection");
        return new Query(connection).checkUser();
    }
}
