package com.me.ssm.System;

import com.me.ssm.model.User;
import com.me.ssm.service.UserService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by 不语恋 on 2017/5/8.
 */
@Component
public class Authentication {
    public static boolean login(String id,String pwd,HttpServletRequest request,UserService userService){
        User user=userService.getUserById(id);
        //加密后的字符串
        String pwd2=md5(pwd+user.getSalt());
        if(pwd2.equals(user.getPassword())){
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
    public static boolean isLogin(HttpServletRequest request){
        // 如果不存在 session 会话，则创建一个 session 对象
        HttpSession session = request.getSession();
        if(session.isNew()){
           return false;
        }
        return true;
    }
    public static String md5(String str){

        MessageDigest md5= null;
        String newstr=null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            newstr=bytesToHexString(md5.digest(str.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newstr;
    }
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    public static String getSalt(){
        char[] chars="0123456789abcdefghijklmnopqrwtuvzxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] saltchars=new char[6];
        Random RANDOM = new SecureRandom();
        for(int i=0;i<6;i++){
            int n=RANDOM.nextInt(62);
            saltchars[i]=chars[n];
        }
        String salt=new String(saltchars);
        return salt;
    }
}
