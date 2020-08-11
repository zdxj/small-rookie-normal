package com.zdxj.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zdxj.constant.GlobalConstants;

@RestController
@RequestMapping("/code")
public class VerifycodeController {
	
	/**
	 * 响应验证码图片
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("verifycode")
	public void verifycode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//设置页面不缓存  
    	response.setHeader("Pragma", "no-cache");
    	response.setHeader("Cache-Control", "no-catch");
    	response.setDateHeader("Expires", 0);

    	//在内存中创建图象  
    	int width = 60;
    	int height = 25;
    	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    	//创建图象  
    	Graphics g = image.getGraphics();
    	//生成随机对象  
    	Random random = new Random();
    	//设置背景色  
    	//g.setColor(getRandColor(200,250));  
    	g.setColor(new Color(255, 255, 255));
    	g.fillRect(0, 0, width, height);

    	//设置字体  
    	g.setFont(new Font("Tines Nev Roman", Font.BOLD, 16));
    	//随机产生干扰线  
    	g.setColor(getRandColor(160, 200));
    	for (int i = 0; i < 3; i++) {
    		int x = random.nextInt(width);
    		int y = random.nextInt(height);
    		int xl = random.nextInt(12);
    		int yl = random.nextInt(12);
    		g.drawLine(x, y, xl, yl);
    	}
    	//随机产生认证码,4位数字  
    	String VChar = "A,B,C,D,E,F,G,H,G,K,L,M,N,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,g,k,m,n,p,q,r,s,t,u,v,w,x,y,z"; //,0,1,2,3,4,5,6,7,8,9
		String[] CCodes = VChar.split(",");
    	int CLength = CCodes.length;

    	String sRand = "";
    	for (int i = 0; i < 4; i++) {
    		String rand = CCodes[random.nextInt(CLength)];
    		sRand += rand;
    		g.setColor(new Color(0, 0, 0));
    		g.drawString(rand, 13 * i + 6, 19);
    	}
    	request.getSession().setAttribute(GlobalConstants.PICTURE_VERIFICATION_CODE_KEY, sRand);
    	
    	//图像生效  
    	g.dispose();
		// 输出图像到页面
		ImageIO.write(image, "JPEG", response.getOutputStream());
		response.flushBuffer();
	}
	
	/**
	 * 生成颜色帮助方法
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	protected Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc < 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);

		return new Color(r, g, b);
	}
}
