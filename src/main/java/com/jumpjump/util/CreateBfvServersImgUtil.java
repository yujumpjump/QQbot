package com.jumpjump.util;

import com.jumpjump.api.GameTool;
import com.jumpjump.base.BfvServers;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

@Component
public class CreateBfvServersImgUtil {

    @Resource
    private GameTool gameTool;


    public   BufferedImage createImg(int wight, int height,List<BfvServers> bfvServer,List<BfvServers> bfvServersS){
        BufferedImage img = null;
        try {
            img = new BufferedImage(wight, height, BufferedImage.TYPE_INT_RGB);


            Graphics2D g = img.createGraphics();
//            BufferedImage bg = ImageIO.read(new File("C:\\Users\\admin\\Desktop\\img\\back.png"));
//            g.drawImage(bg.getScaledInstance(wight, height, Image.SCALE_DEFAULT), 0, 0, null);
            // 设置标题 和标题字体大小和颜色
            g.setColor(new Color(255,255,255));
            g.setFont(new Font("微软雅黑",Font.PLAIN,50));
            g.drawString("8653服务器列表",wight/2-170,70);


            // 设置服务这些多少字体大小和颜色
            g.setColor(new Color(233, 233, 233, 210));
            g.setFont(new Font("微软雅黑",Font.PLAIN,15));
            // 画分割线
            g.drawLine(wight,150,0,150);


            /**
             * 统计BFV全部人数
             */
            int BFVSum = 0;
            for(BfvServers bfvServers:bfvServersS){
                BFVSum += bfvServers.getPlayerAmount();
            }
            // 8653在线人数
            int BSum = 0;
            for(BfvServers bfvServers:bfvServer){
                BSum += bfvServers.getPlayerAmount();
            }
            // 当前全部bfv服务器当前在线多少
            g.drawString("当前BFV服务器总数："+bfvServersS.size(),10,120);
            g.drawString("当前8653服务器总数："+bfvServer.size(),10,140);

            // 当前服务器的人数

            g.drawString("当前BFV总服务器在线人数："+BFVSum,480,120);
            g.drawString("当前8653总服务器在线人数："+BSum,480,140);

            /**
             * 画服务器分割线
             */
            // 定义从那开始画
            int line = 250;
            // 设置 服务器的分割线颜色
            g.setColor(new Color(200, 200, 200, 176));
            for(BfvServers bfvServers:bfvServer){
                // 有多少服务器我们画多少条
                g.drawLine(wight,line,0,line);
                line+=100;
            }

            /**
             * 写服务器名
             */
            int lineText = 180;
            // 设置字体的颜色
            g.setColor(new Color(233, 233, 233));
            g.setFont(new Font("宋体",Font.PLAIN,18));
            for(BfvServers bfvServers:bfvServer){
                // 把数据写上
                g.drawString(bfvServers.getPrefix(),wight/2-150,lineText);
                lineText+=100;
            }

            /**
             * 画图  和服务器介绍
             */
            // 设置字体的颜色
            g.setColor(new Color(210, 210, 210));
            g.setFont(new Font("微软雅黑",Font.PLAIN,15));
            int imgHeight = 165;
            int introduce = 240;
            for(BfvServers bfvServers:bfvServer){
                // 把服务器图片放在上面
                String imgPath = gameTool.getImgPath(bfvServers.getUrl());
                BufferedImage imgServer = ImageIO.read(new File(imgPath));
                g.drawImage(imgServer.getScaledInstance(150, 80, Image.SCALE_DEFAULT), 20, imgHeight, null);
                g.drawString("当前人数："+bfvServers.getServerInfo(),wight/2-150,introduce);
                g.drawString("区域："+bfvServers.getRegion(),wight/2-15,introduce);
                g.drawString("当前地图："+bfvServers.getCurrentMap(),wight/2+70,introduce);
                g.drawString("模式："+bfvServers.getMode(),wight/2+230,introduce);
                imgHeight+=100;
                introduce+=100;
            }
            g.dispose();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return img;
    }
}
