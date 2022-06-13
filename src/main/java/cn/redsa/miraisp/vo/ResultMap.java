package cn.redsa.miraisp.vo;

import java.util.HashMap;

/**
 *@ClassName: ResponseMap
 *@Author: CJ
 *@Date: 2021-9-2 11:25
 */
public class ResultMap extends HashMap<String, Object> {

    public static ResultMap successOrFail(Boolean b){
        ResultMap resultMap = new ResultMap();
        if (b){
            resultMap.put("code", 200);
            resultMap.put("msg", "操作成功");
        }else{
            resultMap.put("code", 400);
            resultMap.put("msg", "操作失败");
        }
        return resultMap;
    }
    public ResultMap success(){
        this.put("code", 200);
        this.put("msg", "操作成功");
        return this;
    }

    public ResultMap error() {
        this.put("code", 500);
        this.put("msg", "内部错误");
        return this;
    }

    public ResultMap fail() {
        this.put("code", 400);
        this.put("msg", "操作失败");
        return this;
    }

    public ResultMap message(String message) {
        this.put("msg", message);
        return this;
    }

    public ResultMap data(Object object) {
        this.put("data", object);
        return this;
    }

    public ResultMap data(String msg, Object object) {
        this.put("data", object);
        this.put("msg", msg);
        return this;
    }

    public ResultMap exception(Integer code, String message) {
        this.put("code", code);
        this.put("msg", message);
        return this;
    }
}
