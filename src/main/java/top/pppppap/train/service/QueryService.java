package top.pppppap.train.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;
import top.pppppap.train.client.BaseClient;
import top.pppppap.train.client.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author pppppap
 * @since 2018-07-04 上午 10:25
 */
@Service("queryService")
public class QueryService {
    /**
     * 解析返回的json数据
     *
     * @param jsonObject 返回的json数据
     * @return List<JSONObject>
     **/
    private List<JSONObject> parseStation(JSONObject jsonObject) {
        if (jsonObject == null) return null;
        JSONObject data = ((JSONObject) jsonObject.get("data"));
        String[] result = data.getString("result").replace("\"", "").split(",");
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (String i : result) {
            //23：软卧,26:无座，28:硬卧,29:硬座,30:二等座,31:一等座,32:商务座特等座
            String[] stations = i.split("\\|");
//            for (int j = 3; j < stations.length; j++) {
//                if (j == 12 ) continue;
//                System.out.print("(" + j + "):" + stations[j] + "__");
//
//            }
//            System.out.println();
            for (int j = 0; j < stations.length; j++) {
                if (stations[j].equals("")) stations[j] = "—";
            }
//            String[] station=new String[10];
//            station[0]=stations[3];
//            station[1]=stations[6];
//            station[2]=stations[7];
//            station[3]=stations[23];
//            station[4]=stations[26];
//            station[5]=stations[28];
//            station[6]=stations[29];
//            station[7]=stations[30];
//            station[8]=stations[31];
//            station[9]=stations[32];
            JSONObject object = new JSONObject();
            try {
                object.put("no", stations[3]);//车次
                object.put("startStation", stations[6]);//起点站
                object.put("endStation", stations[7]);//到达站
                object.put("seat1", stations[23]);//软卧
                object.put("seat2", stations[26]);//无座
                object.put("seat3", stations[28]);//硬卧
                object.put("seat4", stations[29]);//硬座
                object.put("seat5", stations[30]);//二等座
                object.put("seat6", stations[31]);//一等座
                object.put("seat7", stations[32]);//商务座特等座
                object.put("secret", stations[0]);//密码串
                object.put("startTime", stations[8]);
                object.put("endTime", stations[9]);
                object.put("durationTime", stations[10]);
                jsonObjectList.add(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        for (JSONObject object : jsonObjectList) {
//            System.out.println(object);
//        }
        return jsonObjectList;
    }

    /**
     * 通过站点和时间查询车票
     *
     * @param startStation 起点站
     * @param endStation   终点站
     * @param time         时间
     * @return List<JSONObject>
     **/
    public List<JSONObject> qeuryStation(String startStation, String endStation, String time) {
        String queryUrl = BaseClient.QUERY_STATION;
        queryUrl = queryUrl.replaceFirst("#", time);
        queryUrl = queryUrl.replaceFirst("#", startStation);
        queryUrl = queryUrl.replaceFirst("#", endStation);
        return parseStation(Query.query(queryUrl));
    }

    public Object getPassengerDTOs(CloseableHttpClient client) {
        Query query = new Query(client);
        JSONObject jsonObject = query.getPassengerDTOs();
        JSONObject data = (JSONObject) jsonObject.get("data");
        return data.get("normal_passengers");
    }

    public JSONObject submitOrder(CloseableHttpClient connection,
                                  String secretStr,
                                  String trainDate,
                                  String backTrainDate,
                                  String query_from_station_name,
                                  String query_to_station_name) {
        Map<String, String> params = new HashMap<>();
        params.put("secretStr", secretStr);
        params.put("train_date", trainDate);
        params.put("back_train_date", backTrainDate);
        params.put("query_from_station_name", query_from_station_name);
        params.put("query_to_station_name", query_to_station_name);
        String s = new Query(connection).submitOrderRequest(params);
        return new JSONObject();
    }
}
