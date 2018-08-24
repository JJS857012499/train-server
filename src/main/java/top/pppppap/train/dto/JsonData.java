package top.pppppap.train.dto;

/**
 * TODO
 *
 * @author pppppap
 * @since 2018-06-27 下午 17:17
 */


public class JsonData {
    private Integer code;
    private String msg;
    private Object data;

    public JsonData() {

    }

    public JsonData(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
