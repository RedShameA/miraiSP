package cn.redsa.miraisp.config;


import cn.redsa.miraisp.vo.ResultMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResultMap AccessDeniedExceptionHandler(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        return new ResultMap().error().message("服务器内部错误");
    }

}