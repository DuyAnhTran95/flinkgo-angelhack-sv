package vn.crazyx.flinkgo.config;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ResponseSuccess {
    
    private static final Logger log = Logger.getLogger(ResponseSuccess.class.getName());
    
    private Map<String, Object> responseData = new HashMap<String, Object>();
    private String message;
    private int status;
    
    public static Logger getLog() {
        return log;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(int status, String message) {
        this.message = message;
        this.status = status;
    }
    
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public ResponseSuccess addResults(String key, Object value) {
        try {
            if (key.equals(null) || key.isEmpty())
                throw new Exception("key null");
            if (value.equals(null))
                throw new Exception("value null");
            this.responseData.put(key, value);
            return this;
        } catch (Exception e) {
            return this;
        }
    }
    
    public ResponseSuccess(int status, String message) {
        this.status = status;
        this.message = message;
    }
    
    public Map<String, Object> getResponseData() {
        return responseData;
    }
    
    public void setResponseData(Map<String, Object> responseData) {
        this.responseData = responseData;
    }
  
}
