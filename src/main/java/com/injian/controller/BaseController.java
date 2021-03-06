package com.injian.controller;


import com.injian.error.BusinessException;
import com.injian.error.EmBusinessError;
import com.injian.response.CommonReturnType;
import com.injian.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";
    public static final String DEFAULT_USER_IMG_PATH_F="F:\\ProgramCoding\\IntellJJavaCode\\injian\\src\\main\\resources\\upload\\defaultF.jpg";
    public static final String DEFAULT_USER_IMG_PATH_M="F:\\ProgramCoding\\IntellJJavaCode\\injian\\src\\main\\resources\\upload\\defaultM.jpg";
    public static final String DEFAULT_USER_IMG_PATH="F:\\ProgramCoding\\IntellJJavaCode\\injian\\src\\main\\resources\\upload";

    //定义exceptionhandler解决未被controller层吸收的异常
    // controller层是链接view层最后的一层，若发生在该层的的异常没有没处理，那么对于用户体验并不是很好
    //因此定义一种处理方式，使发生在controller层的异常得到处理，转化为用户可以接受且看的懂得界面，优化用户响应方式
    @ExceptionHandler(Exception.class)//捕获异常
    @ResponseStatus(HttpStatus.OK)//200 并非是业务逻辑异常，因此返回status ok
    @ResponseBody//注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区，通常用来返回JSON数据或者是XML
    //在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，他的效果等同于通过response对象输出指定格式的数据。
    public Object handlerException(HttpServletRequest request, Exception ex){
        Map<String,Object> responseData = new HashMap<>();//解析异常数据,异常错误码和异常信息
        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;//强行向上转型

            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMsg());

        }else{

            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg",EmBusinessError.UNKNOWN_ERROR.getErrMsg());

        }
        return CommonReturnType.create(responseData,"fail");//若直接把枚举传递过来，responseBody所默认的json数据序列化方式直接变成UNKNOWN_ERROR，得不到errCode和errMsg方式



    }

    public UserModel validateUserLogin() throws BusinessException {
        Boolean isLogin = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户未登录");
        }
        //获取用户登录信息
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        return userModel;
    }
}
