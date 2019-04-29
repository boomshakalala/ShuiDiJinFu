package tech.shuidikeji.shuidijinfu.http;

public class ResultException extends RuntimeException {
    private  int code;
    private String message;
    private String errorData;

    public ResultException(int code,String message,  String errorData) {
        this.code = code;
        this.errorData = errorData;
        this.message = message;
    }

    public String getErrorData() {
        return errorData;
    }

    public void setErrorData(String errorData) {
        this.errorData = errorData;
    }

    public ResultException(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
