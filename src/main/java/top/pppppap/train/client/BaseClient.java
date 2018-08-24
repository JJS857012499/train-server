package top.pppppap.train.client;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * TODO
 *
 * @author pppppap
 * @since 2018-06-20 下午 12:58
 */

public class BaseClient {
    public static Map<String, String> defaultHeaders = new HashMap<String, String>();

    static {
        defaultHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36");
        defaultHeaders.put("Accept-Language", "zh-CN,zh;q=0.9");
        defaultHeaders.put("Accept", " */*");
    }

    //车票查询地址
    public static final String QUERY_STATION = "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=#&leftTicketDTO.from_station=#&leftTicketDTO.to_station=#&purpose_codes=ADULT";
    //登录地址
    public static final String LOGIN_URL = "https://kyfw.12306.cn/passport/web/login";
    //验证码图片地址
    public static final String CHECK_IMG = "https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&0.014571496076534185";
    //验证码验证地址
    public static final String CHECK_URL = "https://kyfw.12306.cn/passport/captcha/captcha-check";
    //获取乘客信息
    public static final String PASSENGER = "https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs";
    //checkUser
    public static final String CHECK_USER = "https://kyfw.12306.cn/otn/login/checkUser";
    //check uamtk
    public static final String CHECK_UAMTK = "https://kyfw.12306.cn/passport/web/auth/uamtk";
    //验证tk
    public static final String CHECK_TK = "https://kyfw.12306.cn/otn/uamauthclient";
    //获得RepeatSubmitToken
    public static final String REPEAT_SUBMIT_TOKEN = "https://kyfw.12306.cn/otn/confirmPassenger/initDc";
    //提交订单
    public static final String SUBMIT_ORDER = "https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest";
    //检查订单
    public static final String CHECK_ORDAERINFO = "https://kyfw.12306.cn/otn/passport?redirect=/otn/confirmPassenger/checkOrderInfo";

    /**
     * 设置headers
     *
     * @param
     * @return
     **/
    public static void setHead(HttpRequestBase httpRequestBase, Map<String, String> headers) {
        if (headers != null && headers.size() > 0) {
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                httpRequestBase.addHeader(key, headers.get(key));
            }
        }
    }

    /**
     * 设置POST的参数
     *
     * @param httpPost
     * @param paramsMap
     * @throws Exception
     */
    public static void setPostParams(HttpPost httpPost, Map<String, String> paramsMap) throws UnsupportedEncodingException {
        if (paramsMap != null && paramsMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<String> keySet = paramsMap.keySet();
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, paramsMap.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        }
    }

    /**
     * 将返回结果转化为String
     *
     * @param entity
     * @return
     * @throws Exception
     */
    public static String getResponseString(HttpEntity entity) throws IOException {
        if (entity == null) {
            return null;
        }
        InputStream is = entity.getContent();
        StringBuffer strBuf = new StringBuffer();
        byte[] buffer = new byte[4096];
        int r = 0;
        while ((r = is.read(buffer)) > 0) {
            strBuf.append(new String(buffer, 0, r, "UTF-8"));
        }
        return strBuf.toString();
    }

    /**
     * 将返回结果转化为Json
     *
     * @param
     * @return
     **/
    public static JSONObject getResponseJson(HttpEntity entity) throws IOException {
        return JSONObject.parseObject(getResponseString(entity));
    }
}
