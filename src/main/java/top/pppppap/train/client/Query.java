package top.pppppap.train.client;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author pppppap
 * @since 2018-07-04 上午 10:28
 */

public class Query {
    private CloseableHttpClient client;

    private Query() {
    }

    public Query(CloseableHttpClient client) {
        this.client = client;
    }

    /**
     * 获取不带参数的Json Get数据
     *
     * @param
     * @return
     **/
    public static JSONObject query(String url) {
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet);) {
            jsonObject = BaseClient.getResponseJson(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 获取带参数的Json Post数据
     *
     * @param
     * @return
     **/
    public JSONObject query(String url, Map<String, String> params) {
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        JSONObject object = null;
        try {
            BaseClient.setPostParams(post, params);
            response = this.client.execute(post);
            object = BaseClient.getResponseJson(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }

    /**
     * 获取带参数的String Post数据
     *
     * @param
     * @return
     **/
    public String queryPostString(String url, Map<String, String> params) {
        return queryPostString(null, url, params);
    }

    /**
     * 获取指定HttpPost带参数的String Post数据
     *
     * @param
     * @return
     **/
    public String queryPostString(HttpPost post, Map<String, String> params) {
        return queryPostString(post, null, params);
    }

    /**
     * 获取指定HttpPost带参数的String Post数据
     *
     * @param
     * @return
     **/
    public String queryPostString(HttpPost post, String url, Map<String, String> params) {
        if (post == null) post = new HttpPost(url);
        CloseableHttpResponse response = null;
        String s = "";
        try {
            BaseClient.setPostParams(post, params);
            response = client.execute(post);
            s = BaseClient.getResponseString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return s;
    }

    /**
     * TODO
     *
     * @param
     * @return
     **/
    public JSONObject checkUser() {
        Map<String, String> params = new HashMap<>();
        params.put("_json_att", "");
        JSONObject jsonObject = query(BaseClient.CHECK_USER, params);
        return jsonObject;
    }

    /**
     * 获取tk
     *
     * @param
     * @return
     **/
    public JSONObject getTk() {
        Map<String, String> params = new HashMap<>();
        params.put("_json_att", "");
        params.put("appid", "otn");
        JSONObject jsonObject = query(BaseClient.CHECK_UAMTK, params);
        if ("0".equals(jsonObject.getString("result_code"))) {
            String tk = jsonObject.getString("newapptk");
            Login.tk = tk;
        }
        return jsonObject;
    }

    /**
     * 验证tk
     *
     * @param
     * @return
     **/
    public JSONObject checkTk() {
        Map<String, String> params = new HashMap<>();
        params.put("tk", Login.tk);
        params.put("_json_att", "");
        JSONObject jsonObject = query(BaseClient.CHECK_TK, params);
        return jsonObject;
    }

    /**
     * 提交订单
     *
     * @param
     * @return
     **/
    public String submitOrderRequest(Map<String, String> params) {
        params.put("tour_flag", "dc");
        params.put("purpose_codes", "ADULT");
        params.put("undefined", "");
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", "https://kyfw.12306.cn/otn/leftTicket/init");
        headers.put("Origin", "https://kyfw.12306.cn");
        headers.put("Host", "kyfw.12306.cn");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Connection", "keep-alive");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        HttpPost post = new HttpPost(BaseClient.SUBMIT_ORDER);
        BaseClient.setHead(post, BaseClient.defaultHeaders);
        BaseClient.setHead(post, headers);
        String s = queryPostString(post, params);
        return s;
    }

    /**
     * 获取12306账户上面的乘客信息
     *
     * @param
     * @return
     **/
    public JSONObject getPassengerDTOs() {
        Map<String, String> params = new HashMap<>();
        params.put("_json_att", "");
        params.put("REPEAT_SUBMIT_TOKEN", "aeed40a6a784ebd5c4017d977af7f9c9");
        return query(BaseClient.PASSENGER, params);
    }

    public JSONObject checkOrederInfo(Map<String, String> params) {
        params.put("REPEAT_SUBMIT_TOKEN", "2ec32577ce150af97cd27deea9186f91");
        return query(BaseClient.CHECK_ORDAERINFO, params);
    }

    public void check() {
        getTk();
        checkTk();
        checkUser();
    }
}