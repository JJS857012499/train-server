package top.pppppap.train.client;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * TODO
 *
 * @author pppppap
 * @since 2018-06-27 下午 16:36
 */

public class Login {
    private CloseableHttpClient httpClient;
    public static String uamtk;
    public static String tk;

    private Login() {
    }

    public Login(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 获取验证码
     *
     * @return
     * @paramd
     **/
    public OutputStream getCheckImg(OutputStream outputStream) {
        HttpGet httpGet = new HttpGet(BaseClient.CHECK_IMG);
        BaseClient.setHead(httpGet, BaseClient.defaultHeaders);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            response.getEntity().writeTo(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStream;
    }

    /**
     * 校验验证码
     *
     * @param
     * @return
     **/
    public boolean checkImg(Map<String, String> params) {
        HttpPost httpPost = new HttpPost(BaseClient.CHECK_URL);
        BaseClient.setHead(httpPost, BaseClient.defaultHeaders);
        boolean flag = false;
        CloseableHttpResponse response = null;
        try {
            BaseClient.setPostParams(httpPost, params);
            response = httpClient.execute(httpPost);
            JSONObject result = BaseClient.getResponseJson(response.getEntity());
            flag = "4".equals(result.getString("result_code"));
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
        return flag;
    }

    public boolean login(Map<String, String> params) {
        HttpPost httpPost = new HttpPost(BaseClient.LOGIN_URL);
        BaseClient.setHead(httpPost, BaseClient.defaultHeaders);
        boolean flag = false;
        CloseableHttpResponse response = null;
        try {
            BaseClient.setPostParams(httpPost, params);
            response = httpClient.execute(httpPost);
            JSONObject result = BaseClient.getResponseJson(response.getEntity());
            flag = "0".equals(result.getString("result_code"));
            if (flag) uamtk = result.getString("uamtk");
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
        return flag;
    }

}
