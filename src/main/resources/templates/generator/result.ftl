package ${classInfo.packagePath}.core.result;

import java.io.Serializable;

/**
* Created by peng.liu11 on 2018/9/10.
*/
public class Result<T> implements Serializable{

    private static int SUCCESS_CODE = 0;

    private static int ERROR_CODE = 1;

    private int status;
    private String errMsg;

    private T data;

    public T getData() {
    return data;
    }

    public void setData(T data) {
    this.data = data;
    }

    public int getStatus() {
    return status;
    }

    public void setStatus(int status) {
    this.status = status;
    }

    public String getErrMsg() {
    return errMsg;
    }

    public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
    }

    public Result(int status, String errMsg) {
    this.status = status;
    this.errMsg = errMsg;
    }

    public Result(int status, T data) {
    this.status = status;
    this.data = data;
    }

    public static Result SUCCESS = new Result(SUCCESS_CODE, "校验成功！");

    public static Result errorResult(String message) {
    return new Result(ERROR_CODE, message);
    }

    public static <T> Result<T> result(T data) {
        return new Result(SUCCESS_CODE, data);
    }
}

