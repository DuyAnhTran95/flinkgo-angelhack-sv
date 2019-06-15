package vn.crazyx.flinkgo.config;

@SuppressWarnings("serial")
public class RestCustomException extends RuntimeException {
    
    private Integer status;
    
    private Integer errorCode;
    
    public RestCustomException(Integer errorCode, Integer status, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
    
    public Integer getStatus() {
        return status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public final static RestCustomException UNKNOWN_ERROR = new RestCustomException(10000, 500, "Unkown Error");
    
    //request error
    public final static RestCustomException MALFORM_REQUEST = new RestCustomException(20000, 400, "Malform request");
    public final static RestCustomException WRONG_DOB_FORMAT = new RestCustomException(20001, 400, "Wrong date format");
    public final static RestCustomException PARAMATER_INVALID = new RestCustomException(20002, 400, "Parameter invalid");
    public final static RestCustomException PARAMATER_MISSING = new RestCustomException(20003, 400, "Parameter missing");
    public final static RestCustomException TYPE_MISMATCHED = new RestCustomException(20004, 400, "Parameter type mismatched");
    public final static RestCustomException ACCESS_DENIED = new RestCustomException(20005, 403, "Access denied");
    
    //resource error
    public final static RestCustomException RESOURCE_NOT_FOUND = new RestCustomException(30000, 404, "Resource not found");
    public final static RestCustomException RESOURCE_CONFLICTED = new RestCustomException(30001, 409, "Resource conflicted"); 
    
}
