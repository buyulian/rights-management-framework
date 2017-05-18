package com.me.ssm.System;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by 不语恋 on 2017/5/18.
 */
public class VerificationCode {
    private static int width = 90;//验证码宽度
    private static int height = 40;//验证码高度
    private static int codeCount = 4;//验证码个数
    private static int lineCount = 19;//混淆线个数

    static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    public static void getCode(HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        //定义随机数类
        Random r = new Random();
        //定义存储验证码的类
        StringBuilder builderCode = new StringBuilder();
        //定义画布
        BufferedImage buffImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //得到画笔
        Graphics g = buffImg.getGraphics();
        //1.设置颜色,画边框
        g.setColor(Color.black);
        g.drawRect(0,0,width,height);
        //2.设置颜色,填充内部
        g.setColor(Color.white);
        g.fillRect(1,1,width-2,height-2);
        //3.设置干扰线
        g.setColor(Color.gray);
        for (int i = 0; i < lineCount; i++) {
            g.drawLine(r.nextInt(width),r.nextInt(width),r.nextInt(width),r.nextInt(width));
        }
        //4.设置验证码
        g.setColor(Color.blue);
        //4.1设置验证码字体
        g.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,15));
        for (int i = 0; i < codeCount; i++) {
            char c = codeSequence[r.nextInt(codeSequence.length)];
            builderCode.append(c);
            g.drawString(c+"",15*(i+1),15);
        }
        //5.输出到屏幕
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(buffImg,"png",sos);
        //6.保存到session中
        HttpSession session = request.getSession();
        session.setAttribute("codeValidate",builderCode.toString().toLowerCase());
        //7.禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        //8.关闭sos
        sos.close();
    }
    public static boolean validateCode(HttpServletRequest request,String code){
        HttpSession session=request.getSession();
        String codeValidate=(String) session.getAttribute("codeValidate");
        boolean flag=false;
        if(codeValidate!=null&&codeValidate.equals(code.toLowerCase())){
            flag=true;
        }
        session.removeAttribute("codeValidate");
        return flag;
    }
}
