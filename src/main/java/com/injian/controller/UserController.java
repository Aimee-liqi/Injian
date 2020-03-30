package com.injian.controller;

import com.aliyuncs.exceptions.ClientException;
import com.injian.controller.viewobject.UserVO;
import com.injian.error.BusinessException;
import com.injian.error.EmBusinessError;
import com.injian.response.CommonReturnType;
import com.injian.service.UserService;
import com.injian.service.model.UserModel;
import com.injian.util.SendSms;
import com.injian.util.FileUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")//实现ajex跨域请求,但是没有办法做到session共享
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;//获取httpSession，其对应当前用户对应的请求


    //用户登录
    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone") String telphone,
                                  @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if(StringUtils.isEmpty(telphone)||
                StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务，校验用户登录是否合法
        UserModel userModel = userService.validatelogin(telphone, this.EncodeByMD5(password));//加密后的密码

        //将登录凭证加入到用户登录成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return CommonReturnType.create(this.convertFromModel(userModel));
    }

    //用户注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telphone") String telphone,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name="gender")String gender,
                                     @RequestParam(name="email")String email,
                                     @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setUsername(name);
        userModel.setGender(new Byte(gender));
        userModel.setTelphone(telphone);
        userModel.setEmail(email);
        if(gender.equals("0")){
            userModel.setImgUrl(DEFAULT_USER_IMG_PATH_F);
        }else{
            userModel.setImgUrl(DEFAULT_USER_IMG_PATH_M);
        }

        userModel.setEncrptpassword(this.EncodeByMD5(password));//将密码进行加密
        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    //用户获取otp短信接口
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone") String telphone) throws ClientException, BusinessException {
        //判断用户书否登录若登陆则需要判断手机号是否存在
        Boolean isLogin = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin != null && isLogin){
            userService.validateTelphone(telphone);
        }
        //生成短信验证码
        StringBuffer stringBuffer=new StringBuffer();
        for (int x=0;x<=5;x++) {
            int random = (int) (Math.random() * (10 - 1));
            stringBuffer.append(random);
        }
        String string = stringBuffer.toString();
        int otpCode = Integer.parseInt(string);
        //发送短信验证码
        SendSms.getMessage(telphone,otpCode);
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        System.out.println("telphone = " + telphone + " & otpCode = "+otpCode);
        return CommonReturnType.create(null);
    }

    //短信确认
    @RequestMapping(value = "/confirmotp",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType confirmOtp(@RequestParam(name="otpCode") String otpCode,
                                       @RequestParam(name = "telphone") String telphone )throws BusinessException {
        //输入短信验证码确认
        String inSessionOtpCode = String.valueOf(httpServletRequest.getSession().getAttribute(telphone)) ;
        System.out.println(inSessionOtpCode);
        boolean b = com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionOtpCode);
        if(!b){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        return CommonReturnType.create(null);
    }


    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser() throws BusinessException {
        //判断用户是否登录且拿到用户相关信息
        UserModel userModel = validateUserLogin();
        //从数据库检索准确用户信息，以防用户更改信息刷新页面后userModel没有更新
        UserModel userModelForReturn = userService.getUserById(userModel.getId());
        if(userModelForReturn == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"用户信息有误请重新登录");
        }
        //将核心领域模型用户对象转化为可供UI使用的viewobject
        UserVO userVO = convertFromModel(userModelForReturn);
        //返回通用对象
        return CommonReturnType.create(userVO);
    }

    //修改个人信息
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType updateInfo(@RequestParam(name="name")String name,
                                       @RequestParam(name="gender")String gender,
                                       @RequestParam(name="email")String email) throws BusinessException {
        //判断用户是否登录且拿到用户相关信息
        UserModel userModel = validateUserLogin();
        if( !userModel.getUsername().equals(name)){
            userModel.setUsername(name);
        }
        if( !String.valueOf(userModel.getGender()).equals(gender)){
            userModel.setGender(new Byte(gender));
            if(gender.equals("0")){
                userModel.setImgUrl(DEFAULT_USER_IMG_PATH_F);
            }else {
                userModel.setImgUrl(DEFAULT_USER_IMG_PATH_M);
            }
        }
        if( !userModel.getEmail().equals(email)){
            userModel.setEmail(email);
        }
        userService.updateUserInfo(userModel);
        //更新session 里的 userModel
        UserModel userModelForReturn = userService.getUserById(userModel.getId());
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModelForReturn);
        return CommonReturnType.create(null);
    }

    //上传图片
    @RequestMapping("/imageUpload")
    @ResponseBody
    public CommonReturnType uploadImg(@RequestParam("fileName") MultipartFile file) throws BusinessException, IOException {
        UserModel userModel = validateUserLogin();
        if (file.getSize() / 1000 > 100){
            throw new BusinessException(EmBusinessError.USER_IMAGE_BIG);
        }
        else{
            //判断上传文件格式
            String fileType = file.getContentType();
            if(fileType == null) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
            if (fileType.equals("image/jpg") || fileType.equals("image/png") || fileType.equals("image/jpeg")) {
                // 要上传的目标文件存放的绝对路径
                //用src为保存绝对路径不能改名只能用原名，不用原名会导致ajax上传图片后在前端显示时出现404错误-->原因未知
                //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
                // 获取文件名
                String fileName = file.getOriginalFilename();
                //获取文件后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                //重新生成文件名
                fileName = UUID.randomUUID()+suffixName;
                if (FileUtils.upload(file,DEFAULT_USER_IMG_PATH,fileName)) {
                    //文件存放的相对路径(一般存放在数据库用于img标签的src)
                    String relativePath="img/"+fileName;
                    String realPath = DEFAULT_USER_IMG_PATH + "\\" + fileName;
                    //数据库上传
                    //查询用户原有头像url在项目文件夹中删除
                    String oldPath = userService.getUserById(userModel.getId()).getImgUrl();
                    String oldImgName = oldPath.substring(DEFAULT_USER_IMG_PATH.length()+1);
                    System.out.println(oldImgName);
                    FileUtils.delete(oldPath);
                    userService.updateImg(userModel.getId(),realPath);
                    userModel.setImgUrl(realPath);
                    //userModel更新
                    this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
                }
                else{
                    throw new BusinessException(EmBusinessError.USER_IMAGE_ERROR,"上传失败");
                }
            } else{
                throw new BusinessException(EmBusinessError.USER_IMAGE_ERROR,"图片格式不正确");
            }
        }
        return CommonReturnType.create(null);
    }

    //修改密码&忘记密码
    @RequestMapping("/password")
    @ResponseBody
    public CommonReturnType updatePassword(@RequestParam("password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        UserModel userModel = validateUserLogin();
        userService.updatePassword(userModel.getId(),this.EncodeByMD5(password));
        return CommonReturnType.create(null);
    }





    private UserVO convertFromModel(UserModel userModel)
    {
        if(userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

    private String EncodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定一个计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;

    }




}