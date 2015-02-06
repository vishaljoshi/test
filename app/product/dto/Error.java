package product.dto;

public class Error {
    
    private String errorMsg =null;
    private String errorCode =null;
    public Error(String errorMsg, String errorCode) {
        super();
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }
    public String getErrorMsg() {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
   
   
}
