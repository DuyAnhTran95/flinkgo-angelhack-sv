package vn.crazyx.flinkgo.config;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class ResponseFail {
    private static final Logger log = Logger.getLogger(ResponseFail.class.getName());

    private int status;
    private String error;
    private List<String> messages = new ArrayList<String>();

    public List<String> getMessage() {
        return messages;
    }

    public void setMessage(List<String> message) {
        this.messages = message;
    }
    
    public void addMessage(String message) {
        this.messages.add(message);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResponseFail(int status, String errorCode, String... messages) {
        this.error = errorCode;
        log.warning("Error: " + errorCode);
        for (String message : messages) {
            this.messages.add(message);
        }
        this.status = status;
    }
    
    public ResponseFail(int status, Exception e) {
        this.status = status;
        this.error = "Exception";
        this.messages.add(e.getMessage());
        e.printStackTrace();
    }
    
    public ResponseFail(RestCustomException e) {
        this.status = e.getStatus();
        this.error = e.getErrorCode().toString();
        this.messages.add(e.getMessage());
        e.printStackTrace();
    }

}
