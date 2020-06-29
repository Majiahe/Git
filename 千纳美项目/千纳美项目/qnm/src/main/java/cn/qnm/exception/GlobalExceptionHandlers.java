package cn.qnm.exception;

import cn.qnm.util.RestResponse;
import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import freemarker.template.TemplateModelException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

//功能描述:统一异常处理类

@ControllerAdvice
public class GlobalExceptionHandlers {

    private static final Log log = LogFactory.get();

    /**
     * 500 - Bad Request
     */
    @ExceptionHandler({HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            TemplateModelException.class,
            SQLException.class})
    public void handleHttpMessageNotReadableException(HttpServletRequest request,
                                                      HttpServletResponse response,
                                                      Exception e){

        RestResponse restResponse = RestResponse.failure(e.getMessage());
        try {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSONObject.toJSONString(restResponse));
            writer.flush();
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}
