package com.me.ssm.System;

import com.me.ssm.model.User;
import com.me.ssm.service.UserService;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;

/**
 * Created by 不语恋 on 2017/5/8.
 */
public class Authentication {
    static String errorPath;
    @Resource
    static UserService userService;
    static boolean login(String id,String pwd,HttpServletRequest request) throws Exception{
        User user=userService.getUserById(id);
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String pwd2=base64en.encode(md5.digest((pwd+user.getSalt()).getBytes("utf-8")));
        if(pwd2.equals(pwd)){
            // 如果不存在 session 会话，则创建一个 session 对象
            HttpSession session = request.getSession();
            if(!session.isNew()){
                session.invalidate();
                session=request.getSession();
            }
            session.setAttribute("id",user.getId());
            session.setAttribute("role",user.getRole());
            session.setAttribute("userName",user.getName());
            return true;
        }
        return false;
    }
}
